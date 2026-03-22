package net.lab1024.sa.admin.module.business.approvalFlow.dao;

import net.lab1024.sa.admin.module.business.approvalFlow.domain.entity.ApprovalFlowEntity;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowQueryForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.vo.ApprovalFlowVO;
import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 审批流程配置表 Dao
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Mapper
public interface ApprovalFlowDao extends BaseMapper<ApprovalFlowEntity> {

    /**
     * 分页 查询
     *
     * @param page
     * @param queryForm
     * @return
     */
    List<ApprovalFlowVO> queryPage(Page page, @Param("queryForm") ApprovalFlowQueryForm queryForm);

    /**
     * 更新删除状态
     */
    long updateDeleted(@Param("approvalFlowId")Long approvalFlowId,@Param("deletedFlag")boolean deletedFlag);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("idList")List<Long> idList,@Param("deletedFlag")boolean deletedFlag);

}
