package net.lab1024.sa.admin.module.business.supplier.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateAddForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateQueryForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateUpdateForm;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierEvaluateVO;
import net.lab1024.sa.admin.module.business.supplier.service.SupplierEvaluateService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 供应商评价 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_SUPPLIER_EVALUATE)
public class SupplierEvaluateController {

    @Resource
    private SupplierEvaluateService supplierEvaluateService;

    @Operation(summary = "分页查询评价 @author 1024创新实验室")
    @PostMapping("/supplierEvaluate/query")
    @SaCheckPermission("supplierEvaluate:query")
    public ResponseDTO<PageResult<SupplierEvaluateVO>> query(@RequestBody @Valid SupplierEvaluateQueryForm queryForm) {
        return supplierEvaluateService.query(queryForm);
    }

    @Operation(summary = "根据供应商ID查询评价列表 @author 1024创新实验室")
    @GetMapping("/supplierEvaluate/list/{supplierId}")
    public ResponseDTO<List<SupplierEvaluateVO>> listBySupplierId(@PathVariable Long supplierId) {
        return supplierEvaluateService.queryBySupplierId(supplierId);
    }

    @Operation(summary = "查询评价详情 @author 1024创新实验室")
    @GetMapping("/supplierEvaluate/detail/{evaluateId}")
    public ResponseDTO<SupplierEvaluateVO> detail(@PathVariable Long evaluateId) {
        return supplierEvaluateService.getDetail(evaluateId);
    }

    @Operation(summary = "添加评价 @author 1024创新实验室")
    @PostMapping("/supplierEvaluate/add")
    @SaCheckPermission("supplierEvaluate:add")
    public ResponseDTO<String> add(@RequestBody @Valid SupplierEvaluateAddForm addForm) {
        return supplierEvaluateService.add(addForm);
    }

    @Operation(summary = "更新评价 @author 1024创新实验室")
    @PostMapping("/supplierEvaluate/update")
    @SaCheckPermission("supplierEvaluate:update")
    public ResponseDTO<String> update(@RequestBody @Valid SupplierEvaluateUpdateForm updateForm) {
        return supplierEvaluateService.update(updateForm);
    }

    @Operation(summary = "删除评价 @author 1024创新实验室")
    @GetMapping("/supplierEvaluate/delete/{evaluateId}")
    @SaCheckPermission("supplierEvaluate:delete")
    public ResponseDTO<String> delete(@PathVariable Long evaluateId) {
        return supplierEvaluateService.delete(evaluateId);
    }
}
