package net.lab1024.sa.admin.module.business.purchase.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.approval.domain.form.ApprovalHandleForm;
import net.lab1024.sa.admin.module.business.approval.domain.vo.ApprovalRecordVO;
import net.lab1024.sa.admin.module.business.purchase.domain.form.PurchaseOrderAddForm;
import net.lab1024.sa.admin.module.business.purchase.domain.form.PurchaseOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.domain.vo.PurchaseOrderVO;
import net.lab1024.sa.admin.module.business.purchase.service.PurchaseOrderService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购申请管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_PURCHASE_ORDER)
public class PurchaseOrderController {

    @Resource
    private PurchaseOrderService purchaseOrderService;

    @Operation(summary = "分页查询采购申请 @author 1024创新实验室")
    @PostMapping("/purchaseOrder/query")
    @SaCheckPermission("purchaseOrder:query")
    public ResponseDTO<PageResult<PurchaseOrderVO>> query(@RequestBody @Valid PurchaseOrderQueryForm queryForm) {
        return purchaseOrderService.query(queryForm);
    }

    @Operation(summary = "查询我的采购申请 @author 1024创新实验室")
    @PostMapping("/purchaseOrder/queryMy")
    public ResponseDTO<PageResult<PurchaseOrderVO>> queryMy(@RequestBody @Valid PurchaseOrderQueryForm queryForm) {
        return purchaseOrderService.queryMy(queryForm);
    }

    @Operation(summary = "查询采购申请详情 @author 1024创新实验室")
    @GetMapping("/purchaseOrder/detail/{purchaseOrderId}")
    @SaCheckPermission("purchaseOrder:query")
    public ResponseDTO<PurchaseOrderVO> detail(@PathVariable Long purchaseOrderId) {
        return purchaseOrderService.getDetail(purchaseOrderId);
    }

    @Operation(summary = "发起采购申请 @author 1024创新实验室")
    @PostMapping("/purchaseOrder/add")
    @SaCheckPermission("purchaseOrder:add")
    public ResponseDTO<String> add(@RequestBody @Valid PurchaseOrderAddForm addForm) {
        return purchaseOrderService.add(addForm);
    }

    @Operation(summary = "审批采购申请 @author 1024创新实验室")
    @PostMapping("/purchaseOrder/approval")
    @SaCheckPermission("purchaseOrder:approval")
    public ResponseDTO<String> approval(@RequestBody @Valid ApprovalHandleForm handleForm) {
        return purchaseOrderService.handleApproval(handleForm);
    }

    @Operation(summary = "查询审批记录 @author 1024创新实验室")
    @GetMapping("/purchaseOrder/approvalRecords/{purchaseOrderId}")
    public ResponseDTO<List<ApprovalRecordVO>> approvalRecords(@PathVariable Long purchaseOrderId) {
        return purchaseOrderService.getApprovalRecords(purchaseOrderId);
    }

    @Operation(summary = "撤回采购申请 @author 1024创新实验室")
    @GetMapping("/purchaseOrder/withdraw/{purchaseOrderId}")
    @SaCheckPermission("purchaseOrder:update")
    public ResponseDTO<String> withdraw(@PathVariable Long purchaseOrderId) {
        return purchaseOrderService.withdraw(purchaseOrderId);
    }
}
