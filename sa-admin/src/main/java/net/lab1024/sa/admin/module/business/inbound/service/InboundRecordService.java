package net.lab1024.sa.admin.module.business.inbound.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.inbound.dao.InboundRecordDao;
import net.lab1024.sa.admin.module.business.inbound.domain.entity.InboundRecordEntity;
import net.lab1024.sa.admin.module.business.inbound.domain.form.InboundRecordAddForm;
import net.lab1024.sa.admin.module.business.inbound.domain.form.InboundRecordQueryForm;
import net.lab1024.sa.admin.module.business.inbound.domain.vo.InboundRecordVO;
import net.lab1024.sa.admin.module.business.material.dao.MaterialDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 入库记录 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class InboundRecordService {

    private static final String[] INBOUND_TYPE_NAMES = {"", "采购入库", "退货入库", "其他"};

    @Resource
    private InboundRecordDao inboundRecordDao;

    @Resource
    private MaterialDao materialDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(InboundRecordAddForm addForm) {
        MaterialEntity materialEntity = materialDao.selectById(addForm.getMaterialId());
        if (materialEntity == null || materialEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }

        InboundRecordEntity entity = SmartBeanUtil.copy(addForm, InboundRecordEntity.class);
        entity.setMaterialCode(materialEntity.getMaterialCode());
        entity.setMaterialName(materialEntity.getMaterialName());
        entity.setSpecificationModel(materialEntity.getSpecificationModel());
        entity.setUnit(materialEntity.getUnit());
        entity.setInboundType(addForm.getInboundType() != null ? addForm.getInboundType() : 1);
        entity.setDeletedFlag(false);

        if (entity.getUnitPrice() == null) {
            entity.setUnitPrice(materialEntity.getUnitPrice());
        }
        if (entity.getUnitPrice() != null) {
            entity.setTotalPrice(entity.getUnitPrice().multiply(addForm.getQuantity()));
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            entity.setCreateUserId(requestEmployee.getEmployeeId());
            entity.setCreateUserName(requestEmployee.getActualName());
        }

        inboundRecordDao.insert(entity);

        BigDecimal newStock = (materialEntity.getCurrentStock() != null ? materialEntity.getCurrentStock() : BigDecimal.ZERO).add(addForm.getQuantity());
        MaterialEntity updateMaterial = new MaterialEntity();
        updateMaterial.setMaterialId(materialEntity.getMaterialId());
        updateMaterial.setCurrentStock(newStock);
        materialDao.updateById(updateMaterial);

        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<InboundRecordVO>> query(InboundRecordQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<InboundRecordVO> list = inboundRecordDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<InboundRecordVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    private void fillDictNames(List<InboundRecordVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getInboundType() != null && e.getInboundType() >= 1 && e.getInboundType() <= 3) {
                e.setInboundTypeName(INBOUND_TYPE_NAMES[e.getInboundType()]);
            }
        });
    }
}
