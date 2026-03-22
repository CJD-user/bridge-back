package net.lab1024.sa.admin.module.business.material.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialAddForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialQueryForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialUpdateForm;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialExcelVO;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialVO;
import net.lab1024.sa.admin.module.business.material.service.MaterialService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.ValidateList;
import net.lab1024.sa.base.common.util.SmartExcelUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 材料管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_MATERIAL)
public class MaterialController {

    @Resource
    private MaterialService materialService;

    @Operation(summary = "分页查询 @author 1024创新实验室")
    @PostMapping("/material/query")
    @SaCheckPermission("material:query")
    public ResponseDTO<PageResult<MaterialVO>> query(@RequestBody @Valid MaterialQueryForm queryForm) {
        return materialService.query(queryForm);
    }

    @Operation(summary = "查询详情 @author 1024创新实验室")
    @GetMapping("/material/detail/{materialId}")
    @SaCheckPermission("material:query")
    public ResponseDTO<MaterialVO> detail(@PathVariable Long materialId) {
        return materialService.getDetail(materialId);
    }

    @Operation(summary = "添加材料 @author 1024创新实验室")
    @PostMapping("/material/add")
    @SaCheckPermission("material:add")
    public ResponseDTO<String> add(@RequestBody @Valid MaterialAddForm addForm) {
        return materialService.add(addForm);
    }

    @Operation(summary = "更新材料 @author 1024创新实验室")
    @PostMapping("/material/update")
    @SaCheckPermission("material:update")
    public ResponseDTO<String> update(@RequestBody @Valid MaterialUpdateForm updateForm) {
        return materialService.update(updateForm);
    }

    @Operation(summary = "删除材料 @author 1024创新实验室")
    @GetMapping("/material/delete/{materialId}")
    @SaCheckPermission("material:delete")
    public ResponseDTO<String> delete(@PathVariable Long materialId) {
        return materialService.delete(materialId);
    }

    @Operation(summary = "批量删除 @author 1024创新实验室")
    @PostMapping("/material/batchDelete")
    @SaCheckPermission("material:batchDelete")
    public ResponseDTO<String> batchDelete(@RequestBody @Valid ValidateList<Long> idList) {
        return materialService.batchDelete(idList);
    }

    @Operation(summary = "更新禁用状态 @author 1024创新实验室")
    @GetMapping("/material/updateDisabled/{materialId}")
    @SaCheckPermission("material:update")
    public ResponseDTO<String> updateDisabled(@PathVariable Long materialId, @RequestParam Boolean disabledFlag) {
        return materialService.updateDisabledFlag(materialId, disabledFlag);
    }

    @Operation(summary = "查询所有材料(下拉选择用) @author 1024创新实验室")
    @GetMapping("/material/listAll")
    public ResponseDTO<List<MaterialVO>> listAll() {
        return materialService.listAll();
    }

    // --------------- 导入导出 -------------------

    @Operation(summary = "导入材料 @author 1024创新实验室")
    @PostMapping("/material/import")
    @SaCheckPermission("material:import")
    public ResponseDTO<String> importMaterial(@RequestParam MultipartFile file) {
        return materialService.importMaterial(file);
    }

    @Operation(summary = "导出材料 @author 1024创新实验室")
    @GetMapping("/material/export")
    @SaCheckPermission("material:export")
    public void exportMaterial(HttpServletResponse response) throws IOException {
        List<MaterialExcelVO> materialList = materialService.getAllMaterialForExport();
        SmartExcelUtil.exportExcel(response, "材料列表.xlsx", "材料", MaterialExcelVO.class, materialList);
    }

    @Operation(summary = "下载导入模板 @author 1024创新实验室")
    @GetMapping("/material/downloadTemplate")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        SmartExcelUtil.exportExcel(response, "材料导入模板.xlsx", "材料", MaterialExcelVO.class, List.of());
    }
}
