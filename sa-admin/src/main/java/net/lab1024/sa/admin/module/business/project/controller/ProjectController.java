package net.lab1024.sa.admin.module.business.project.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectAddForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectUpdateForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectOutboundMaterialVO;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import net.lab1024.sa.admin.module.business.project.service.ProjectService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目管理 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_PROJECT)
public class ProjectController {

    @Resource
    private ProjectService projectService;

    @Operation(summary = "分页查询项目 @author 1024创新实验室")
    @PostMapping("/project/query")
    @SaCheckPermission("project:query")
    public ResponseDTO<PageResult<ProjectVO>> query(@RequestBody @Valid ProjectQueryForm queryForm) {
        return projectService.query(queryForm);
    }

    @Operation(summary = "查询项目详情 @author 1024创新实验室")
    @GetMapping("/project/detail/{projectId}")
    @SaCheckPermission("project:query")
    public ResponseDTO<ProjectVO> detail(@PathVariable Long projectId) {
        return projectService.getDetail(projectId);
    }

    @Operation(summary = "查询所有项目列表 @author 1024创新实验室")
    @GetMapping("/project/listAll")
    public ResponseDTO<List<ProjectVO>> listAll() {
        return projectService.queryAll();
    }

    @Operation(summary = "添加项目 @author 1024创新实验室")
    @PostMapping("/project/add")
    @SaCheckPermission("project:add")
    public ResponseDTO<String> add(@RequestBody @Valid ProjectAddForm addForm) {
        return projectService.add(addForm);
    }

    @Operation(summary = "更新项目 @author 1024创新实验室")
    @PostMapping("/project/update")
    @SaCheckPermission("project:update")
    public ResponseDTO<String> update(@RequestBody @Valid ProjectUpdateForm updateForm) {
        return projectService.update(updateForm);
    }

    @Operation(summary = "删除项目 @author 1024创新实验室")
    @GetMapping("/project/delete/{projectId}")
    @SaCheckPermission("project:delete")
    public ResponseDTO<String> delete(@PathVariable Long projectId) {
        return projectService.delete(projectId);
    }

    @Operation(summary = "更新项目状态 @author 1024创新实验室")
    @GetMapping("/project/updateStatus/{projectId}/{projectStatus}")
    @SaCheckPermission("project:update")
    public ResponseDTO<String> updateStatus(@PathVariable Long projectId, @PathVariable Integer projectStatus) {
        return projectService.updateStatus(projectId, projectStatus);
    }

    @Operation(summary = "查询项目出库材料明细 @author 1024创新实验室")
    @GetMapping("/project/outboundMaterials/{projectId}")
    @SaCheckPermission("project:query")
    public ResponseDTO<List<ProjectOutboundMaterialVO>> outboundMaterials(@PathVariable Long projectId) {
        return projectService.getOutboundMaterials(projectId);
    }
}
