/**
 * @author LYU
 * @create 2017年10月13日 11:11
 * @Copyright(C) 2010 - 2017 GBSZ
 * All rights reserved
 */

package com.lyu.study.test.entity;

public final class Order {
    private long orderId;

    private int userId;

    private String status;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
