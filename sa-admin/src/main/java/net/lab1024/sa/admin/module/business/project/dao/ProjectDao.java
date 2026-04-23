package net.lab1024.sa.admin.module.business.project.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lab1024.sa.admin.module.business.project.domain.entity.ProjectEntity;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectOutboundMaterialVO;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目信息 DAO
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Mapper
public interface ProjectDao extends BaseMapper<ProjectEntity> {

    List<ProjectVO> query(Page<?> page, @Param("query") ProjectQueryForm query);

    ProjectEntity selectByProjectNo(@Param("projectNo") String projectNo);

    List<ProjectVO> queryAll(@Param("deletedFlag") Boolean deletedFlag);

    List<ProjectOutboundMaterialVO> queryOutboundMaterialsByProjectId(@Param("projectId") Long projectId);
}
