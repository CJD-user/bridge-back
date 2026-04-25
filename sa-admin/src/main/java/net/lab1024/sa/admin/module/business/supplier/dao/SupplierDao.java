package net.lab1024.sa.admin.module.business.supplier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.supplier.domain.entity.SupplierEntity;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierQueryForm;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierPurchaseRecordVO;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface SupplierDao extends BaseMapper<SupplierEntity> {

    List<SupplierVO> query(Page<?> page, @Param("query") SupplierQueryForm query);

    void batchUpdateDeleted(@Param("supplierIdList") List<Long> supplierIdList, @Param("deletedFlag") Boolean deletedFlag);

    SupplierEntity selectBySupplierCode(@Param("supplierCode") String supplierCode);

    List<SupplierPurchaseRecordVO> queryPurchaseRecordsBySupplierId(@Param("supplierId") Long supplierId);
}
