package com.ruoyi.arriveorder.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 采购入库表对象 arriveorder
 *
 * @author ruoyi
 * @date 2024-02-16
 */
public class Arriveorder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arriveTime;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String itemCode;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Integer arriveNum;

    /** 单位码 */
    @Excel(name = "单位码")
    private Integer uid;

    /** 入库编码 */
    @Excel(name = "入库编码")
    private String arriveCode;

    /** 关联采购订单号 */
    @Excel(name = "关联采购订单号")
    private String purchaseCode;

    /** id */
    private Integer id;

    public void setArriveTime(Date arriveTime)
    {
        this.arriveTime = arriveTime;
    }

    public Date getArriveTime()
    {
        return arriveTime;
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
    public void setArriveNum(Integer arriveNum)
    {
        this.arriveNum = arriveNum;
    }

    public Integer getArriveNum()
    {
        return arriveNum;
    }
    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setArriveCode(String arriveCode)
    {
        this.arriveCode = arriveCode;
    }

    public String getArriveCode()
    {
        return arriveCode;
    }
    public void setPurchaseCode(String purchaseCode)
    {
        this.purchaseCode = purchaseCode;
    }

    public String getPurchaseCode()
    {
        return purchaseCode;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("arriveTime", getArriveTime())
                .append("itemCode", getItemCode())
                .append("name", getName())
                .append("arriveNum", getArriveNum())
                .append("uid", getUid())
                .append("arriveCode", getArriveCode())
                .append("purchaseCode", getPurchaseCode())
                .append("id", getId())
                .toString();
    }
}
