package net.lab1024.sa.admin.module.business.approval.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.approval.dao.ApprovalFlowDao;
import net.lab1024.sa.admin.module.business.approval.dao.ApprovalNodeDao;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalFlowEntity;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalNodeEntity;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalFlowAddForm;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalFlowQueryForm;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalFlowUpdateForm;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalFlowVO;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalNodeVO;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 审批流程 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class ApprovalFlowService {

    private static final String[] BUSINESS_TYPE_NAMES = {"", "采购申请", "出库申请"};
    private static final String[] APPROVER_TYPE_NAMES = {"", "指定人员", "部门负责人", "角色", "发起人自选"};

    @Resource
    private ApprovalFlowDao approvalFlowDao;

    @Resource
    private ApprovalNodeDao approvalNodeDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(ApprovalFlowAddForm addForm) {
        ApprovalFlowEntity existEntity = approvalFlowDao.selectByFlowCode(addForm.getFlowCode());
        if (existEntity != null) {
            return ResponseDTO.error(UserErrorCode.ALREADY_EXIST, "流程编码已存在");
        }

        if (CollectionUtils.isEmpty(addForm.getNodeList()) || addForm.getNodeList().size() > 3) {
            return ResponseDTO.userErrorParam("审批节点数量必须在1-3个之间");
        }

        ApprovalFlowEntity flowEntity = SmartBeanUtil.copy(addForm, ApprovalFlowEntity.class);
        flowEntity.setDeletedFlag(false);
        if (flowEntity.getStatus() == null) {
            flowEntity.setStatus(1);
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            flowEntity.setCreateUserId(requestEmployee.getEmployeeId());
            flowEntity.setCreateUserName(requestEmployee.getActualName());
        }

        approvalFlowDao.insert(flowEntity);

        List<ApprovalNodeEntity> nodeList = addForm.getNodeList().stream()
                .map(nodeForm -> {
                    ApprovalNodeEntity nodeEntity = SmartBeanUtil.copy(nodeForm, ApprovalNodeEntity.class);
                    nodeEntity.setApprovalFlowId(flowEntity.getApprovalFlowId());
                    return nodeEntity;
                })
                .collect(Collectors.toList());
        approvalNodeDao.batchInsert(nodeList);

        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(ApprovalFlowUpdateForm updateForm) {
        ApprovalFlowEntity existEntity = approvalFlowDao.selectById(updateForm.getApprovalFlowId());
        if (existEntity == null || existEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("审批流程不存在");
        }

        ApprovalFlowEntity codeEntity = approvalFlowDao.selectByFlowCode(updateForm.getFlowCode());
        if (codeEntity != null && !codeEntity.getApprovalFlowId().equals(updateForm.getApprovalFlowId())) {
            return ResponseDTO.error(UserErrorCode.ALREADY_EXIST, "流程编码已存在");
        }

        if (CollectionUtils.isEmpty(updateForm.getNodeList()) || updateForm.getNodeList().size() > 3) {
            return ResponseDTO.userErrorParam("审批节点数量必须在1-3个之间");
        }

        ApprovalFlowEntity flowEntity = SmartBeanUtil.copy(updateForm, ApprovalFlowEntity.class);
        approvalFlowDao.updateById(flowEntity);

        approvalNodeDao.deleteByFlowId(updateForm.getApprovalFlowId());

        List<ApprovalNodeEntity> nodeList = updateForm.getNodeList().stream()
                .map(nodeForm -> {
                    ApprovalNodeEntity nodeEntity = SmartBeanUtil.copy(nodeForm, ApprovalNodeEntity.class);
                    nodeEntity.setApprovalFlowId(updateForm.getApprovalFlowId());
                    return nodeEntity;
                })
                .collect(Collectors.toList());
        approvalNodeDao.batchInsert(nodeList);

        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<ApprovalFlowVO>> query(ApprovalFlowQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ApprovalFlowVO> list = approvalFlowDao.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ApprovalFlowEntity>()
                        .eq(ApprovalFlowEntity::getDeletedFlag, false)
                        .like(queryForm.getFlowName() != null, ApprovalFlowEntity::getFlowName, queryForm.getFlowName())
                        .like(queryForm.getFlowCode() != null, ApprovalFlowEntity::getFlowCode, queryForm.getFlowCode())
                        .eq(queryForm.getBusinessType() != null, ApprovalFlowEntity::getBusinessType, queryForm.getBusinessType())
                        .eq(queryForm.getStatus() != null, ApprovalFlowEntity::getStatus, queryForm.getStatus())
                        .orderByDesc(ApprovalFlowEntity::getApprovalFlowId)
        ).stream().map(e -> {
            ApprovalFlowVO vo = SmartBeanUtil.copy(e, ApprovalFlowVO.class);
            if (e.getBusinessType() != null && e.getBusinessType() >= 1 && e.getBusinessType() <= 2) {
                vo.setBusinessTypeName(BUSINESS_TYPE_NAMES[e.getBusinessType()]);
            }
            List<ApprovalNodeEntity> nodeList = approvalNodeDao.queryByFlowId(e.getApprovalFlowId());
            if (CollectionUtils.isNotEmpty(nodeList)) {
                List<ApprovalNodeVO> nodeVOList = nodeList.stream()
                        .map(node -> {
                            ApprovalNodeVO nodeVO = SmartBeanUtil.copy(node, ApprovalNodeVO.class);
                            if (node.getApproverType() != null && node.getApproverType() >= 1 && node.getApproverType() <= 4) {
                                nodeVO.setApproverTypeName(APPROVER_TYPE_NAMES[node.getApproverType()]);
                            }
                            return nodeVO;
                        })
                        .collect(Collectors.toList());
                vo.setNodeList(nodeVOList);
            } else {
                vo.setNodeList(java.util.Collections.emptyList());
            }
            return vo;
        }).collect(Collectors.toList());

        PageResult<ApprovalFlowVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<ApprovalFlowVO> getDetail(Long approvalFlowId) {
        ApprovalFlowEntity flowEntity = approvalFlowDao.selectById(approvalFlowId);
        if (flowEntity == null || flowEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("审批流程不存在");
        }

        ApprovalFlowVO flowVO = SmartBeanUtil.copy(flowEntity, ApprovalFlowVO.class);
        if (flowEntity.getBusinessType() != null && flowEntity.getBusinessType() >= 1 && flowEntity.getBusinessType() <= 2) {
            flowVO.setBusinessTypeName(BUSINESS_TYPE_NAMES[flowEntity.getBusinessType()]);
        }

        List<ApprovalNodeEntity> nodeList = approvalNodeDao.queryByFlowId(approvalFlowId);
        if (CollectionUtils.isNotEmpty(nodeList)) {
            List<ApprovalNodeVO> nodeVOList = nodeList.stream()
                    .map(node -> {
                        ApprovalNodeVO nodeVO = SmartBeanUtil.copy(node, ApprovalNodeVO.class);
                        if (node.getApproverType() != null && node.getApproverType() >= 1 && node.getApproverType() <= 4) {
                            nodeVO.setApproverTypeName(APPROVER_TYPE_NAMES[node.getApproverType()]);
                        }
                        return nodeVO;
                    })
                    .collect(Collectors.toList());
            flowVO.setNodeList(nodeVOList);
        }

        return ResponseDTO.ok(flowVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long approvalFlowId) {
        ApprovalFlowEntity flowEntity = approvalFlowDao.selectById(approvalFlowId);
        if (flowEntity == null || flowEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("审批流程不存在");
        }

        ApprovalFlowEntity updateEntity = new ApprovalFlowEntity();
        updateEntity.setApprovalFlowId(approvalFlowId);
        updateEntity.setDeletedFlag(true);
        approvalFlowDao.updateById(updateEntity);

        return ResponseDTO.ok();
    }

    public ResponseDTO<String> updateStatus(Long approvalFlowId, Integer status) {
        ApprovalFlowEntity flowEntity = approvalFlowDao.selectById(approvalFlowId);
        if (flowEntity == null || flowEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("审批流程不存在");
        }

        ApprovalFlowEntity updateEntity = new ApprovalFlowEntity();
        updateEntity.setApprovalFlowId(approvalFlowId);
        updateEntity.setStatus(status);
        approvalFlowDao.updateById(updateEntity);

        return ResponseDTO.ok();
    }

    public ResponseDTO<List<ApprovalFlowVO>> listByBusinessType(Integer businessType) {
        List<ApprovalFlowEntity> flowList = approvalFlowDao.selectList(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<ApprovalFlowEntity>()
                        .eq(ApprovalFlowEntity::getDeletedFlag, false)
                        .eq(ApprovalFlowEntity::getStatus, 1)
                        .eq(businessType != null, ApprovalFlowEntity::getBusinessType, businessType)
                        .orderByDesc(ApprovalFlowEntity::getApprovalFlowId)
        );

        List<ApprovalFlowVO> voList = flowList.stream()
                .map(e -> {
                    ApprovalFlowVO vo = SmartBeanUtil.copy(e, ApprovalFlowVO.class);
                    if (e.getBusinessType() != null && e.getBusinessType() >= 1 && e.getBusinessType() <= 2) {
                        vo.setBusinessTypeName(BUSINESS_TYPE_NAMES[e.getBusinessType()]);
                    }
                    return vo;
                })
                .collect(Collectors.toList());

        return ResponseDTO.ok(voList);
    }
}
