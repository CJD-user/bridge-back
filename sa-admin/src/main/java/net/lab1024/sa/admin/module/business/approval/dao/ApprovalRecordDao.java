package net.lab1024.sa.admin.module.business.approval.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.business.approval.domain.entity.ApprovalRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 审批记录 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface ApprovalRecordDao extends BaseMapper<ApprovalRecordEntity> {

    List<ApprovalRecordEntity> queryByBusinessId(@Param("businessType") Integer businessType, @Param("businessId") Long businessId);

    ApprovalRecordEntity selectByBusinessAndNode(@Param("businessType") Integer businessType, @Param("businessId") Long businessId, @Param("nodeOrder") Integer nodeOrder);
}
