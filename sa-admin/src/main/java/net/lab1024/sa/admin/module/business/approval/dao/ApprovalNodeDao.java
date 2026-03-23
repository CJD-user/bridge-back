package net.lab1024.sa.admin.module.business.approval.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalNodeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批节点 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface ApprovalNodeDao extends BaseMapper<ApprovalNodeEntity> {

    List<ApprovalNodeEntity> queryByFlowId(@Param("approvalFlowId") Long approvalFlowId);

    void deleteByFlowId(@Param("approvalFlowId") Long approvalFlowId);

    void batchInsert(@Param("list") List<ApprovalNodeEntity> list);
}
