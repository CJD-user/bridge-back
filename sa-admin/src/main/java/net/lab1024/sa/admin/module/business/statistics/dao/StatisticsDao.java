package net.lab1024.sa.admin.module.business.statistics.dao;

import net.lab1024.sa.admin.module.business.statistics.domain.vo.BaseStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.InventoryStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.OutboundStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.PurchaseStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 统计 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface StatisticsDao {

    List<InventoryStatisticsVO> queryTop10Inventory();

    List<PurchaseStatisticsVO> queryPurchaseStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<OutboundStatisticsVO> queryOutboundStatistics(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    BaseStatisticsVO queryBaseStatistics();
}
