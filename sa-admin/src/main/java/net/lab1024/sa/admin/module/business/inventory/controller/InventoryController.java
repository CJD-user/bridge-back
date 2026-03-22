package net.lab1024.sa.admin.module.business.inventory.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryAddForm;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryAdjustForm;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryQueryForm;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryUpdateForm;
import net.lab1024.sa.admin.module.business.inventory.domain.vo.InventoryVO;
import net.lab1024.sa.admin.module.business.inventory.domain.vo.InventoryWarningVO;
import net.lab1024.sa.admin.module.business.inventory.service.InventoryService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_INVENTORY)
public class InventoryController {

    @Resource
    private InventoryService inventoryService;

    @Operation(summary = "分页查询库存 @author 1024创新实验室")
    @PostMapping("/inventory/query")
    @SaCheckPermission("inventory:query")
    public ResponseDTO<PageResult<InventoryVO>> query(@RequestBody @Valid InventoryQueryForm queryForm) {
        return inventoryService.query(queryForm);
    }

    @Operation(summary = "查询库存详情 @author 1024创新实验室")
    @GetMapping("/inventory/detail/{inventoryId}")
    @SaCheckPermission("inventory:query")
    public ResponseDTO<InventoryVO> detail(@PathVariable Long inventoryId) {
        return inventoryService.getDetail(inventoryId);
    }

    @Operation(summary = "根据材料ID查询库存 @author 1024创新实验室")
    @GetMapping("/inventory/listByMaterial/{materialId}")
    public ResponseDTO<List<InventoryVO>> listByMaterial(@PathVariable Long materialId) {
        return inventoryService.queryByMaterialId(materialId);
    }

    @Operation(summary = "添加库存 @author 1024创新实验室")
    @PostMapping("/inventory/add")
    @SaCheckPermission("inventory:add")
    public ResponseDTO<String> add(@RequestBody @Valid InventoryAddForm addForm) {
        return inventoryService.add(addForm);
    }

    @Operation(summary = "更新库存 @author 1024创新实验室")
    @PostMapping("/inventory/update")
    @SaCheckPermission("inventory:update")
    public ResponseDTO<String> update(@RequestBody @Valid InventoryUpdateForm updateForm) {
        return inventoryService.update(updateForm);
    }

    @Operation(summary = "库存调整(入库/出库/盘点) @author 1024创新实验室")
    @PostMapping("/inventory/adjust")
    @SaCheckPermission("inventory:adjust")
    public ResponseDTO<String> adjust(@RequestBody @Valid InventoryAdjustForm adjustForm) {
        return inventoryService.adjust(adjustForm);
    }

    @Operation(summary = "查询库存预警列表 @author 1024创新实验室")
    @GetMapping("/inventory/warning/list")
    @SaCheckPermission("inventory:query")
    public ResponseDTO<List<InventoryWarningVO>> warningList(@RequestParam(required = false) Integer warningStatus) {
        return inventoryService.queryWarningList(warningStatus);
    }

    @Operation(summary = "刷新库存预警状态 @author 1024创新实验室")
    @GetMapping("/inventory/warning/refresh")
    @SaCheckPermission("inventory:update")
    public ResponseDTO<String> refreshWarning() {
        return inventoryService.refreshWarningStatus();
    }
}
