package net.lab1024.sa.admin.module.business.approvalFlow.service;

import net.lab1024.sa.admin.module.business.approvalFlow.dao.ApprovalFlowDao;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.entity.ApprovalFlowEntity;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowAddForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowQueryForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.form.ApprovalFlowUpdateForm;
import net.lab1024.sa.admin.module.business.approvalFlow.domain.vo.ApprovalFlowVO;
import java.util.List;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.domain.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 审批流程配置表 Service
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Service
public class ApprovalFlowService {

    @Resource
    private ApprovalFlowDao approvalFlowDao;

    /**
     * 分页查询
     */
    public PageResult<ApprovalFlowVO> queryPage(ApprovalFlowQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ApprovalFlowVO> list = approvalFlowDao.queryPage(page, queryForm);
        return SmartPageUtil.convert2PageResult(page, list);
    }

    /**
     * 添加
     */
    public ResponseDTO<String> add(ApprovalFlowAddForm addForm) {
        ApprovalFlowEntity approvalFlowEntity = SmartBeanUtil.copy(addForm, ApprovalFlowEntity.class);
        approvalFlowDao.insert(approvalFlowEntity);
        return ResponseDTO.ok();
    }

    /**
     * 更新
     *
     */
    public ResponseDTO<String> update(ApprovalFlowUpdateForm updateForm) {
        ApprovalFlowEntity approvalFlowEntity = SmartBeanUtil.copy(updateForm, ApprovalFlowEntity.class);
        approvalFlowDao.updateById(approvalFlowEntity);
        return ResponseDTO.ok();
    }

    /**
     * 批量删除
     */
    public ResponseDTO<String> batchDelete(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)){
            return ResponseDTO.ok();
        }

        approvalFlowDao.batchUpdateDeleted(idList, true);
        return ResponseDTO.ok();
    }

    /**
     * 单个删除
     */
    public ResponseDTO<String> delete(Long approvalFlowId) {
        if (null == approvalFlowId){
            return ResponseDTO.ok();
        }

        approvalFlowDao.updateDeleted(approvalFlowId, true);
        return ResponseDTO.ok();
    }
}
