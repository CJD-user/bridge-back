package net.lab1024.sa.admin.module.business.inventory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.inventory.dao.InventoryDao;
import net.lab1024.sa.admin.module.business.inventory.domain.entity.InventoryEntity;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryAddForm;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryAdjustForm;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryQueryForm;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryUpdateForm;
import net.lab1024.sa.admin.module.business.inventory.domain.vo.InventoryVO;
import net.lab1024.sa.admin.module.business.inventory.domain.vo.InventoryWarningVO;
import net.lab1024.sa.admin.module.business.material.dao.MaterialDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * 库存 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class InventoryService {

    private static final String[] WARNING_STATUS_NAMES = {"正常", "库存不足", "即将过期", "已过期"};

    private static final int WARNING_NORMAL = 0;
    private static final int WARNING_LOW_STOCK = 1;
    private static final int WARNING_NEAR_EXPIRATION = 2;
    private static final int WARNING_EXPIRED = 3;

    private static final int NEAR_EXPIRATION_DAYS = 30;

    @Resource
    private InventoryDao inventoryDao;

    @Resource
    private MaterialDao materialDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(InventoryAddForm addForm) {
        MaterialEntity materialEntity = materialDao.selectById(addForm.getMaterialId());
        if (materialEntity == null || materialEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }

        String batchNo = StringUtils.isNotBlank(addForm.getBatchNo()) ? addForm.getBatchNo() : null;
        InventoryEntity existEntity = inventoryDao.selectByMaterialIdAndBatchNo(addForm.getMaterialId(), batchNo);
        if (existEntity != null) {
            return ResponseDTO.userErrorParam("该材料批次已存在库存记录");
        }

        InventoryEntity inventoryEntity = SmartBeanUtil.copy(addForm, InventoryEntity.class);
        inventoryEntity.setMaterialCode(materialEntity.getMaterialCode());
        inventoryEntity.setMaterialName(materialEntity.getMaterialName());
        inventoryEntity.setSpecificationModel(materialEntity.getSpecificationModel());
        inventoryEntity.setUnit(materialEntity.getUnit());
        inventoryEntity.setLockedQuantity(BigDecimal.ZERO);
        inventoryEntity.setAvailableQuantity(addForm.getQuantity());
        inventoryEntity.setWarningStatus(WARNING_NORMAL);
        inventoryEntity.setLastInboundTime(LocalDateTime.now());

        this.calculateWarningStatus(inventoryEntity, materialEntity);
        inventoryDao.insert(inventoryEntity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(InventoryUpdateForm updateForm) {
        InventoryEntity existEntity = inventoryDao.selectById(updateForm.getInventoryId());
        if (existEntity == null) {
            return ResponseDTO.userErrorParam("库存记录不存在");
        }

        MaterialEntity materialEntity = materialDao.selectById(updateForm.getMaterialId());
        if (materialEntity == null || materialEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }

        InventoryEntity inventoryEntity = SmartBeanUtil.copy(updateForm, InventoryEntity.class);
        inventoryEntity.setMaterialCode(materialEntity.getMaterialCode());
        inventoryEntity.setMaterialName(materialEntity.getMaterialName());
        inventoryEntity.setSpecificationModel(materialEntity.getSpecificationModel());
        inventoryEntity.setUnit(materialEntity.getUnit());
        inventoryEntity.setAvailableQuantity(updateForm.getQuantity().subtract(existEntity.getLockedQuantity()));
        this.calculateWarningStatus(inventoryEntity, materialEntity);
        inventoryDao.updateById(inventoryEntity);
        return ResponseDTO.ok();
    }

    private void calculateWarningStatus(InventoryEntity inventoryEntity, MaterialEntity materialEntity) {
        int warningStatus = WARNING_NORMAL;
        LocalDate today = LocalDate.now();

        if (inventoryEntity.getExpirationDate() != null) {
            if (inventoryEntity.getExpirationDate().isBefore(today)) {
                warningStatus = WARNING_EXPIRED;
            } else if (inventoryEntity.getExpirationDate().isBefore(today.plusDays(NEAR_EXPIRATION_DAYS))) {
                warningStatus = WARNING_NEAR_EXPIRATION;
            }
        }

        if (warningStatus == WARNING_NORMAL && materialEntity.getSafetyStockThreshold() != null) {
            if (inventoryEntity.getAvailableQuantity().compareTo(materialEntity.getSafetyStockThreshold()) < 0) {
                warningStatus = WARNING_LOW_STOCK;
            }
        }

        inventoryEntity.setWarningStatus(warningStatus);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> adjust(InventoryAdjustForm adjustForm) {
        InventoryEntity inventoryEntity = inventoryDao.selectById(adjustForm.getInventoryId());
        if (inventoryEntity == null) {
            return ResponseDTO.userErrorParam("库存记录不存在");
        }

        BigDecimal newQuantity = inventoryEntity.getQuantity().add(adjustForm.getAdjustQuantity());
        if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseDTO.userErrorParam("调整后库存数量不能为负数");
        }

        BigDecimal newAvailableQuantity = inventoryEntity.getAvailableQuantity().add(adjustForm.getAdjustQuantity());
        if (newAvailableQuantity.compareTo(BigDecimal.ZERO) < 0) {
            return ResponseDTO.userErrorParam("可用数量不足，存在锁定库存");
        }

        InventoryEntity updateEntity = new InventoryEntity();
        updateEntity.setInventoryId(adjustForm.getInventoryId());
        updateEntity.setQuantity(newQuantity);
        updateEntity.setAvailableQuantity(newAvailableQuantity);

        if (adjustForm.getAdjustType() == 1) {
            updateEntity.setLastInboundTime(LocalDateTime.now());
        } else if (adjustForm.getAdjustType() == 2) {
            updateEntity.setLastOutboundTime(LocalDateTime.now());
        }

        MaterialEntity materialEntity = materialDao.selectById(inventoryEntity.getMaterialId());
        this.calculateWarningStatus(updateEntity, materialEntity != null ? materialEntity : new MaterialEntity());

        inventoryDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<InventoryVO>> query(InventoryQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<InventoryVO> list = inventoryDao.query(page, queryForm);
        this.fillWarningStatusName(list);
        PageResult<InventoryVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<InventoryVO> getDetail(Long inventoryId) {
        InventoryEntity inventoryEntity = inventoryDao.selectById(inventoryId);
        if (inventoryEntity == null) {
            return ResponseDTO.userErrorParam("库存记录不存在");
        }
        InventoryVO inventoryVO = SmartBeanUtil.copy(inventoryEntity, InventoryVO.class);
        if (inventoryEntity.getWarningStatus() != null && inventoryEntity.getWarningStatus() >= 0 && inventoryEntity.getWarningStatus() <= 3) {
            inventoryVO.setWarningStatusName(WARNING_STATUS_NAMES[inventoryEntity.getWarningStatus()]);
        }
        return ResponseDTO.ok(inventoryVO);
    }

    public ResponseDTO<List<InventoryVO>> queryByMaterialId(Long materialId) {
        List<InventoryVO> list = inventoryDao.queryByMaterialId(materialId);
        this.fillWarningStatusName(list);
        return ResponseDTO.ok(list);
    }

    public ResponseDTO<List<InventoryWarningVO>> queryWarningList(Integer warningStatus) {
        List<InventoryWarningVO> list = inventoryDao.queryWarningList(warningStatus);
        if (CollectionUtils.isNotEmpty(list)) {
            LocalDate today = LocalDate.now();
            list.forEach(e -> {
                if (e.getWarningStatus() != null && e.getWarningStatus() >= 1 && e.getWarningStatus() <= 3) {
                    e.setWarningStatusName(WARNING_STATUS_NAMES[e.getWarningStatus()]);
                }
                if (e.getExpirationDate() != null) {
                    long days = ChronoUnit.DAYS.between(today, e.getExpirationDate());
                    e.setRemainingDays((int) days);
                }
            });
        }
        return ResponseDTO.ok(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> refreshWarningStatus() {
        List<InventoryEntity> allInventory = inventoryDao.selectList(null);
        LocalDate today = LocalDate.now();

        for (InventoryEntity inventory : allInventory) {
            int warningStatus = WARNING_NORMAL;

            if (inventory.getExpirationDate() != null) {
                if (inventory.getExpirationDate().isBefore(today)) {
                    warningStatus = WARNING_EXPIRED;
                } else if (inventory.getExpirationDate().isBefore(today.plusDays(NEAR_EXPIRATION_DAYS))) {
                    warningStatus = WARNING_NEAR_EXPIRATION;
                }
            }

            if (warningStatus == WARNING_NORMAL) {
                MaterialEntity materialEntity = materialDao.selectById(inventory.getMaterialId());
                if (materialEntity != null && materialEntity.getSafetyStockThreshold() != null) {
                    if (inventory.getAvailableQuantity().compareTo(materialEntity.getSafetyStockThreshold()) < 0) {
                        warningStatus = WARNING_LOW_STOCK;
                    }
                }
            }

            if (!warningStatusEquals(inventory.getWarningStatus(), warningStatus)) {
                inventoryDao.updateWarningStatus(inventory.getInventoryId(), warningStatus);
            }
        }

        return ResponseDTO.ok();
    }

    private boolean warningStatusEquals(Integer status1, Integer status2) {
        if (status1 == null && status2 == null) return true;
        if (status1 == null || status2 == null) return false;
        return status1.equals(status2);
    }

    private void fillWarningStatusName(List<InventoryVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getWarningStatus() != null && e.getWarningStatus() >= 0 && e.getWarningStatus() <= 3) {
                e.setWarningStatusName(WARNING_STATUS_NAMES[e.getWarningStatus()]);
            }
        });
    }
}
