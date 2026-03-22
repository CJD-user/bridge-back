package net.lab1024.sa.admin.module.business.approvalFlow.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 审批流程配置表 实体类
 *
 * @Author hyc
 * @Date 2026-03-22 15:50:20
 * @Copyright /
 */

@Data
@TableName("b_approval_flow")
public class ApprovalFlowEntity {

    /**
     * 审批流程ID
     */
    @TableId(type = IdType.AUTO)
    private Long approvalFlowId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 流程编码
     */
    private String flowCode;

    /**
     * 业务类型: 1-采购申请 2-出库申请 3-供应商评价
     */
    private Integer businessType;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 状态: 0-停用 1-启用
     */
    private Integer status;

    /**
     * 删除状态: 0未删除 1已删除
     */
    private Integer deletedFlag;

    /**
     * 创建人ID
     */
    private Long createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
