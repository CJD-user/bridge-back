package net.lab1024.sa.admin.module.business.inbound.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.inbound.domain.form.InboundRecordAddForm;
import net.lab1024.sa.admin.module.business.inbound.domain.form.InboundRecordQueryForm;
import net.lab1024.sa.admin.module.business.inbound.domain.vo.InboundRecordVO;
import net.lab1024.sa.admin.module.business.inbound.service.InboundRecordService;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * 入库记录 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_INBOUND_RECORD)
public class InboundRecordController {

    @Resource
    private InboundRecordService inboundRecordService;

    @Operation(summary = "分页查询入库记录 @author 1024创新实验室")
    @PostMapping("/inboundRecord/query")
    @SaCheckPermission("inboundRecord:query")
    public ResponseDTO<PageResult<InboundRecordVO>> query(@RequestBody @Valid InboundRecordQueryForm queryForm) {
        return inboundRecordService.query(queryForm);
    }

    @Operation(summary = "添加入库记录 @author 1024创新实验室")
    @PostMapping("/inboundRecord/add")
    @SaCheckPermission("inboundRecord:add")
    public ResponseDTO<String> add(@RequestBody @Valid InboundRecordAddForm addForm) {
        return inboundRecordService.add(addForm);
    }
}
