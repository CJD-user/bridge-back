package net.lab1024.sa.admin.module.business.material.service;

import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.material.dao.MaterialCategoryDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialCategoryEntity;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialCategoryAddForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialCategoryTreeQueryForm;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialCategoryUpdateForm;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialCategoryTreeVO;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialCategoryVO;
import net.lab1024.sa.base.common.code.UserErrorCode;
import net.lab1024.sa.base.common.constant.StringConst;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 材料分类 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class MaterialCategoryService {

    private static final Long DEFAULT_PARENT_ID = 0L;

    @Resource
    private MaterialCategoryDao materialCategoryDao;

    public ResponseDTO<String> add(MaterialCategoryAddForm addForm) {
        MaterialCategoryEntity categoryEntity = SmartBeanUtil.copy(addForm, MaterialCategoryEntity.class);
        ResponseDTO<String> checkResult = this.checkCategory(categoryEntity, false);
        if (!checkResult.getOk()) {
            return checkResult;
        }

        Long parentId = addForm.getParentId() == null ? DEFAULT_PARENT_ID : addForm.getParentId();
        categoryEntity.setParentId(parentId);
        categoryEntity.setSort(addForm.getSort() == null ? 0 : addForm.getSort());
        categoryEntity.setDeletedFlag(false);
        if (categoryEntity.getDisabledFlag() == null) {
            categoryEntity.setDisabledFlag(false);
        }

        materialCategoryDao.insert(categoryEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> update(MaterialCategoryUpdateForm updateForm) {
        Long categoryId = updateForm.getMaterialCategoryId();
        MaterialCategoryEntity existEntity = materialCategoryDao.selectById(categoryId);
        if (existEntity == null || existEntity.getDeletedFlag()) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        MaterialCategoryEntity categoryEntity = SmartBeanUtil.copy(updateForm, MaterialCategoryEntity.class);
        categoryEntity.setParentId(existEntity.getParentId());

        ResponseDTO<String> checkResult = this.checkCategory(categoryEntity, true);
        if (!checkResult.getOk()) {
            return checkResult;
        }

        materialCategoryDao.updateById(categoryEntity);
        return ResponseDTO.ok();
    }

    private ResponseDTO<String> checkCategory(MaterialCategoryEntity categoryEntity, boolean isUpdate) {
        Long parentId = categoryEntity.getParentId();
        Long categoryId = categoryEntity.getMaterialCategoryId();

        if (parentId != null) {
            if (Objects.equals(categoryId, parentId)) {
                return ResponseDTO.userErrorParam("父级分类不能是自己");
            }
            if (!Objects.equals(parentId, DEFAULT_PARENT_ID)) {
                MaterialCategoryEntity parentEntity = materialCategoryDao.selectById(parentId);
                if (parentEntity == null || parentEntity.getDeletedFlag()) {
                    return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST, "父级分类不存在");
                }
            }
        } else {
            parentId = DEFAULT_PARENT_ID;
        }

        MaterialCategoryEntity queryEntity = new MaterialCategoryEntity();
        queryEntity.setParentId(parentId);
        queryEntity.setCategoryName(categoryEntity.getCategoryName());
        queryEntity.setDeletedFlag(false);
        queryEntity = materialCategoryDao.selectOne(queryEntity);
        if (queryEntity != null) {
            if (isUpdate) {
                if (!Objects.equals(queryEntity.getMaterialCategoryId(), categoryId)) {
                    return ResponseDTO.userErrorParam("同级下已存在相同名称的分类");
                }
            } else {
                return ResponseDTO.userErrorParam("同级下已存在相同名称的分类");
            }
        }

        if (StringUtils.isNotBlank(categoryEntity.getCategoryCode())) {
            MaterialCategoryEntity codeEntity = materialCategoryDao.selectByCategoryCode(categoryEntity.getCategoryCode());
            if (codeEntity != null) {
                if (isUpdate) {
                    if (!Objects.equals(codeEntity.getMaterialCategoryId(), categoryId)) {
                        return ResponseDTO.userErrorParam("分类编码已存在");
                    }
                } else {
                    return ResponseDTO.userErrorParam("分类编码已存在");
                }
            }
        }

        return ResponseDTO.ok();
    }

    public ResponseDTO<MaterialCategoryVO> getDetail(Long materialCategoryId) {
        MaterialCategoryEntity categoryEntity = materialCategoryDao.selectById(materialCategoryId);
        if (categoryEntity == null || categoryEntity.getDeletedFlag()) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }
        MaterialCategoryVO categoryVO = SmartBeanUtil.copy(categoryEntity, MaterialCategoryVO.class);
        return ResponseDTO.ok(categoryVO);
    }

    public ResponseDTO<List<MaterialCategoryTreeVO>> queryTree(MaterialCategoryTreeQueryForm queryForm) {
        Long parentId = queryForm.getParentId();
        if (parentId == null) {
            parentId = DEFAULT_PARENT_ID;
        }

        List<MaterialCategoryEntity> allCategoryList = materialCategoryDao.queryAll(false);
        List<MaterialCategoryEntity> rootCategoryList = allCategoryList.stream()
                .filter(e -> e.getParentId().equals(parentId))
                .collect(Collectors.toList());

        List<MaterialCategoryTreeVO> treeList = SmartBeanUtil.copyList(rootCategoryList, MaterialCategoryTreeVO.class);
        treeList.forEach(e -> {
            e.setLabel(e.getCategoryName());
            e.setValue(e.getMaterialCategoryId());
            e.setCategoryFullName(e.getCategoryName());
        });

        this.queryAndSetSubCategory(treeList, allCategoryList);
        return ResponseDTO.ok(treeList);
    }

    private void queryAndSetSubCategory(List<MaterialCategoryTreeVO> treeList, List<MaterialCategoryEntity> allCategoryList) {
        if (CollectionUtils.isEmpty(treeList)) {
            return;
        }

        List<Long> parentIdList = treeList.stream()
                .map(MaterialCategoryTreeVO::getValue)
                .collect(Collectors.toList());

        List<MaterialCategoryEntity> subCategoryList = allCategoryList.stream()
                .filter(e -> parentIdList.contains(e.getParentId()))
                .collect(Collectors.toList());

        Map<Long, List<MaterialCategoryEntity>> subCategoryMap = subCategoryList.stream()
                .collect(Collectors.groupingBy(MaterialCategoryEntity::getParentId));

        treeList.forEach(e -> {
            List<MaterialCategoryEntity> childrenEntityList = subCategoryMap.getOrDefault(e.getValue(), Lists.newArrayList());
            List<MaterialCategoryTreeVO> childrenVOList = SmartBeanUtil.copyList(childrenEntityList, MaterialCategoryTreeVO.class);
            childrenVOList.forEach(item -> {
                item.setLabel(item.getCategoryName());
                item.setValue(item.getMaterialCategoryId());
                item.setCategoryFullName(e.getCategoryFullName() + StringConst.SEPARATOR_SLASH + item.getCategoryName());
            });
            this.queryAndSetSubCategory(childrenVOList, allCategoryList);
            e.setChildren(childrenVOList);
        });
    }

    public ResponseDTO<String> delete(Long materialCategoryId) {
        MaterialCategoryEntity categoryEntity = materialCategoryDao.selectById(materialCategoryId);
        if (categoryEntity == null || categoryEntity.getDeletedFlag()) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        List<MaterialCategoryEntity> subCategoryList = materialCategoryDao.queryByParentId(
                Lists.newArrayList(materialCategoryId), false);
        if (CollectionUtils.isNotEmpty(subCategoryList)) {
            return ResponseDTO.userErrorParam("请先删除子级分类");
        }

        MaterialCategoryEntity updateEntity = new MaterialCategoryEntity();
        updateEntity.setMaterialCategoryId(materialCategoryId);
        updateEntity.setDeletedFlag(true);
        materialCategoryDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<String> updateDisabledFlag(Long materialCategoryId, Boolean disabledFlag) {
        MaterialCategoryEntity categoryEntity = materialCategoryDao.selectById(materialCategoryId);
        if (categoryEntity == null || categoryEntity.getDeletedFlag()) {
            return ResponseDTO.error(UserErrorCode.DATA_NOT_EXIST);
        }

        MaterialCategoryEntity updateEntity = new MaterialCategoryEntity();
        updateEntity.setMaterialCategoryId(materialCategoryId);
        updateEntity.setDisabledFlag(disabledFlag);
        materialCategoryDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<List<MaterialCategoryVO>> listAll() {
        List<MaterialCategoryEntity> categoryList = materialCategoryDao.queryAll(false);
        List<MaterialCategoryVO> voList = SmartBeanUtil.copyList(categoryList, MaterialCategoryVO.class);
        return ResponseDTO.ok(voList);
    }
}
