package net.lab1024.sa.admin.module.business.supplier.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.supplier.dao.SupplierDao;
import net.lab1024.sa.admin.module.business.supplier.dao.SupplierEvaluateDao;
import net.lab1024.sa.admin.module.business.supplier.domain.entity.SupplierEntity;
import net.lab1024.sa.admin.module.business.supplier.domain.entity.SupplierEvaluateEntity;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateAddForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateQueryForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateUpdateForm;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierEvaluateVO;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 供应商评价 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class SupplierEvaluateService {

    private static final String[] EVALUATE_TYPE_NAMES = {"", "季度评价", "年度评价", "项目评价"};

    @Resource
    private SupplierEvaluateDao supplierEvaluateDao;

    @Resource
    private SupplierDao supplierDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(SupplierEvaluateAddForm addForm) {
        SupplierEntity supplierEntity = supplierDao.selectById(addForm.getSupplierId());
        if (supplierEntity == null || supplierEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }

        SupplierEvaluateEntity evaluateEntity = SmartBeanUtil.copy(addForm, SupplierEvaluateEntity.class);
        evaluateEntity.setDeletedFlag(Boolean.FALSE);
        evaluateEntity.setTotalScore(this.calculateTotalScore(addForm));

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            evaluateEntity.setCreateUserId(requestEmployee.getEmployeeId());
            evaluateEntity.setCreateUserName(requestEmployee.getActualName());
        }

        supplierEvaluateDao.insert(evaluateEntity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(SupplierEvaluateUpdateForm updateForm) {
        SupplierEvaluateEntity existEntity = supplierEvaluateDao.selectById(updateForm.getEvaluateId());
        if (existEntity == null || existEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("评价记录不存在");
        }

        SupplierEntity supplierEntity = supplierDao.selectById(updateForm.getSupplierId());
        if (supplierEntity == null || supplierEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }

        SupplierEvaluateEntity evaluateEntity = SmartBeanUtil.copy(updateForm, SupplierEvaluateEntity.class);
        evaluateEntity.setTotalScore(this.calculateTotalScore(updateForm));
        supplierEvaluateDao.updateById(evaluateEntity);
        return ResponseDTO.ok();
    }

    private BigDecimal calculateTotalScore(SupplierEvaluateAddForm form) {
        int count = 0;
        BigDecimal sum = BigDecimal.ZERO;

        if (form.getQualityScore() != null) {
            sum = sum.add(form.getQualityScore());
            count++;
        }
        if (form.getDeliveryScore() != null) {
            sum = sum.add(form.getDeliveryScore());
            count++;
        }
        if (form.getServiceScore() != null) {
            sum = sum.add(form.getServiceScore());
            count++;
        }
        if (form.getPriceScore() != null) {
            sum = sum.add(form.getPriceScore());
            count++;
        }

        if (count > 0) {
            return sum.divide(BigDecimal.valueOf(count), 1, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long evaluateId) {
        SupplierEvaluateEntity evaluateEntity = supplierEvaluateDao.selectById(evaluateId);
        if (evaluateEntity == null || evaluateEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("评价记录不存在");
        }

        SupplierEvaluateEntity updateEntity = new SupplierEvaluateEntity();
        updateEntity.setEvaluateId(evaluateId);
        updateEntity.setDeletedFlag(true);
        supplierEvaluateDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<SupplierEvaluateVO>> query(SupplierEvaluateQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SupplierEvaluateVO> list = supplierEvaluateDao.query(page, queryForm);
        this.fillEvaluateTypeName(list);
        PageResult<SupplierEvaluateVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<List<SupplierEvaluateVO>> queryBySupplierId(Long supplierId) {
        List<SupplierEvaluateVO> list = supplierEvaluateDao.queryBySupplierId(supplierId);
        this.fillEvaluateTypeName(list);
        return ResponseDTO.ok(list);
    }

    public ResponseDTO<SupplierEvaluateVO> getDetail(Long evaluateId) {
        SupplierEvaluateEntity evaluateEntity = supplierEvaluateDao.selectById(evaluateId);
        if (evaluateEntity == null || evaluateEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("评价记录不存在");
        }
        SupplierEvaluateVO evaluateVO = SmartBeanUtil.copy(evaluateEntity, SupplierEvaluateVO.class);
        if (evaluateEntity.getEvaluateType() != null && evaluateEntity.getEvaluateType() >= 1 && evaluateEntity.getEvaluateType() <= 3) {
            evaluateVO.setEvaluateTypeName(EVALUATE_TYPE_NAMES[evaluateEntity.getEvaluateType()]);
        }
        if (evaluateEntity.getSupplierId() != null) {
            SupplierEntity supplierEntity = supplierDao.selectById(evaluateEntity.getSupplierId());
            if (supplierEntity != null) {
                evaluateVO.setSupplierName(supplierEntity.getSupplierName());
                evaluateVO.setSupplierCode(supplierEntity.getSupplierCode());
            }
        }
        return ResponseDTO.ok(evaluateVO);
    }

    private void fillEvaluateTypeName(List<SupplierEvaluateVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getEvaluateType() != null && e.getEvaluateType() >= 1 && e.getEvaluateType() <= 3) {
                e.setEvaluateTypeName(EVALUATE_TYPE_NAMES[e.getEvaluateType()]);
            }
        });
    }
}
