package com.ruoyi.algorithm.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * mrp对象 mrp
 *
 * @author diaoaonan
 * @date 2024-02-14
 */
public class Mrp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 建议采购量 */
    @Excel(name = "建议采购量")
    private Double planedAmount;

    /** 建议采购时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "建议采购时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    /** 需求时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date demandTime;

    /** 需求数量 */
    @Excel(name = "需求数量")
    private Double demandAmount;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String itemName;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** mrp编码 */
    @Excel(name = "mrp编码")
    private String mrpid;

    /** 状态码 */
    @Excel(name = "状态码")
    private Integer statusCode;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public Double getPlanedAmount() {
        return planedAmount;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setPlanedAmount(Double planedAmount) {
        this.planedAmount = planedAmount;
    }

    public void setDemandAmount(Double demandAmount) {
        this.demandAmount = demandAmount;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public void setDemandTime(Date demandTime) {
        this.demandTime = demandTime;
    }

    public Date getDemandTime() {
        return demandTime;
    }

    public Double getDemandAmount() {
        return demandAmount;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setMrpid(String mrpid) {
        this.mrpid = mrpid;
    }

    public String getMrpid() {
        return mrpid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("itemCode", getItemCode())
                .append("planedAmount", getPlanedAmount())
                .append("orderTime", getOrderTime())
                .append("demandTime", getDemandTime())
                .append("demandAmount", getDemandAmount())
                .append("itemName", getItemName())
                .append("unit", getUnit())
                .append("mrpid", getMrpid())
                .toString();
    }
}
