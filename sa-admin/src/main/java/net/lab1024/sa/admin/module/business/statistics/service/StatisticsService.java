package net.lab1024.sa.admin.module.business.statistics.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.statistics.dao.StatisticsDao;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.BaseStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.InventoryStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.OutboundStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.PurchaseStatisticsVO;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 统计 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class StatisticsService {

    private static final String TYPE_WEEK = "week";
    private static final String TYPE_MONTH = "month";
    private static final String TYPE_YEAR = "year";

    @Resource
    private StatisticsDao statisticsDao;

    public ResponseDTO<List<InventoryStatisticsVO>> getTop10Inventory() {
        List<InventoryStatisticsVO> list = statisticsDao.queryTop10Inventory();
        return ResponseDTO.ok(list);
    }

    public ResponseDTO<List<PurchaseStatisticsVO>> getPurchaseStatistics(String type) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = getStartDate(type, endDate);
        List<PurchaseStatisticsVO> list = statisticsDao.queryPurchaseStatistics(startDate, endDate);
        return ResponseDTO.ok(list);
    }

    public ResponseDTO<List<OutboundStatisticsVO>> getOutboundStatistics(String type) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = getStartDate(type, endDate);
        List<OutboundStatisticsVO> list = statisticsDao.queryOutboundStatistics(startDate, endDate);
        return ResponseDTO.ok(list);
    }

    public ResponseDTO<BaseStatisticsVO> getBaseStatistics() {
        BaseStatisticsVO vo = statisticsDao.queryBaseStatistics();
        return ResponseDTO.ok(vo);
    }

    private LocalDate getStartDate(String type, LocalDate endDate) {
        switch (type) {
            case TYPE_WEEK:
                return endDate.minusDays(7);
            case TYPE_MONTH:
                return endDate.minusMonths(1);
            case TYPE_YEAR:
                return endDate.minusYears(1);
            default:
                return endDate.minusDays(7);
        }
    }
}
