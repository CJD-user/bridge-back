package net.lab1024.sa.admin.module.business.approvalFlow.controller;

import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowAddForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowQueryForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowUpdateForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.vo.ApprovalFlowVO;
import net.lab1024.sa.admin.module.business.approvalFlow.service.ApprovalFlowService;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

/**
 * 审批流程配置表 Controller
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@RestController
@Tag(name = "审批流程配置表")
public class ApprovalFlowController {

    @Resource
    private ApprovalFlowService approvalFlowService;

    @Operation(summary = "分页查询 @author hyc")
    @PostMapping("/approvalFlow/queryPage")
    @SaCheckPermission("approvalFlow:query")
    public ResponseDTO<PageResult<ApprovalFlowVO>> queryPage(@RequestBody @Valid ApprovalFlowQueryForm queryForm) {
        return ResponseDTO.ok(approvalFlowService.queryPage(queryForm));
    }

    @Operation(summary = "添加 @author hyc")
    @PostMapping("/approvalFlow/add")
    @SaCheckPermission("approvalFlow:add")
    public ResponseDTO<String> add(@RequestBody @Valid ApprovalFlowAddForm addForm) {
        return approvalFlowService.add(addForm);
    }

    @Operation(summary = "更新 @author hyc")
    @PostMapping("/approvalFlow/update")
    @SaCheckPermission("approvalFlow:update")
    public ResponseDTO<String> update(@RequestBody @Valid ApprovalFlowUpdateForm updateForm) {
        return approvalFlowService.update(updateForm);
    }

    @Operation(summary = "批量删除 @author hyc")
    @PostMapping("/approvalFlow/batchDelete")
    @SaCheckPermission("approvalFlow:delete")
    public ResponseDTO<String> batchDelete(@RequestBody ValidateList<Long> idList) {
        return approvalFlowService.batchDelete(idList);
    }

    @Operation(summary = "单个删除 @author hyc")
    @GetMapping("/approvalFlow/delete/{approvalFlowId}")
    @SaCheckPermission("approvalFlow:delete")
    public ResponseDTO<String> batchDelete(@PathVariable Long approvalFlowId) {
        return approvalFlowService.delete(approvalFlowId);
    }
}
