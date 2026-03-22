package net.lab1024.sa.admin.module.business.material.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 材料分类 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface MaterialCategoryDao extends BaseMapper<MaterialCategoryEntity> {

    List<MaterialCategoryEntity> queryByParentId(@Param("parentIdList") List<Long> parentIdList,
                                                  @Param("deletedFlag") Boolean deletedFlag);

    List<MaterialCategoryEntity> queryAll(@Param("deletedFlag") Boolean deletedFlag);

    MaterialCategoryEntity selectByCategoryCode(@Param("categoryCode") String categoryCode);

    MaterialCategoryEntity selectOne(MaterialCategoryEntity entity);
}
