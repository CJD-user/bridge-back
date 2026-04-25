package net.lab1024.sa.admin.module.business.supplier.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierAddForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierQueryForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierUpdateForm;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierPurchaseRecordVO;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierVO;
import net.lab1024.sa.admin.module.business.supplier.service.SupplierService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_SUPPLIER)
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @Operation(summary = "分页查询 @author 1024创新实验室")
    @PostMapping("/supplier/query")
    @SaCheckPermission("supplier:query")
    public ResponseDTO<PageResult<SupplierVO>> query(@RequestBody @Valid SupplierQueryForm queryForm) {
        return supplierService.query(queryForm);
    }

    @Operation(summary = "查询详情 @author 1024创新实验室")
    @GetMapping("/supplier/detail/{supplierId}")
    @SaCheckPermission("supplier:query")
    public ResponseDTO<SupplierVO> detail(@PathVariable Long supplierId) {
        return supplierService.getDetail(supplierId);
    }

    @Operation(summary = "添加供应商 @author 1024创新实验室")
    @PostMapping("/supplier/add")
    @SaCheckPermission("supplier:add")
    public ResponseDTO<String> add(@RequestBody @Valid SupplierAddForm addForm) {
        return supplierService.add(addForm);
    }

    @Operation(summary = "更新供应商 @author 1024创新实验室")
    @PostMapping("/supplier/update")
    @SaCheckPermission("supplier:update")
    public ResponseDTO<String> update(@RequestBody @Valid SupplierUpdateForm updateForm) {
        return supplierService.update(updateForm);
    }

    @Operation(summary = "删除供应商 @author 1024创新实验室")
    @GetMapping("/supplier/delete/{supplierId}")
    @SaCheckPermission("supplier:delete")
    public ResponseDTO<String> delete(@PathVariable Long supplierId) {
        return supplierService.delete(supplierId);
    }

    @Operation(summary = "批量删除 @author 1024创新实验室")
    @PostMapping("/supplier/batchDelete")
    @SaCheckPermission("supplier:batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody @Valid ValidateList<Long> idList) {
        return supplierService.batchDelete(idList);
    }

    @Operation(summary = "更新禁用状态 @author 1024创新实验室")
    @GetMapping("/supplier/updateDisabled/{supplierId}")
    @SaCheckPermission("supplier:update")
    public ResponseDTO<String> updateDisabled(@PathVariable Long supplierId, @RequestParam Boolean disabledFlag) {
        return supplierService.updateDisabledFlag(supplierId, disabledFlag);
    }

    @Operation(summary = "查询所有供应商(下拉选择用) @author 1024创新实验室")
    @GetMapping("/supplier/listAll")
    public ResponseDTO<List<SupplierVO>> listAll() {
        return supplierService.listAll();
    }

    @Operation(summary = "查询供应商采购记录 @author 1024创新实验室")
    @GetMapping("/supplier/purchaseRecords/{supplierId}")
    @SaCheckPermission("supplier:query")
    public ResponseDTO<List<SupplierPurchaseRecordVO>> purchaseRecords(@PathVariable Long supplierId) {
        return supplierService.getPurchaseRecords(supplierId);
    }
}
