package net.lab1024.sa.admin.module.business.approvalFlow.constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lab1024.sa.base.common.enumeration.BaseEnum;

@AllArgsConstructor
@Getter
public enum ApprovalFlowEnum implements BaseEnum {
    /**
     * 状态: 0-停用 1-启用
     */
    STATUS_DISABLED(0, "停用"),
    STATUS_ENABLED(1, "启用"),
    /**
     * 删除状态: 0未删除 1已删除
     */
    DELETED_FLAG_NO(0, "未删除"),
    DELETED_FLAG_YES(1, "已删除"),
    /**
     * 业务类型: 1-采购申请 2-出库申请 3-供应商评价
     */
    BUSINESS_TYPE_PURCHASE_APPLICATION(1, "采购申请"),
    BUSINESS_TYPE_OUT_OF_STOCK_APPLICATION(2, "出库申请"),
    BUSINESS_TYPE_SUPPLIER_EVALUATION(3, "供应商评价");

    private final Integer code;
    private final String message;

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String getDesc() {
        return "";
    }
}
