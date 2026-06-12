package com.ruoyi.unit.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 单位对象 unit
 * 
 * @author ruoyi
 * @date 2024-01-31
 */
public class Unit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** uid */
    private Long uid;

    /** 单位描述 */
    @Excel(name = "单位描述")
    private String unit;

    public void setUid(Long uid) 
    {
        this.uid = uid;
    }

    public Long getUid() 
    {
        return uid;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uid", getUid())
            .append("unit", getUnit())
            .toString();
    }
}
