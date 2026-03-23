package net.lab1024.sa.admin.module.business.approval.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalFlowAddForm;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalFlowQueryForm;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalFlowUpdateForm;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalFlowVO;
import net.lab1024.sa.admin.module.business.approval.service.ApprovalFlowService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 审批流程管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_APPROVAL_FLOW)
public class ApprovalFlowController {

    @Resource
    private ApprovalFlowService approvalFlowService;

    @Operation(summary = "分页查询审批流程 @author 1024创新实验室")
    @PostMapping("/approvalFlow/query")
    @SaCheckPermission("approvalFlow:query")
    public ResponseDTO<PageResult<ApprovalFlowVO>> query(@RequestBody @Valid ApprovalFlowQueryForm queryForm) {
        return approvalFlowService.query(queryForm);
    }

    @Operation(summary = "查询审批流程详情 @author 1024创新实验室")
    @GetMapping("/approvalFlow/detail/{approvalFlowId}")
    @SaCheckPermission("approvalFlow:query")
    public ResponseDTO<ApprovalFlowVO> detail(@PathVariable Long approvalFlowId) {
        return approvalFlowService.getDetail(approvalFlowId);
    }

    @Operation(summary = "添加审批流程 @author 1024创新实验室")
    @PostMapping("/approvalFlow/add")
    @SaCheckPermission("approvalFlow:add")
    public ResponseDTO<String> add(@RequestBody @Valid ApprovalFlowAddForm addForm) {
        return approvalFlowService.add(addForm);
    }

    @Operation(summary = "更新审批流程 @author 1024创新实验室")
    @PostMapping("/approvalFlow/update")
    @SaCheckPermission("approvalFlow:update")
    public ResponseDTO<String> update(@RequestBody @Valid ApprovalFlowUpdateForm updateForm) {
        return approvalFlowService.update(updateForm);
    }

    @Operation(summary = "删除审批流程 @author 1024创新实验室")
    @GetMapping("/approvalFlow/delete/{approvalFlowId}")
    @SaCheckPermission("approvalFlow:delete")
    public ResponseDTO<String> delete(@PathVariable Long approvalFlowId) {
        return approvalFlowService.delete(approvalFlowId);
    }

    @Operation(summary = "更新审批流程状态 @author 1024创新实验室")
    @GetMapping("/approvalFlow/updateStatus/{approvalFlowId}")
    @SaCheckPermission("approvalFlow:update")
    public ResponseDTO<String> updateStatus(@PathVariable Long approvalFlowId, @RequestParam Integer status) {
        return approvalFlowService.updateStatus(approvalFlowId, status);
    }

    @Operation(summary = "根据业务类型查询审批流程列表 @author 1024创新实验室")
    @GetMapping("/approvalFlow/listByBusinessType")
    public ResponseDTO<List<ApprovalFlowVO>> listByBusinessType(@RequestParam(required = false) Integer businessType) {
        return approvalFlowService.listByBusinessType(businessType);
    }
}
