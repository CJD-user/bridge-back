package net.lab1024.sa.admin.module.business.supplier.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.supplier.dao.SupplierDao;
import net.lab1024.sa.admin.module.business.supplier.domain.entity.SupplierEntity;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierAddForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierQueryForm;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierUpdateForm;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierPurchaseRecordVO;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierVO;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 供应商 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class SupplierService {

    @Resource
    private SupplierDao supplierDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(SupplierAddForm addForm) {
        SupplierEntity existEntity = supplierDao.selectBySupplierCode(addForm.getSupplierCode());
        if (existEntity != null) {
            return ResponseDTO.error(UserErrorCode.ALREADY_EXIST, "供应商编码已存在");
        }

        SupplierEntity supplierEntity = SmartBeanUtil.copy(addForm, SupplierEntity.class);
        supplierEntity.setDeletedFlag(Boolean.FALSE);
        supplierEntity.setDisabledFlag(Boolean.FALSE);
        if (supplierEntity.getRating() == null) {
            supplierEntity.setRating(0);
        }
        if (supplierEntity.getTotalSupplyCount() == null) {
            supplierEntity.setTotalSupplyCount(0);
        }
        if (supplierEntity.getTotalSupplyAmount() == null) {
            supplierEntity.setTotalSupplyAmount(java.math.BigDecimal.ZERO);
        }
        supplierDao.insert(supplierEntity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(SupplierUpdateForm updateForm) {
        SupplierEntity existEntity = supplierDao.selectById(updateForm.getSupplierId());
        if (existEntity == null || existEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }

        SupplierEntity codeEntity = supplierDao.selectBySupplierCode(updateForm.getSupplierCode());
        if (codeEntity != null && !codeEntity.getSupplierId().equals(updateForm.getSupplierId())) {
            return ResponseDTO.error(UserErrorCode.ALREADY_EXIST, "供应商编码已存在");
        }

        SupplierEntity supplierEntity = SmartBeanUtil.copy(updateForm, SupplierEntity.class);
        supplierDao.updateById(supplierEntity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long supplierId) {
        SupplierEntity supplierEntity = supplierDao.selectById(supplierId);
        if (supplierEntity == null || supplierEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }
        batchDelete(Collections.singletonList(supplierId));
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> batchDelete(List<Long> supplierIdList) {
        if (CollectionUtils.isEmpty(supplierIdList)) {
            return ResponseDTO.ok();
        }
        supplierDao.batchUpdateDeleted(supplierIdList, Boolean.TRUE);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> updateDisabledFlag(Long supplierId, Boolean disabledFlag) {
        SupplierEntity supplierEntity = supplierDao.selectById(supplierId);
        if (supplierEntity == null || supplierEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }
        SupplierEntity updateEntity = new SupplierEntity();
        updateEntity.setSupplierId(supplierId);
        updateEntity.setDisabledFlag(disabledFlag);
        supplierDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<SupplierVO>> query(SupplierQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<SupplierVO> list = supplierDao.query(page, queryForm);
        PageResult<SupplierVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<SupplierVO> getDetail(Long supplierId) {
        SupplierEntity supplierEntity = supplierDao.selectById(supplierId);
        if (supplierEntity == null || supplierEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }
        SupplierVO supplierVO = SmartBeanUtil.copy(supplierEntity, SupplierVO.class);
        return ResponseDTO.ok(supplierVO);
    }

    public ResponseDTO<List<SupplierVO>> listAll() {
        List<SupplierEntity> supplierEntityList = supplierDao.selectList(null);
        List<SupplierVO> list = supplierEntityList.stream()
                .filter(e -> !e.getDeletedFlag() && !e.getDisabledFlag())
                .map(e -> SmartBeanUtil.copy(e, SupplierVO.class))
                .collect(Collectors.toList());
        return ResponseDTO.ok(list);
    }

    private static final String[] PURCHASE_TYPE_NAMES = {"", "常规采购", "紧急采购", "补货采购"};
    private static final String[] APPROVAL_STATUS_NAMES = {"待提交", "审批中", "已通过", "已驳回", "已撤回"};

    public ResponseDTO<List<SupplierPurchaseRecordVO>> getPurchaseRecords(Long supplierId) {
        SupplierEntity supplierEntity = supplierDao.selectById(supplierId);
        if (supplierEntity == null || supplierEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("供应商不存在");
        }

        List<SupplierPurchaseRecordVO> list = supplierDao.queryPurchaseRecordsBySupplierId(supplierId);
        list.forEach(e -> {
            if (e.getPurchaseType() != null && e.getPurchaseType() >= 1 && e.getPurchaseType() <= 3) {
                e.setPurchaseTypeName(PURCHASE_TYPE_NAMES[e.getPurchaseType()]);
            }
            if (e.getApprovalStatus() != null && e.getApprovalStatus() >= 0 && e.getApprovalStatus() <= 4) {
                e.setApprovalStatusName(APPROVAL_STATUS_NAMES[e.getApprovalStatus()]);
            }
        });
        return ResponseDTO.ok(list);
    }
}
