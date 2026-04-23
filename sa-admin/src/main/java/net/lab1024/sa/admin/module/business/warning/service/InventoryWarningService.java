package net.lab1024.sa.admin.module.business.warning.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.lab1024.sa.admin.module.business.material.dao.MaterialDao;
import net.lab1024.sa.admin.module.business.material.domain.entity.MaterialEntity;
import net.lab1024.sa.admin.module.business.warning.dao.InventoryWarningDao;
import net.lab1024.sa.admin.module.business.warning.domain.entity.InventoryWarningEntity;
import net.lab1024.sa.admin.module.business.warning.domain.form.InventoryWarningHandleForm;
import net.lab1024.sa.admin.module.business.warning.domain.form.InventoryWarningQueryForm;
import net.lab1024.sa.admin.module.business.warning.domain.vo.InventoryWarningVO;
import net.lab1024.sa.admin.module.system.login.domain.RequestEmployee;
import net.lab1024.sa.admin.util.AdminRequestUtil;
import net.lab1024.sa.base.common.domain.PageResult;
import net.lab1024.sa.base.common.domain.ResponseDTO;
import net.lab1024.sa.base.common.enumeration.UserTypeEnum;
import net.lab1024.sa.base.common.util.SmartBeanUtil;
import net.lab1024.sa.base.common.util.SmartPageUtil;
import net.lab1024.sa.base.module.support.message.constant.MessageTypeEnum;
import net.lab1024.sa.base.module.support.message.domain.MessageSendForm;
import net.lab1024.sa.base.module.support.message.service.MessageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 库存预警 Service
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
@Service
@Slf4j
public class InventoryWarningService {

    private static final String[] WARNING_TYPE_NAMES = {"", "库存不足", "即将过期", "已过期"};
    private static final String[] WARNING_LEVEL_NAMES = {"", "一般", "重要", "紧急"};
    private static final String[] HANDLE_STATUS_NAMES = {"未处理", "已处理", "已忽略"};

    private static final int WARNING_TYPE_LOW_STOCK = 1;
    private static final int WARNING_TYPE_NEAR_EXPIRATION = 2;
    private static final int WARNING_TYPE_EXPIRED = 3;

    private static final int HANDLE_STATUS_UNHANDLED = 0;

    @Resource
    private InventoryWarningDao inventoryWarningDao;

    @Resource
    private MaterialDao materialDao;

    @Resource
    private MessageService messageService;

