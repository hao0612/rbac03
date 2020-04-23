package cn.wolfcode.domain;

import java.util.Date;

public class CustomerTransfer {
    private Long id;

    private Long customerId;

    private Long operatorId;

    private Date operateTime;

    private Long oldSellerId;

    private Long newSellerId;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Long getOldSellerId() {
        return oldSellerId;
    }

    public void setOldSellerId(Long oldSellerId) {
        this.oldSellerId = oldSellerId;
    }

    public Long getNewSellerId() {
        return newSellerId;
    }

    public void setNewSellerId(Long newSellerId) {
        this.newSellerId = newSellerId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}