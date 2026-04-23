package net.lab1024.sa.admin.module.business.warning.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.warning.domain.entity.InventoryWarningEntity;
import net.lab1024.sa.admin.module.business.warning.domain.form.InventoryWarningQueryForm;
import net.lab1024.sa.admin.module.business.warning.domain.vo.InventoryWarningVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存预警 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface InventoryWarningDao extends BaseMapper<InventoryWarningEntity> {

    List<InventoryWarningVO> query(Page<?> page, @Param("query") InventoryWarningQueryForm query);

    InventoryWarningEntity selectByMaterialAndType(@Param("materialId") Long materialId, @Param("warningType") Integer warningType, @Param("handleStatus") Integer handleStatus);
}
