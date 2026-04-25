package net.lab1024.sa.admin.module.business.material.service;

import cn.idev.excel.FastExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.material.dao.MaterialCategoryDao;
import net.lab1024.sa.admin.module.business.material.dao.MaterialDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialCategoryEntity;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialAddForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialImportForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialQueryForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialUpdateForm;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialExcelVO;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialVO;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.exception.BusinessException;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 材料 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class MaterialService {

    @Resource
    private MaterialDao materialDao;

    @Resource
    private MaterialCategoryDao materialCategoryDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(MaterialAddForm addForm) {
        MaterialEntity existEntity = materialDao.selectByMaterialCode(addForm.getMaterialCode());
        if (existEntity != null) {
            return ResponseDTO.error(UserErrorCode.ALREADY_EXIST, "材料编码已存在");
        }
        
        ResponseDTO<String> checkResult = this.checkCategory(addForm.getMaterialCategoryId());
        if (!checkResult.getOk()) {
            return checkResult;
        }
        
        MaterialEntity materialEntity = SmartBeanUtil.copy(addForm, MaterialEntity.class);
        materialEntity.setDeletedFlag(Boolean.FALSE);
        materialEntity.setDisabledFlag(Boolean.FALSE);
        materialDao.insert(materialEntity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(MaterialUpdateForm updateForm) {
        MaterialEntity existEntity = materialDao.selectById(updateForm.getMaterialId());
        if (existEntity == null || existEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }
        
        MaterialEntity codeEntity = materialDao.selectByMaterialCode(updateForm.getMaterialCode());
        if (codeEntity != null && !codeEntity.getMaterialId().equals(updateForm.getMaterialId())) {
            return ResponseDTO.error(UserErrorCode.ALREADY_EXIST, "材料编码已存在");
        }
        
        ResponseDTO<String> checkResult = this.checkCategory(updateForm.getMaterialCategoryId());
        if (!checkResult.getOk()) {
            return checkResult;
        }
        
        MaterialEntity materialEntity = SmartBeanUtil.copy(updateForm, MaterialEntity.class);
        materialDao.updateById(materialEntity);
        return ResponseDTO.ok();
    }

    private ResponseDTO<String> checkCategory(Long categoryId) {
        if (categoryId == null) {
            return ResponseDTO.ok();
        }
        MaterialCategoryEntity categoryEntity = materialCategoryDao.selectById(categoryId);
        if (categoryEntity == null || categoryEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料分类不存在");
        }
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long materialId) {
        MaterialEntity materialEntity = materialDao.selectById(materialId);
        if (materialEntity == null || materialEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }
        batchDelete(Collections.singletonList(materialId));
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> batchDelete(List<Long> materialIdList) {
        if (CollectionUtils.isEmpty(materialIdList)) {
            return ResponseDTO.ok();
        }
        materialDao.batchUpdateDeleted(materialIdList, Boolean.TRUE);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> updateDisabledFlag(Long materialId, Boolean disabledFlag) {
        MaterialEntity materialEntity = materialDao.selectById(materialId);
        if (materialEntity == null || materialEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }
        MaterialEntity updateEntity = new MaterialEntity();
        updateEntity.setMaterialId(materialId);
        updateEntity.setDisabledFlag(disabledFlag);
        materialDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<MaterialVO>> query(MaterialQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<MaterialVO> list = materialDao.query(page, queryForm);
        PageResult<MaterialVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        
        if (pageResult.getEmptyFlag()) {
            return ResponseDTO.ok(pageResult);
        }
        
        List<Long> categoryIdList = list.stream()
                .map(MaterialVO::getMaterialCategoryId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        
        if (CollectionUtils.isNotEmpty(categoryIdList)) {
            List<MaterialCategoryEntity> categoryList = materialCategoryDao.selectBatchIds(categoryIdList);
            Map<Long, MaterialCategoryEntity> categoryMap = categoryList.stream()
                    .collect(Collectors.toMap(MaterialCategoryEntity::getMaterialCategoryId, e -> e));
            list.forEach(e -> {
                if (e.getMaterialCategoryId() != null) {
                    MaterialCategoryEntity categoryEntity = categoryMap.get(e.getMaterialCategoryId());
                    if (categoryEntity != null) {
                        e.setMaterialCategoryName(categoryEntity.getCategoryName());
                    }
                }
            });
        }
        
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<MaterialVO> getDetail(Long materialId) {
        MaterialEntity materialEntity = materialDao.selectById(materialId);
        if (materialEntity == null || materialEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("材料不存在");
        }
        MaterialVO materialVO = SmartBeanUtil.copy(materialEntity, MaterialVO.class);
        
        if (materialEntity.getMaterialCategoryId() != null) {
            MaterialCategoryEntity categoryEntity = materialCategoryDao.selectById(materialEntity.getMaterialCategoryId());
            if (categoryEntity != null) {
                materialVO.setMaterialCategoryName(categoryEntity.getCategoryName());
            }
        }
        
        return ResponseDTO.ok(materialVO);
    }

    public ResponseDTO<String> importMaterial(MultipartFile file) {
        List<MaterialImportForm> dataList;
        try {
            dataList = FastExcel.read(file.getInputStream())
                    .head(MaterialImportForm.class)
                    .sheet()
                    .doReadSync();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException("数据格式存在问题，无法读取");
        }

        if (CollectionUtils.isEmpty(dataList)) {
            return ResponseDTO.userErrorParam("数据为空");
        }

        int successCount = 0;
        int failCount = 0;
        StringBuilder failMsg = new StringBuilder();

        for (int i = 0; i < dataList.size(); i++) {
            MaterialImportForm importForm = dataList.get(i);
            int rowNum = i + 2;
            
            if (StringUtils.isBlank(importForm.getMaterialCode())) {
                failCount++;
                failMsg.append("第").append(rowNum).append("行：材料编码不能为空；");
                continue;
            }
            if (StringUtils.isBlank(importForm.getMaterialName())) {
                failCount++;
                failMsg.append("第").append(rowNum).append("行：材料名称不能为空；");
                continue;
            }
            
            MaterialEntity existEntity = materialDao.selectByMaterialCode(importForm.getMaterialCode());
            if (existEntity != null) {
                failCount++;
                failMsg.append("第").append(rowNum).append("行：材料编码已存在；");
                continue;
            }
            
            Long categoryId = null;
            if (StringUtils.isNotBlank(importForm.getCategoryName())) {
                MaterialCategoryEntity categoryEntity = materialCategoryDao.selectByCategoryName(importForm.getCategoryName());
                if (categoryEntity != null) {
                    categoryId = categoryEntity.getMaterialCategoryId();
                }
            }
            
            MaterialEntity materialEntity = SmartBeanUtil.copy(importForm, MaterialEntity.class);
            materialEntity.setMaterialCategoryId(categoryId);
            materialEntity.setDeletedFlag(Boolean.FALSE);
            materialEntity.setDisabledFlag(Boolean.FALSE);
            materialDao.insert(materialEntity);
            successCount++;
        }

        if (failCount > 0) {
            return ResponseDTO.okMsg("导入完成，成功" + successCount + "条，失败" + failCount + "条。失败原因：" + failMsg.toString());
        }
        return ResponseDTO.okMsg("导入成功，共导入" + successCount + "条数据");
    }

    public List<MaterialExcelVO> getAllMaterialForExport() {
        List<MaterialEntity> materialEntityList = materialDao.selectList(null);
        
        List<Long> categoryIdList = materialEntityList.stream()
                .map(MaterialEntity::getMaterialCategoryId)
                .filter(id -> id != null)
                .distinct()
                .collect(Collectors.toList());
        
        Map<Long, MaterialCategoryEntity> categoryMap = CollectionUtils.isNotEmpty(categoryIdList) 
                ? materialCategoryDao.selectBatchIds(categoryIdList).stream()
                        .collect(Collectors.toMap(MaterialCategoryEntity::getMaterialCategoryId, e -> e))
                : Collections.emptyMap();
        
        return materialEntityList.stream()
                .filter(e -> !e.getDeletedFlag())
                .map(e -> {
                    String categoryName = null;
                    if (e.getMaterialCategoryId() != null) {
                        MaterialCategoryEntity categoryEntity = categoryMap.get(e.getMaterialCategoryId());
                        if (categoryEntity != null) {
                            categoryName = categoryEntity.getCategoryName();
                        }
                    }
                    return MaterialExcelVO.builder()
                            .materialCode(e.getMaterialCode())
                            .materialName(e.getMaterialName())
                            .categoryName(categoryName)
                            .specificationModel(e.getSpecificationModel())
                            .unit(e.getUnit())
                            .unitPrice(e.getUnitPrice())
                            .safetyStockThreshold(e.getSafetyStockThreshold())
                            .shelfLifeDays(e.getShelfLifeDays())
                            .brand(e.getBrand())
                            .manufacturer(e.getManufacturer())
                            .remark(e.getRemark())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public ResponseDTO<List<MaterialVO>> listAll() {
        List<MaterialEntity> materialEntityList = materialDao.selectList(null);
        List<MaterialVO> list = materialEntityList.stream()
                .filter(e -> !e.getDeletedFlag() && !e.getDisabledFlag())
                .map(e -> SmartBeanUtil.copy(e, MaterialVO.class))
                .collect(Collectors.toList());
        return ResponseDTO.ok(list);
    }
}
