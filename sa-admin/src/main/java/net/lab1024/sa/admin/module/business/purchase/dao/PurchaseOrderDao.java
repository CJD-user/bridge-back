package net.lab1024.sa.admin.module.business.purchase.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.purchase.domain.entity.PurchaseOrderEntity;
import net.lab1024.sa.admin.module.business.purchase.domain.entity.PurchaseOrderItemEntity;
import net.lab1024.sa.admin.module.business.purchase.domain.form.PurchaseOrderQueryForm;
import net.lab1024.sa.admin.module.business.purchase.domain.vo.PurchaseOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 采购申请单 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface PurchaseOrderDao extends BaseMapper<PurchaseOrderEntity> {

    List<PurchaseOrderVO> query(Page<?> page, @Param("query") PurchaseOrderQueryForm query);

    PurchaseOrderEntity selectByOrderNo(@Param("purchaseOrderNo") String purchaseOrderNo);

    void batchInsertItems(@Param("list") List<PurchaseOrderItemEntity> list);

    List<PurchaseOrderItemEntity> queryItemsByOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    void deleteItemsByOrderId(@Param("purchaseOrderId") Long purchaseOrderId);
}
