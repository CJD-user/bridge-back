package net.lab1024.sa.admin.module.business.outbound.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalHandleForm;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalRecordVO;
import net.lab1024.sa.admin.module.business.outbound.domain.form.OutboundOrderAddForm;
import net.lab1024.sa.admin.module.business.outbound.domain.form.OutboundOrderQueryForm;
import net.lab1024.sa.admin.module.business.outbound.domain.vo.OutboundOrderVO;
import net.lab1024.sa.admin.module.business.outbound.service.OutboundOrderService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出库申请管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_OUTBOUND_ORDER)
public class OutboundOrderController {

    @Resource
    private OutboundOrderService outboundOrderService;

    @Operation(summary = "分页查询出库申请 @author 1024创新实验室")
    @PostMapping("/outboundOrder/query")
    @SaCheckPermission("outboundOrder:query")
    public ResponseDTO<PageResult<OutboundOrderVO>> query(@RequestBody @Valid OutboundOrderQueryForm queryForm) {
        return outboundOrderService.query(queryForm);
    }

    @Operation(summary = "查询我的出库申请 @author 1024创新实验室")
    @PostMapping("/outboundOrder/queryMy")
    public ResponseDTO<PageResult<OutboundOrderVO>> queryMy(@RequestBody @Valid OutboundOrderQueryForm queryForm) {
        return outboundOrderService.queryMy(queryForm);
    }

    @Operation(summary = "查询出库申请详情 @author 1024创新实验室")
    @GetMapping("/outboundOrder/detail/{outboundOrderId}")
    public ResponseDTO<OutboundOrderVO> detail(@PathVariable Long outboundOrderId) {
        return outboundOrderService.getDetail(outboundOrderId);
    }

    @Operation(summary = "发起出库申请 @author 1024创新实验室")
    @PostMapping("/outboundOrder/add")
    public ResponseDTO<String> add(@RequestBody @Valid OutboundOrderAddForm addForm) {
        return outboundOrderService.add(addForm);
    }

    @Operation(summary = "审批出库申请 @author 1024创新实验室")
    @PostMapping("/outboundOrder/approval")
    @SaCheckPermission("outboundOrder:approval")
    public ResponseDTO<String> approval(@RequestBody @Valid ApprovalHandleForm handleForm) {
        return outboundOrderService.handleApproval(handleForm);
    }

    @Operation(summary = "查询审批记录 @author 1024创新实验室")
    @GetMapping("/outboundOrder/approvalRecords/{outboundOrderId}")
    public ResponseDTO<List<ApprovalRecordVO>> approvalRecords(@PathVariable Long outboundOrderId) {
        return outboundOrderService.getApprovalRecords(outboundOrderId);
    }

    @Operation(summary = "撤回出库申请 @author 1024创新实验室")
    @GetMapping("/outboundOrder/withdraw/{outboundOrderId}")
    public ResponseDTO<String> withdraw(@PathVariable Long outboundOrderId) {
        return outboundOrderService.withdraw(outboundOrderId);
    }

    @Operation(summary = "确认出库 @author 1024创新实验室")
    @GetMapping("/outboundOrder/confirm/{outboundOrderId}")
    @SaCheckPermission("outboundOrder:confirm")
    public ResponseDTO<String> confirm(@PathVariable Long outboundOrderId) {
        return outboundOrderService.confirmOutbound(outboundOrderId);
    }
}
