package net.lab1024.sa.admin.module.business.inbound.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.inbound.domain.entity.InboundRecordEntity;
import net.lab1024.sa.admin.module.business.inbound.domain.form.InboundRecordQueryForm;
import net.lab1024.sa.admin.module.business.inbound.domain.vo.InboundRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 入库记录 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface InboundRecordDao extends BaseMapper<InboundRecordEntity> {

    List<InboundRecordVO> query(Page<?> page, @Param("query") InboundRecordQueryForm query);
}
