package com.ruoyi.purchaseorder.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购订单对象 purchaseorder
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public class Purchaseorder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date orderTime;

    /** 商品编号 */
    @Excel(name = "商品编号")
    private String itemCode;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 采购数量 */
    @Excel(name = "采购数量")
    private Double purchaseVolume;

    /** 单位码 */
    @Excel(name = "单位码")
    private Integer uid;

    /** 入库状态 */
    @Excel(name = "入库状态")
    private Integer status;

    /** id */
    private Integer id;

    /** 预计到达时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计到达时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrivalTime;

    /** 采购单编码 */
    @Excel(name = "采购单编码")
    private String orderCode;

    public Purchaseorder(String itemCode){
        this.itemCode = itemCode;
    }

    public void setOrderTime(Date orderTime)
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime()
    {
        return orderTime;
    }
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemCode()
    {
        return itemCode;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setPurchaseVolume(Double purchaseVolume)
    {
        this.purchaseVolume = purchaseVolume;
    }

    public Double getPurchaseVolume()
    {
        return purchaseVolume;
    }
    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setArrivalTime(Date arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    public Date getArrivalTime()
    {
        return arrivalTime;
    }
    public void setOrderCode(String orderCode)
    {
        if(orderCode!=null)
            this.orderCode = orderCode;
        else
            this.orderCode = "CGD";
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("orderTime", getOrderTime())
                .append("itemCode", getItemCode())
                .append("name", getName())
                .append("purchaseVolume", getPurchaseVolume())
                .append("uid", getUid())
                .append("status", getStatus())
                .append("id", getId())
                .append("arrivalTime", getArrivalTime())
                .append("orderCode", getOrderCode())
                .toString();
    }
}
