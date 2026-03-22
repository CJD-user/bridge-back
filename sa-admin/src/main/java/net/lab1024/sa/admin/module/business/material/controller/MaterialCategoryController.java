package net.lab1024.sa.admin.module.business.material.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialCategoryAddForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialCategoryTreeQueryForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialCategoryUpdateForm;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialCategoryTreeVO;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialCategoryVO;
import net.lab1024.sa.admin.module.business.material.service.MaterialCategoryService;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 材料分类管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_MATERIAL_CATEGORY)
public class MaterialCategoryController {

    @Resource
    private MaterialCategoryService materialCategoryService;

    @Operation(summary = "添加分类 @author 1024创新实验室")
    @PostMapping("/materialCategory/add")
    @SaCheckPermission("materialCategory:add")
    public ResponseDTO<String> add(@RequestBody @Valid MaterialCategoryAddForm addForm) {
        return materialCategoryService.add(addForm);
    }

    @Operation(summary = "更新分类 @author 1024创新实验室")
    @PostMapping("/materialCategory/update")
    @SaCheckPermission("materialCategory:update")
    public ResponseDTO<String> update(@RequestBody @Valid MaterialCategoryUpdateForm updateForm) {
        return materialCategoryService.update(updateForm);
    }

    @Operation(summary = "查询分类详情 @author 1024创新实验室")
    @GetMapping("/materialCategory/detail/{materialCategoryId}")
    public ResponseDTO<MaterialCategoryVO> detail(@PathVariable Long materialCategoryId) {
        return materialCategoryService.getDetail(materialCategoryId);
    }

    @Operation(summary = "查询分类树 @author 1024创新实验室")
    @PostMapping("/materialCategory/tree")
    @SaCheckPermission("materialCategory:tree")
    public ResponseDTO<List<MaterialCategoryTreeVO>> queryTree(@RequestBody @Valid MaterialCategoryTreeQueryForm queryForm) {
        return materialCategoryService.queryTree(queryForm);
    }

    @Operation(summary = "删除分类 @author 1024创新实验室")
    @GetMapping("/materialCategory/delete/{materialCategoryId}")
    @SaCheckPermission("materialCategory:delete")
    public ResponseDTO<String> delete(@PathVariable Long materialCategoryId) {
        return materialCategoryService.delete(materialCategoryId);
    }

    @Operation(summary = "更新禁用状态 @author 1024创新实验室")
    @GetMapping("/materialCategory/updateDisabled/{materialCategoryId}")
    @SaCheckPermission("materialCategory:update")
    public ResponseDTO<String> updateDisabled(@PathVariable Long materialCategoryId, @RequestParam Boolean disabledFlag) {
        return materialCategoryService.updateDisabledFlag(materialCategoryId, disabledFlag);
    }

    @Operation(summary = "查询所有分类(下拉选择用) @author 1024创新实验室")
    @GetMapping("/materialCategory/listAll")
    public ResponseDTO<List<MaterialCategoryVO>> listAll() {
        return materialCategoryService.listAll();
    }
}