    public ResponseDTO<PageResult<InventoryWarningVO>> query(InventoryWarningQueryForm queryForm) {
        Page<?> page = SmartPageUtil.convert2PageQuery(queryForm);
        List<InventoryWarningVO> list = inventoryWarningDao.query(page, queryForm);
        this.fillDictNames(list);
        PageResult<InventoryWarningVO> pageResult = SmartPageUtil.convert2PageResult(page, list);
        return ResponseDTO.ok(pageResult);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> handle(InventoryWarningHandleForm handleForm) {
        InventoryWarningEntity entity = inventoryWarningDao.selectById(handleForm.getWarningId());
        if (entity == null) {
            return ResponseDTO.userErrorParam("预警记录不存在");
        }

        if (entity.getHandleStatus() != HANDLE_STATUS_UNHANDLED) {
            return ResponseDTO.userErrorParam("该预警已处理");
        }

        RequestEmployee requestEmployee = AdminRequestUtil.getRequestUser();
        entity.setHandleStatus(handleForm.getHandleStatus());
        entity.setHandleRemark(handleForm.getHandleRemark());
        entity.setHandleTime(LocalDateTime.now());
        if (requestEmployee != null) {
            entity.setHandleUserId(requestEmployee.getEmployeeId());
            entity.setHandleUserName(requestEmployee.getActualName());
        }

        inventoryWarningDao.updateById(entity);
        return ResponseDTO.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    public void checkAndCreateWarning(Long materialId, BigDecimal currentQuantity) {
        MaterialEntity materialEntity = materialDao.selectById(materialId);
        if (materialEntity == null) {
            return;
        }

        if (materialEntity.getMinWarningQuantity() != null && materialEntity.getMinWarningQuantity().compareTo(BigDecimal.ZERO) > 0) {
            if (currentQuantity.compareTo(materialEntity.getMinWarningQuantity()) <= 0) {
                InventoryWarningEntity existWarning = inventoryWarningDao.selectByMaterialAndType(
                        materialId, WARNING_TYPE_LOW_STOCK, HANDLE_STATUS_UNHANDLED
                );

                if (existWarning == null) {
                    int warningLevel = 1;
                    if (currentQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                        warningLevel = 3;
                    } else if (currentQuantity.compareTo(materialEntity.getMinWarningQuantity().divide(new BigDecimal("2"), 2, BigDecimal.ROUND_HALF_UP)) <= 0) {
                        warningLevel = 2;
                    }

                    InventoryWarningEntity warningEntity = new InventoryWarningEntity();
                    warningEntity.setInventoryId(0L);
                    warningEntity.setMaterialId(materialId);
                    warningEntity.setMaterialCode(materialEntity.getMaterialCode());
                    warningEntity.setMaterialName(materialEntity.getMaterialName());
                    warningEntity.setWarningType(WARNING_TYPE_LOW_STOCK);
                    warningEntity.setWarningLevel(warningLevel);
                    warningEntity.setCurrentQuantity(currentQuantity);
                    warningEntity.setThresholdQuantity(materialEntity.getMinWarningQuantity());
                    warningEntity.setWarningContent("材料【" + materialEntity.getMaterialName() + "】当前库存" + currentQuantity + "，低于预警阈值" + materialEntity.getMinWarningQuantity());
                    warningEntity.setHandleStatus(HANDLE_STATUS_UNHANDLED);
                    warningEntity.setNotificationSent(false);

                    inventoryWarningDao.insert(warningEntity);

                    sendWarningNotification(warningEntity);
                } else {
                    existWarning.setCurrentQuantity(currentQuantity);
                    int warningLevel = 1;
                    if (currentQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                        warningLevel = 3;
                    } else if (currentQuantity.compareTo(materialEntity.getMinWarningQuantity().divide(new BigDecimal("2"), 2, BigDecimal.ROUND_HALF_UP)) <= 0) {
                        warningLevel = 2;
                    }
                    existWarning.setWarningLevel(warningLevel);
                    existWarning.setWarningContent("材料【" + materialEntity.getMaterialName() + "】当前库存" + currentQuantity + "，低于预警阈值" + materialEntity.getMinWarningQuantity());
                    inventoryWarningDao.updateById(existWarning);
                }
            }
        }
    }

    private void sendWarningNotification(InventoryWarningEntity warningEntity) {
        MessageSendForm sendForm = new MessageSendForm();
        sendForm.setMessageType(MessageTypeEnum.MAIL.getValue());
        sendForm.setReceiverUserType(UserTypeEnum.ADMIN_EMPLOYEE.getValue());
        sendForm.setReceiverUserId(1L);
        sendForm.setTitle("库存预警通知");
        sendForm.setContent(warningEntity.getWarningContent());
        sendForm.setDataId(warningEntity.getWarningId());

        try {
            messageService.sendMessage(sendForm);
            warningEntity.setNotificationSent(true);
            warningEntity.setNotificationTime(LocalDateTime.now());
            inventoryWarningDao.updateById(warningEntity);
        } catch (Exception e) {
            log.error("发送预警通知失败", e);
        }
    }

    private void fillDictNames(List<InventoryWarningVO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        list.forEach(e -> {
            if (e.getWarningType() != null && e.getWarningType() >= 1 && e.getWarningType() <= 3) {
                e.setWarningTypeName(WARNING_TYPE_NAMES[e.getWarningType()]);
            }
            if (e.getWarningLevel() != null && e.getWarningLevel() >= 1 && e.getWarningLevel() <= 3) {
                e.setWarningLevelName(WARNING_LEVEL_NAMES[e.getWarningLevel()]);
            }
            if (e.getHandleStatus() != null && e.getHandleStatus() >= 0 && e.getHandleStatus() <= 2) {
                e.setHandleStatusName(HANDLE_STATUS_NAMES[e.getHandleStatus()]);
            }
        });
    }
}
