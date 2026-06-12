package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * mrpinfo对象 mrpinfo
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public class Mrpinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    private String itemCode;

    /** 平均利用率 */
    @Excel(name = "平均利用率")
    private Double averageAvailability;

    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }
    public void setAverageAvailability(Double averageAvailability) 
    {
        this.averageAvailability = averageAvailability;
    }

    public Double getAverageAvailability() 
    {
        return averageAvailability;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemCode", getItemCode())
            .append("averageAvailability", getAverageAvailability())
            .toString();
    }
}
