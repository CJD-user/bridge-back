package net.lab1024.sa.admin.module.business.material.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.admin.module.business.material.domain.form.MaterialQueryForm;
import net.lab1024.sa.admin.module.business.material.domain.vo.MaterialVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 材料 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface MaterialDao extends BaseMapper<MaterialEntity> {

    /**
     * 分页查询材料
     */
    List<MaterialVO> query(Page<?> page, @Param("query") MaterialQueryForm query);

    /**
     * 批量更新删除状态
     */
    void batchUpdateDeleted(@Param("materialIdList") List<Long> materialIdList, @Param("deletedFlag") Boolean deletedFlag);

    /**
     * 根据材料编码查询
     */
    MaterialEntity selectByMaterialCode(@Param("materialCode") String materialCode);
}
