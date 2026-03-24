package net.lab1024.sa.admin.module.business.outbound.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.approval.dao.ApprovalFlowDao;
import net.lab1024.sa.admin.module.business.approval.dao.ApprovalNodeDao;
import net.lab1024.sa.admin.module.business.approval.dao.ApprovalRecordDao;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalFlowEntity;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalNodeEntity;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalRecordEntity;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalRecordVO;
import net.lab1024.sa.admin.module.business.inventory.dao.InventoryDao;
import net.lab1024.sa.admin.module.business.inventory.domain.entity.InventoryEntity;
import net.lab1024.sa.admin.module.business.inventory.service.InventoryService;
import net.lab1024.sa.admin.module.business.material.dao.MaterialDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.admin.module.business.outbound.dao.OutboundOrderDao;
import net.lab1024.sa.admin.module.business.outbound.domain.entity.OutboundOrderEntity;
import net.lab1024.sa.admin.module.business.outbound.domain.entity.OutboundOrderItemEntity;
import net.lab1024.sa.admin.module.business.outbound.domain.form.OutboundOrderAddForm;
import net.lab1024.sa.admin.module.business.outbound.domain.form.OutboundOrderQueryForm;
import net.lab1024.sa.admin.module.business.outbound.domain.vo.OutboundOrderItemVO;
import net.lab1024.sa.admin.module.business.outbound.domain.vo.OutboundOrderVO;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.enumeration.UserTypeEnum;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.message.constant.MessageTypeEnum;
import net.lab1024.sa.base.module.support.message.domain.MessageSendForm;
import net.lab1024.sa.base.module.support.message.service.MessageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 出库申请 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class OutboundOrderService {

    private static final String[] OUTBOUND_TYPE_NAMES = {"", "领用出库", "调拨出库", "报废出库"};
    private static final String[] APPROVAL_STATUS_NAMES = {"待提交", "审批中", "已通过", "已驳回", "已撤回"};
    private static final String[] STATUS_NAMES = {"待审批", "已审批待出库", "已出库", "已作废"};

    private static final int APPROVAL_STATUS_DRAFT = 0;
    private static final int APPROVAL_STATUS_PENDING = 1;
    private static final int APPROVAL_STATUS_PASSED = 2;
    private static final int APPROVAL_STATUS_REJECTED = 3;
    private static final int APPROVAL_STATUS_WITHDRAWN = 4;

    private static final int STATUS_PENDING = 0;
    private static final int STATUS_APPROVED = 1;
    private static final int STATUS_OUTBOUND = 2;
    private static final int STATUS_CANCELLED = 3;

    private static final int BUSINESS_TYPE_OUTBOUND = 2;

    @Resource
    private OutboundOrderDao outboundOrderDao;

    @Resource
    private MaterialDao materialDao;

    @Resource
    private InventoryDao inventoryDao;

    @Resource
    private InventoryService inventoryService;

    @Resource
    private ApprovalFlowDao approvalFlowDao;

    @Resource
    private ApprovalNodeDao approvalNodeDao;

    @Resource
    private ApprovalRecordDao approvalRecordDao;

    @Resource
    private MessageService messageService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(OutboundOrderAddForm addForm) {
        ApprovalFlowEntity flowEntity = approvalFlowDao.selectById(addForm.getApprovalFlowId());
        if (flowEntity == null || flowEntity.getDeletedFlag() || flowEntity.getStatus() != 1) {
            return ResponseDTO.userErrorParam("审批流程不存在或已停用");
        }

        String outboundOrderNo = generateOrderNo();

        OutboundOrderEntity orderEntity = SmartBeanUtil.copy(addForm, OutboundOrderEntity.class);
        orderEntity.setOutboundOrderNo(outboundOrderNo);
        orderEntity.setApprovalStatus(APPROVAL_STATUS_PENDING);
        orderEntity.setCurrentApprovalNode(1);
        orderEntity.setStatus(STATUS_PENDING);
        orderEntity.setDeletedFlag(false);

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            orderEntity.setCreateUserId(requestEmployee.getEmployeeId());
            orderEntity.setCreateUserName(requestEmployee.getActualName());
        }

        BigDecimal totalQuantity = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;

        outboundOrderDao.insert(orderEntity);

        List<OutboundOrderItemEntity> itemEntities = addForm.getItemList().stream()
                .map(itemForm -> {
                    MaterialEntity materialEntity = materialDao.selectById(itemForm.getMaterialId());
                    if (materialEntity == null || materialEntity.getDeletedFlag()) {
                        throw new RuntimeException("材料不存在: " + itemForm.getMaterialId());
                    }

                    OutboundOrderItemEntity itemEntity = new OutboundOrderItemEntity();
                    itemEntity.setOutboundOrderId(orderEntity.getOutboundOrderId());
                    itemEntity.setMaterialId(itemForm.getMaterialId());
                    itemEntity.setMaterialCode(materialEntity.getMaterialCode());
                    itemEntity.setMaterialName(materialEntity.getMaterialName());
                    itemEntity.setSpecificationModel(materialEntity.getSpecificationModel());
                    itemEntity.setUnit(materialEntity.getUnit());
                    itemEntity.setRequestQuantity(itemForm.getRequestQuantity());
                    itemEntity.setApprovedQuantity(itemForm.getRequestQuantity());
                    itemEntity.setActualQuantity(itemForm.getRequestQuantity());
                    itemEntity.setUnitPrice(materialEntity.getUnitPrice() != null ? materialEntity.getUnitPrice() : BigDecimal.ZERO);
                    itemEntity.setTotalPrice(itemEntity.getUnitPrice().multiply(itemForm.getRequestQuantity()));
                    itemEntity.setBatchNo(itemForm.getBatchNo());
                    itemEntity.setStorageLocation(itemForm.getStorageLocation());
                    itemEntity.setRemark(itemForm.getRemark());

                    totalQuantity.add(itemForm.getRequestQuantity());
                    totalAmount.add(itemEntity.getTotalPrice());

                    return itemEntity;
                })
                .collect(Collectors.toList());

        outboundOrderDao.batchInsertItems(itemEntities);

        orderEntity.setTotalQuantity(itemEntities.stream()
                .map(OutboundOrderItemEntity::getRequestQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderEntity.setTotalAmount(itemEntities.stream()
                .map(OutboundOrderItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        outboundOrderDao.updateById(orderEntity);

        createApprovalRecords(orderEntity, flowEntity);

        return ResponseDTO.ok();
    }

    private String generateOrderNo() {
        String dateStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        long count = outboundOrderDao.selectCount(null) + 1;
        return "CK" + dateStr + String.format("%04d", count);
    }

    private void createApprovalRecords(OutboundOrderEntity orderEntity, ApprovalFlowEntity flowEntity) {
        List<ApprovalNodeEntity> nodeList = approvalNodeDao.queryByFlowId(flowEntity.getApprovalFlowId());
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        String approverName = requestEmployee != null ? requestEmployee.getActualName() : null;
        Long approverId = requestEmployee != null ? requestEmployee.getEmployeeId() : null;

        for (ApprovalNodeEntity node : nodeList) {
            ApprovalRecordEntity record = new ApprovalRecordEntity();
            record.setApprovalFlowId(flowEntity.getApprovalFlowId());
            record.setApprovalNodeId(node.getApprovalNodeId());
            record.setBusinessType(BUSINESS_TYPE_OUTBOUND);
            record.setBusinessId(orderEntity.getOutboundOrderId());
            record.setBusinessNo(orderEntity.getOutboundOrderNo());
            record.setNodeOrder(node.getNodeOrder());
            record.setApproverId(approverId);
            record.setApproverName(approverName);
            approvalRecordDao.insert(record);
        }
    }

    public ResponseDTO<PageResult<OutboundOrderVO>> query(OutboundOrderQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<OutboundOrderVO> list = outboundOrderDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<OutboundOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<PageResult<OutboundOrderVO>> queryMy(OutboundOrderQueryForm queryForm) {
        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            queryForm.setCreateUserId(requestEmployee.getEmployeeId());
        }
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<OutboundOrderVO> list = outboundOrderDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<OutboundOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<OutboundOrderVO> getDetail(Long outboundOrderId) {
        OutboundOrderEntity orderEntity = outboundOrderDao.selectById(outboundOrderId);
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("出库单不存在");
        }

        OutboundOrderVO orderVO = SmartBeanUtil.copy(orderEntity, OutboundOrderVO.class);
        this.fillDictNames(Collections.singletonList(orderVO));

        List<OutboundOrderItemEntity> itemEntities = outboundOrderDao.queryItemsByOrderId(outboundOrderId);
        if (CollectionUtils.isNotEmpty(itemEntities)) {
            List<OutboundOrderItemVO> itemVOList = itemEntities.stream()
                    .map(item -> SmartBeanUtil.copy(item, OutboundOrderItemVO.class))
                    .collect(Collectors.toList());
            orderVO.setItemList(itemVOList);
        }

        return ResponseDTO.ok(orderVO);
    }

    public ResponseDTO<List<ApprovalRecordVO>> getApprovalRecords(Long outboundOrderId) {
        List<ApprovalRecordEntity> records = approvalRecordDao.queryByBusinessId(BUSINESS_TYPE_OUTBOUND, outboundOrderId);
        List<ApprovalRecordVO> voList = records.stream()
                .map(record -> {
                    ApprovalRecordVO vo = SmartBeanUtil.copy(record, ApprovalRecordVO.class);
                    if (record.getApprovalStatus() != null) {
                        vo.setApprovalStatusName(record.getApprovalStatus() == 1 ? "通过" : "打回");
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return ResponseDTO.ok(voList);
    }

    private void fillDictNames(List<OutboundOrderVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getOutboundType() != null && e.getOutboundType() >= 1 && e.getOutboundType() <= 3) {
                e.setOutboundTypeName(OUTBOUND_TYPE_NAMES[e.getOutboundType()]);
            }
            if (e.getApprovalStatus() != null && e.getApprovalStatus() >= 0 && e.getApprovalStatus() <= 4) {
                e.setApprovalStatusName(APPROVAL_STATUS_NAMES[e.getApprovalStatus()]);
            }
            if (e.getStatus() != null && e.getStatus() >= 0 && e.getStatus() <= 3) {
                e.setStatusName(STATUS_NAMES[e.getStatus()]);
            }
        });
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> handleApproval(net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalHandleForm handleForm) {
        OutboundOrderEntity orderEntity = outboundOrderDao.selectById(handleForm.getBusinessId());
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("出库单不存在");
        }

        if (orderEntity.getApprovalStatus() != APPROVAL_STATUS_PENDING) {
            return ResponseDTO.userErrorParam("当前出库单不在审批中");
        }

        ApprovalRecordEntity recordEntity = approvalRecordDao.selectByBusinessAndNode(
                BUSINESS_TYPE_OUTBOUND,
                handleForm.getBusinessId(),
                orderEntity.getCurrentApprovalNode()
        );

        if (recordEntity == null) {
            return ResponseDTO.userErrorParam("审批记录不存在");
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        recordEntity.setApprovalStatus(handleForm.getApprovalStatus());
        recordEntity.setApprovalOpinion(handleForm.getApprovalOpinion());
        recordEntity.setAttachment(handleForm.getAttachment());
        recordEntity.setApprovalTime(LocalDateTime.now());
        if (requestEmployee != null) {
            recordEntity.setApproverId(requestEmployee.getEmployeeId());
            recordEntity.setApproverName(requestEmployee.getActualName());
        }
        approvalRecordDao.updateById(recordEntity);

        if (handleForm.getApprovalStatus() == 2) {
            orderEntity.setApprovalStatus(APPROVAL_STATUS_REJECTED);
            orderEntity.setStatus(STATUS_PENDING);
            outboundOrderDao.updateById(orderEntity);
            sendNotification(orderEntity, "出库申请已驳回", "您的出库申请单【" + orderEntity.getOutboundOrderNo() + "】已被驳回，审批意见：" + (handleForm.getApprovalOpinion() != null ? handleForm.getApprovalOpinion() : "无"));
            return ResponseDTO.ok();
        }

        List<ApprovalNodeEntity> nodeList = approvalNodeDao.queryByFlowId(orderEntity.getApprovalFlowId());
        int totalNodes = CollectionUtils.isNotEmpty(nodeList) ? nodeList.size() : 1;

        if (orderEntity.getCurrentApprovalNode() >= totalNodes) {
            orderEntity.setApprovalStatus(APPROVAL_STATUS_PASSED);
            orderEntity.setStatus(STATUS_APPROVED);
            outboundOrderDao.updateById(orderEntity);
            sendNotification(orderEntity, "出库申请已通过", "您的出库申请单【" + orderEntity.getOutboundOrderNo() + "】已审批通过，请确认出库。");
        } else {
            orderEntity.setCurrentApprovalNode(orderEntity.getCurrentApprovalNode() + 1);
            outboundOrderDao.updateById(orderEntity);
        }

        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> withdraw(Long outboundOrderId) {
        OutboundOrderEntity orderEntity = outboundOrderDao.selectById(outboundOrderId);
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("出库单不存在");
        }

        if (orderEntity.getApprovalStatus() != APPROVAL_STATUS_PENDING) {
            return ResponseDTO.userErrorParam("只有审批中的出库单可以撤回");
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee == null || !requestEmployee.getEmployeeId().equals(orderEntity.getCreateUserId())) {
            return ResponseDTO.userErrorParam("只有申请人可以撤回");
        }

        orderEntity.setApprovalStatus(APPROVAL_STATUS_WITHDRAWN);
        orderEntity.setStatus(STATUS_CANCELLED);
        outboundOrderDao.updateById(orderEntity);

        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> confirmOutbound(Long outboundOrderId) {
        OutboundOrderEntity orderEntity = outboundOrderDao.selectById(outboundOrderId);
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("出库单不存在");
        }

        if (orderEntity.getApprovalStatus() != APPROVAL_STATUS_PASSED) {
            return ResponseDTO.userErrorParam("只有审批通过的出库单可以确认出库");
        }

        if (orderEntity.getStatus() != STATUS_APPROVED) {
            return ResponseDTO.userErrorParam("出库单状态不正确");
        }

        List<OutboundOrderItemEntity> itemEntities = outboundOrderDao.queryItemsByOrderId(outboundOrderId);
        if (CollectionUtils.isEmpty(itemEntities)) {
            return ResponseDTO.userErrorParam("出库单明细为空");
        }

        for (OutboundOrderItemEntity item : itemEntities) {
            InventoryEntity inventoryEntity = inventoryDao.selectByMaterialIdAndBatchNo(item.getMaterialId(), item.getBatchNo());
            if (inventoryEntity == null) {
                List<InventoryEntity> inventoryList = inventoryDao.queryByMaterialIdRaw(item.getMaterialId());
                if (CollectionUtils.isNotEmpty(inventoryList)) {
                    inventoryEntity = inventoryList.get(0);
                }
            }

            if (inventoryEntity == null) {
                return ResponseDTO.userErrorParam("材料【" + item.getMaterialName() + "】库存不足，无法出库");
            }

            if (inventoryEntity.getAvailableQuantity().compareTo(item.getActualQuantity()) < 0) {
                return ResponseDTO.userErrorParam("材料【" + item.getMaterialName() + "】可用库存不足，当前可用库存：" + inventoryEntity.getAvailableQuantity() + "，申请数量：" + item.getActualQuantity());
            }

            BigDecimal newQuantity = inventoryEntity.getQuantity().subtract(item.getActualQuantity());
            BigDecimal newAvailableQuantity = inventoryEntity.getAvailableQuantity().subtract(item.getActualQuantity());

            InventoryEntity updateEntity = new InventoryEntity();
            updateEntity.setInventoryId(inventoryEntity.getInventoryId());
            updateEntity.setQuantity(newQuantity);
            updateEntity.setAvailableQuantity(newAvailableQuantity);
            updateEntity.setLastOutboundTime(LocalDateTime.now());

            MaterialEntity materialEntity = materialDao.selectById(item.getMaterialId());
            calculateWarningStatusForInventory(updateEntity, materialEntity);

            inventoryDao.updateById(updateEntity);
        }

        orderEntity.setStatus(STATUS_OUTBOUND);
        outboundOrderDao.updateById(orderEntity);

        return ResponseDTO.ok();
    }

    private void calculateWarningStatusForInventory(InventoryEntity inventoryEntity, MaterialEntity materialEntity) {
        int warningStatus = 0;
        java.time.LocalDate today = java.time.LocalDate.now();

        if (inventoryEntity.getExpirationDate() != null) {
            if (inventoryEntity.getExpirationDate().isBefore(today)) {
                warningStatus = 3;
            } else if (inventoryEntity.getExpirationDate().isBefore(today.plusDays(30))) {
                warningStatus = 2;
            }
        }

        if (warningStatus == 0 && materialEntity != null && materialEntity.getSafetyStockThreshold() != null) {
            if (inventoryEntity.getAvailableQuantity().compareTo(materialEntity.getSafetyStockThreshold()) < 0) {
                warningStatus = 1;
            }
        }

        inventoryEntity.setWarningStatus(warningStatus);
    }

    private void sendNotification(OutboundOrderEntity orderEntity, String title, String content) {
        if (orderEntity.getCreateUserId() == null) {
            return;
        }

        MessageSendForm sendForm = new MessageSendForm();
        sendForm.setMessageType(MessageTypeEnum.MAIL.getValue());
        sendForm.setReceiverUserType(UserTypeEnum.ADMIN_EMPLOYEE.getValue());
        sendForm.setReceiverUserId(orderEntity.getCreateUserId());
        sendForm.setTitle(title);
        sendForm.setContent(content);
        sendForm.setDataId(orderEntity.getOutboundOrderId());

        messageService.sendMessage(sendForm);
    }
}
