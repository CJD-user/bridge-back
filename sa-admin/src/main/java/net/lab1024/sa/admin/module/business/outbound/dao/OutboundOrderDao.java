package net.lab1024.sa.admin.module.business.outbound.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.outbound.domain.entity.OutboundOrderEntity;
import net.lab1024.sa.admin.module.business.outbound.domain.form.OutboundOrderQueryForm;
import net.lab1024.sa.admin.module.business.outbound.domain.vo.OutboundOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 出库申请单 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface OutboundOrderDao extends BaseMapper<OutboundOrderEntity> {

    List<OutboundOrderVO> query(Page<?> page, @Param("query") OutboundOrderQueryForm query);

    OutboundOrderEntity selectByOrderNo(@Param("outboundOrderNo") String outboundOrderNo);

    void batchInsertItems(@Param("list") List<net.lab1024.sa.admin.module.business.outbound.domain.entity.OutboundOrderItemEntity> list);

    List<net.lab1024.sa.admin.module.business.outbound.domain.entity.OutboundOrderItemEntity> queryItemsByOrderId(@Param("outboundOrderId") Long outboundOrderId);

    void deleteItemsByOrderId(@Param("outboundOrderId") Long outboundOrderId);
}
