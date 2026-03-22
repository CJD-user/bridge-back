package net.lab1024.sa.admin.module.business.supplier.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.supplier.domain.entity.SupplierEvaluateEntity;
import net.lab1024.sa.admin.module.business.supplier.domain.form.SupplierEvaluateQueryForm;
import net.lab1024.sa.admin.module.business.supplier.domain.vo.SupplierEvaluateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商评价 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface SupplierEvaluateDao extends BaseMapper<SupplierEvaluateEntity> {

    List<SupplierEvaluateVO> query(Page<?> page, @Param("query") SupplierEvaluateQueryForm query);

    List<SupplierEvaluateVO> queryBySupplierId(@Param("supplierId") Long supplierId);
}
