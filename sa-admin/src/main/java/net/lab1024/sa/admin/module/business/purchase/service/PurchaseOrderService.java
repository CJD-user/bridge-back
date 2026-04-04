package net.lab1024.sa.admin.module.business.purchase.service;

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
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalHandleForm;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalRecordVO;
import net.lab1024.sa.admin.module.business.material.dao.MaterialDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.admin.module.business.purchase.dao.PurchaseOrderDao;
import net.lab1024.sa.admin.module.business.purchase.domain.entity.PurchaseOrderEntity;
import net.lab1024.sa.admin.module.business.purchase.domain.entity.PurchaseOrderItemEntity;
import net.lab1024.sa.admin.module.business.purchase.domain.form.PurchaseOrderAddForm;
import net.lab1024.sa.admin.module.business.purchase.domain.form.PurchaseOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.domain.vo.PurchaseOrderItemVO;
import net.lab1024.sa.admin.module.business.purchase.domain.vo.PurchaseOrderVO;
import net.lab1024.sa.admin.module.business.supplier.dao.SupplierDao;
import net.lab1024.sa.admin.module.business.supplier.domain.entity.SupplierEntity;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
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
 * 采购申请 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class PurchaseOrderService {

    private static final String[] PURCHASE_TYPE_NAMES = {"", "常规采购", "紧急采购", "补货采购"};
    private static final String[] APPROVAL_STATUS_NAMES = {"待提交", "审批中", "已通过", "已驳回", "已撤回"};

    private static final int APPROVAL_STATUS_DRAFT = 0;
    private static final int APPROVAL_STATUS_PENDING = 1;
    private static final int APPROVAL_STATUS_PASSED = 2;
    private static final int APPROVAL_STATUS_REJECTED = 3;
    private static final int APPROVAL_STATUS_WITHDRAWN = 4;

    private static final int BUSINESS_TYPE_PURCHASE = 1;

    @Resource
    private PurchaseOrderDao purchaseOrderDao;

    @Resource
    private MaterialDao materialDao;

    @Resource
    private SupplierDao supplierDao;

    @Resource
    private ApprovalFlowDao approvalFlowDao;

    @Resource
    private ApprovalNodeDao approvalNodeDao;

    @Resource
    private ApprovalRecordDao approvalRecordDao;

    @Resource
    private MessageService messageService;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(PurchaseOrderAddForm addForm) {
        ApprovalFlowEntity flowEntity = approvalFlowDao.selectById(addForm.getApprovalFlowId());
        if (flowEntity == null || flowEntity.getDeletedFlag() || flowEntity.getStatus() != 1) {
            return ResponseDTO.userErrorParam("审批流程不存在或已停用");
        }

        String purchaseOrderNo = generateOrderNo();

        PurchaseOrderEntity orderEntity = SmartBeanUtil.copy(addForm, PurchaseOrderEntity.class);
        orderEntity.setPurchaseOrderNo(purchaseOrderNo);
        orderEntity.setApprovalStatus(APPROVAL_STATUS_PENDING);
        orderEntity.setCurrentApprovalNode(1);
        orderEntity.setDeletedFlag(false);

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            orderEntity.setCreateUserId(requestEmployee.getEmployeeId());
            orderEntity.setCreateUserName(requestEmployee.getActualName());
        }

        purchaseOrderDao.insert(orderEntity);

        List<PurchaseOrderItemEntity> itemEntities = addForm.getItemList().stream()
                .map(itemForm -> {
                    MaterialEntity materialEntity = materialDao.selectById(itemForm.getMaterialId());
                    if (materialEntity == null || materialEntity.getDeletedFlag()) {
                        throw new RuntimeException("材料不存在: " + itemForm.getMaterialId());
                    }

                    PurchaseOrderItemEntity itemEntity = new PurchaseOrderItemEntity();
                    itemEntity.setPurchaseOrderId(orderEntity.getPurchaseOrderId());
                    itemEntity.setMaterialId(itemForm.getMaterialId());
                    itemEntity.setMaterialCode(materialEntity.getMaterialCode());
                    itemEntity.setMaterialName(materialEntity.getMaterialName());
                    itemEntity.setSpecificationModel(materialEntity.getSpecificationModel());
                    itemEntity.setUnit(materialEntity.getUnit());
                    itemEntity.setPurchaseQuantity(itemForm.getPurchaseQuantity());
                    itemEntity.setUnitPrice(itemForm.getUnitPrice() != null ? itemForm.getUnitPrice() : materialEntity.getUnitPrice());
                    itemEntity.setTotalPrice(itemEntity.getUnitPrice() != null ? itemEntity.getUnitPrice().multiply(itemForm.getPurchaseQuantity()) : BigDecimal.ZERO);
                    itemEntity.setSupplierId(itemForm.getSupplierId());
                    itemEntity.setSupplierName(itemForm.getSupplierName());
                    itemEntity.setRemark(itemForm.getRemark());

                    if (itemForm.getSupplierId() != null && itemForm.getSupplierName() == null) {
                        SupplierEntity supplierEntity = supplierDao.selectById(itemForm.getSupplierId());
                        if (supplierEntity != null) {
                            itemEntity.setSupplierName(supplierEntity.getSupplierName());
                        }
                    }

                    return itemEntity;
                })
                .collect(Collectors.toList());

        purchaseOrderDao.batchInsertItems(itemEntities);

        orderEntity.setTotalAmount(itemEntities.stream()
                .map(PurchaseOrderItemEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        purchaseOrderDao.updateById(orderEntity);

        createApprovalRecords(orderEntity, flowEntity);

        return ResponseDTO.ok();
    }

    private String generateOrderNo() {
        String dateStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        long count = purchaseOrderDao.selectCount(null) + 1;
        return "CG" + dateStr + String.format("%04d", count);
    }

    private void createApprovalRecords(PurchaseOrderEntity orderEntity, ApprovalFlowEntity flowEntity) {
        List<ApprovalNodeEntity> nodeList = approvalNodeDao.queryByFlowId(flowEntity.getApprovalFlowId());
        if (CollectionUtils.isEmpty(nodeList)) {
            return;
        }

        for (ApprovalNodeEntity node : nodeList) {
            ApprovalRecordEntity record = new ApprovalRecordEntity();
            record.setApprovalFlowId(flowEntity.getApprovalFlowId());
            record.setApprovalNodeId(node.getApprovalNodeId());
            record.setBusinessType(BUSINESS_TYPE_PURCHASE);
            record.setBusinessId(orderEntity.getPurchaseOrderId());
            record.setBusinessNo(orderEntity.getPurchaseOrderNo());
            record.setNodeOrder(node.getNodeOrder());
            approvalRecordDao.insert(record);
        }
    }

    public ResponseDTO<PageResult<PurchaseOrderVO>> query(PurchaseOrderQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseOrderVO> list = purchaseOrderDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<PurchaseOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<PageResult<PurchaseOrderVO>> queryMy(PurchaseOrderQueryForm queryForm) {
        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            queryForm.setCreateUserId(requestEmployee.getEmployeeId());
        }
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<PurchaseOrderVO> list = purchaseOrderDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<PurchaseOrderVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<PurchaseOrderVO> getDetail(Long purchaseOrderId) {
        PurchaseOrderEntity orderEntity = purchaseOrderDao.selectById(purchaseOrderId);
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("采购单不存在");
        }

        PurchaseOrderVO orderVO = SmartBeanUtil.copy(orderEntity, PurchaseOrderVO.class);
        this.fillDictNames(Collections.singletonList(orderVO));

        List<PurchaseOrderItemEntity> itemEntities = purchaseOrderDao.queryItemsByOrderId(purchaseOrderId);
        if (CollectionUtils.isNotEmpty(itemEntities)) {
            List<PurchaseOrderItemVO> itemVOList = itemEntities.stream()
                    .map(item -> SmartBeanUtil.copy(item, PurchaseOrderItemVO.class))
                    .collect(Collectors.toList());
            orderVO.setItemList(itemVOList);
        }

        return ResponseDTO.ok(orderVO);
    }

    public ResponseDTO<List<ApprovalRecordVO>> getApprovalRecords(Long purchaseOrderId) {
        List<ApprovalRecordEntity> records = approvalRecordDao.queryByBusinessId(BUSINESS_TYPE_PURCHASE, purchaseOrderId);
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

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> handleApproval(ApprovalHandleForm handleForm) {
        PurchaseOrderEntity orderEntity = purchaseOrderDao.selectById(handleForm.getBusinessId());
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("采购单不存在");
        }

        if (orderEntity.getApprovalStatus() != APPROVAL_STATUS_PENDING) {
            return ResponseDTO.userErrorParam("当前采购单不在审批中");
        }

        ApprovalRecordEntity recordEntity = approvalRecordDao.selectByBusinessAndNode(
                BUSINESS_TYPE_PURCHASE,
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
            purchaseOrderDao.updateById(orderEntity);
            sendNotification(orderEntity, "采购申请已驳回", "您的采购申请单【" + orderEntity.getPurchaseOrderNo() + "】已被驳回，审批意见：" + (handleForm.getApprovalOpinion() != null ? handleForm.getApprovalOpinion() : "无"));
            return ResponseDTO.ok();
        }

        List<ApprovalNodeEntity> nodeList = approvalNodeDao.queryByFlowId(orderEntity.getApprovalFlowId());
        int totalNodes = CollectionUtils.isNotEmpty(nodeList) ? nodeList.size() : 1;

        if (orderEntity.getCurrentApprovalNode() >= totalNodes) {
            orderEntity.setApprovalStatus(APPROVAL_STATUS_PASSED);
            purchaseOrderDao.updateById(orderEntity);
            sendNotification(orderEntity, "采购申请已通过", "您的采购申请单【" + orderEntity.getPurchaseOrderNo() + "】已审批通过。");
        } else {
            orderEntity.setCurrentApprovalNode(orderEntity.getCurrentApprovalNode() + 1);
            purchaseOrderDao.updateById(orderEntity);
        }

        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> withdraw(Long purchaseOrderId) {
        PurchaseOrderEntity orderEntity = purchaseOrderDao.selectById(purchaseOrderId);
        if (orderEntity == null || orderEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("采购单不存在");
        }

        if (orderEntity.getApprovalStatus() != APPROVAL_STATUS_PENDING) {
            return ResponseDTO.userErrorParam("只有审批中的采购单可以撤回");
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee == null || !requestEmployee.getEmployeeId().equals(orderEntity.getCreateUserId())) {
            return ResponseDTO.userErrorParam("只有申请人可以撤回");
        }

        orderEntity.setApprovalStatus(APPROVAL_STATUS_WITHDRAWN);
        purchaseOrderDao.updateById(orderEntity);

        return ResponseDTO.ok();
    }

    private void fillDictNames(List<PurchaseOrderVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getPurchaseType() != null && e.getPurchaseType() >= 1 && e.getPurchaseType() <= 3) {
                e.setPurchaseTypeName(PURCHASE_TYPE_NAMES[e.getPurchaseType()]);
            }
            if (e.getApprovalStatus() != null && e.getApprovalStatus() >= 0 && e.getApprovalStatus() <= 4) {
                e.setApprovalStatusName(APPROVAL_STATUS_NAMES[e.getApprovalStatus()]);
            }
        });
    }

    private void sendNotification(PurchaseOrderEntity orderEntity, String title, String content) {
        if (orderEntity.getCreateUserId() == null) {
            return;
        }

        MessageSendForm sendForm = new MessageSendForm();
        sendForm.setMessageType(MessageTypeEnum.MAIL.getValue());
        sendForm.setReceiverUserType(UserTypeEnum.ADMIN_EMPLOYEE.getValue());
        sendForm.setReceiverUserId(orderEntity.getCreateUserId());
        sendForm.setTitle(title);
        sendForm.setContent(content);
        sendForm.setDataId(orderEntity.getPurchaseOrderId());

        messageService.sendMessage(sendForm);
    }
}
