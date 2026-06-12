package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * opminfo对象 opminfo
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public class Opminfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    private String itemCode;

    /** 平均日用量 */
    @Excel(name = "平均日用量")
    private Double dailyUsage;

    /** 订货点 */
    @Excel(name = "订货点")
    private Double orderPoint;

    /** 最高阈值 */
    @Excel(name = "最高阈值")
    private Double maxPoint;

    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }
    public void setDailyUsage(Double dailyUsage) 
    {
        this.dailyUsage = dailyUsage;
    }

    public Double getDailyUsage() 
    {
        return dailyUsage;
    }
    public void setOrderPoint(Double orderPoint) 
    {
        this.orderPoint = orderPoint;
    }

    public Double getOrderPoint() 
    {
        return orderPoint;
    }
    public void setMaxPoint(Double maxPoint) 
    {
        this.maxPoint = maxPoint;
    }

    public Double getMaxPoint() 
    {
        return maxPoint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemCode", getItemCode())
            .append("dailyUsage", getDailyUsage())
            .append("orderPoint", getOrderPoint())
            .append("maxPoint", getMaxPoint())
            .toString();
    }
}
