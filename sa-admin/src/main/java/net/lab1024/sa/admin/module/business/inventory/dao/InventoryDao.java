package net.lab1024.sa.admin.module.business.inventory.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.inventory.domain.entity.InventoryEntity;
import net.lab1024.sa.admin.module.business.inventory.domain.form.InventoryQueryForm;
import net.lab1024.sa.admin.module.business.inventory.domain.vo.InventoryVO;
import net.lab1024.sa.admin.module.business.inventory.domain.vo.InventoryWarningVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 库存 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface InventoryDao extends BaseMapper<InventoryEntity> {

    List<InventoryVO> query(Page<?> page, @Param("query") InventoryQueryForm query);

    InventoryEntity selectByMaterialIdAndBatchNo(@Param("materialId") Long materialId, @Param("batchNo") String batchNo);

    List<InventoryWarningVO> queryWarningList(@Param("warningStatus") Integer warningStatus);

    int updateWarningStatus(@Param("inventoryId") Long inventoryId, @Param("warningStatus") Integer warningStatus);

    List<InventoryVO> queryByMaterialId(@Param("materialId") Long materialId);

    List<InventoryEntity> queryByMaterialIdRaw(@Param("materialId") Long materialId);
}
