package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * opmhistory对象 opmhistory
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public class Opmhistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 周期号 */
    @Excel(name = "周期号")
    private Long cycleNum;

    /** 实际用量 */
    @Excel(name = "实际用量")
    private Double actualUsage;

    /** id */
    private String id;

    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }
    public void setCycleNum(Long cycleNum) 
    {
        this.cycleNum = cycleNum;
    }

    public Long getCycleNum() 
    {
        return cycleNum;
    }
    public void setActualUsage(Double actualUsage) 
    {
        this.actualUsage = actualUsage;
    }

    public Double getActualUsage() 
    {
        return actualUsage;
    }
    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("itemCode", getItemCode())
            .append("cycleNum", getCycleNum())
            .append("actualUsage", getActualUsage())
            .append("id", getId())
            .toString();
    }
}
