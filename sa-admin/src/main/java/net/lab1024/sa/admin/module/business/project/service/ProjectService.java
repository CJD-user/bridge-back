package net.lab1024.sa.admin.module.business.project.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.project.dao.ProjectDao;
import net.lab1024.sa.admin.module.business.project.domain.entity.ProjectEntity;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectAddForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectQueryForm;
import net.lab1024.sa.admin.module.business.project.domain.form.ProjectUpdateForm;
import net.lab1024.sa.admin.module.business.project.domain.vo.ProjectVO;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目信息 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class ProjectService {

    private static final String[] PROJECT_TYPE_NAMES = {"", "桥梁工程", "道路工程", "隧道工程", "其他"};
    private static final String[] PROJECT_STATUS_NAMES = {"筹备中", "进行中", "已完工", "已暂停", "已取消"};

    @Resource
    private ProjectDao projectDao;

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> add(ProjectAddForm addForm) {
        ProjectEntity entity = SmartBeanUtil.copy(addForm, ProjectEntity.class);
        String projectNo = generateProjectNo();
        entity.setProjectNo(projectNo);
        entity.setProjectStatus(addForm.getProjectStatus() != null ? addForm.getProjectStatus() : 0);
        entity.setProjectType(addForm.getProjectType() != null ? addForm.getProjectType() : 1);
        entity.setDeletedFlag(false);

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        if (requestEmployee != null) {
            entity.setCreateUserId(requestEmployee.getEmployeeId());
            entity.setCreateUserName(requestEmployee.getActualName());
        }

        projectDao.insert(entity);
        return ResponseDTO.ok();
    }

    private String generateProjectNo() {
        String dateStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_PATTERN);
        long count = projectDao.selectCount(null) + 1;
        return "PRJ" + dateStr + String.format("%04d", count);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> update(ProjectUpdateForm updateForm) {
        ProjectEntity existEntity = projectDao.selectById(updateForm.getProjectId());
        if (existEntity == null || existEntity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("项目不存在");
        }

        ProjectEntity entity = SmartBeanUtil.copy(updateForm, ProjectEntity.class);
        projectDao.updateById(entity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delete(Long projectId) {
        ProjectEntity entity = projectDao.selectById(projectId);
        if (entity == null || entity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("项目不存在");
        }

        entity.setDeletedFlag(true);
        projectDao.updateById(entity);
        return ResponseDTO.ok();
    }

    public ResponseDTO<PageResult<ProjectVO>> query(ProjectQueryForm queryForm) {
        queryForm.setDeletedFlag(false);
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<ProjectVO> list = projectDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<ProjectVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    public ResponseDTO<ProjectVO> getDetail(Long projectId) {
        ProjectEntity entity = projectDao.selectById(projectId);
        if (entity == null || entity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("项目不存在");
        }

        ProjectVO vo = SmartBeanUtil.copy(entity, ProjectVO.class);
        this.fillDictNames(java.util.Collections.singletonList(vo));
        return ResponseDTO.ok(vo);
    }

    public ResponseDTO<List<ProjectVO>> queryAll() {
        List<ProjectVO> list = projectDao.queryAll(false);
        this.fillDictNames(list);
        return ResponseDTO.ok(list);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateStatus(Long projectId, Integer projectStatus) {
        ProjectEntity entity = projectDao.selectById(projectId);
        if (entity == null || entity.getDeletedFlag()) {
            return ResponseDTO.userErrorParam("项目不存在");
        }

        if (projectStatus == null || projectStatus < 0 || projectStatus > 4) {
            return ResponseDTO.userErrorParam("项目状态不正确");
        }

        ProjectEntity updateEntity = new ProjectEntity();
        updateEntity.setProjectId(projectId);
        updateEntity.setProjectStatus(projectStatus);
        projectDao.updateById(updateEntity);
        return ResponseDTO.ok();
    }

    private void fillDictNames(List<ProjectVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getProjectType() != null && e.getProjectType() >= 1 && e.getProjectType() <= 4) {
                e.setProjectTypeName(PROJECT_TYPE_NAMES[e.getProjectType()]);
            }
            if (e.getProjectStatus() != null && e.getProjectStatus() >= 0 && e.getProjectStatus() <= 4) {
                e.setProjectStatusName(PROJECT_STATUS_NAMES[e.getProjectStatus()]);
            }
        });
    }
}
