package net.lab1024.sa.admin.module.business.warning.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.warning.domain.form.InventoryWarningHandleForm;
import net.lab1024.sa.admin.module.business.warning.domain.form.InventoryWarningQueryForm;
import net.lab1024.sa.admin.module.business.warning.domain.vo.InventoryWarningVO;
import net.lab1024.sa.admin.module.business.warning.service.InventoryWarningService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 库存预警 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_INVENTORY_WARNING)
public class InventoryWarningController {

    @Resource
    private InventoryWarningService inventoryWarningService;

    @Operation(summary = "分页查询库存预警 @author 1024创新实验室")
    @PostMapping("/inventoryWarning/query")
    @SaCheckPermission("inventoryWarning:query")
    public ResponseDTO<PageResult<InventoryWarningVO>> query(@RequestBody @Valid InventoryWarningQueryForm queryForm) {
        return inventoryWarningService.query(queryForm);
    }

    @Operation(summary = "处理库存预警 @author 1024创新实验室")
    @PostMapping("/inventoryWarning/handle")
    @SaCheckPermission("inventoryWarning:handle")
    public ResponseDTO<String> handle(@RequestBody @Valid InventoryWarningHandleForm handleForm) {
        return inventoryWarningService.handle(handleForm);
    }
}
