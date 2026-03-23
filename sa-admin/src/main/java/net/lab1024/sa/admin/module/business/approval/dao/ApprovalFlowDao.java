package net.lab1024.sa.admin.module.business.approval.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 审批流程 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface ApprovalFlowDao extends BaseMapper<ApprovalFlowEntity> {

    ApprovalFlowEntity selectByFlowCode(@Param("flowCode") String flowCode);
}
