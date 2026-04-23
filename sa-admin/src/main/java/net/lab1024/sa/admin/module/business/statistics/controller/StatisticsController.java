package net.lab1024.sa.admin.module.business.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import net.lab1024.sa.admin.constant.AdminSwaggerTagConst;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.BaseStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.InventoryStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.OutboundStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.domain.vo.PurchaseStatisticsVO;
import net.lab1024.sa.admin.module.business.statistics.service.StatisticsService;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 统计 Controller
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@RestController
@Tag(name = AdminSwaggerTagConst.Business.MANAGER_STATISTICS)
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    @Operation(summary = "Top10库存统计 @author 1024创新实验室")
    @GetMapping("/statistics/top10Inventory")
    public ResponseDTO<List<InventoryStatisticsVO>> top10Inventory() {
        return statisticsService.getTop10Inventory();
    }

    @Operation(summary = "采购统计 @author 1024创新实验室")
    @GetMapping("/statistics/purchase/{type}")
    public ResponseDTO<List<PurchaseStatisticsVO>> purchaseStatistics(@PathVariable String type) {
        return statisticsService.getPurchaseStatistics(type);
    }

    @Operation(summary = "出库统计 @author 1024创新实验室")
    @GetMapping("/statistics/outbound/{type}")
    public ResponseDTO<List<OutboundStatisticsVO>> outboundStatistics(@PathVariable String type) {
        return statisticsService.getOutboundStatistics(type);
    }

    @Operation(summary = "基础数据统计 @author 1024创新实验室")
    @GetMapping("/statistics/base")
    public ResponseDTO<BaseStatisticsVO> baseStatistics() {
        return statisticsService.getBaseStatistics();
    }
}
