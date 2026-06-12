package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * mrphistory对象 mrphistory
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public class Mrphistory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 周期号 */
    @Excel(name = "周期号")
    private Long cycleNum;

    /** mrp运算出的数量 */
    @Excel(name = "mrp运算出的数量")
    private Double MRPNum;

    /** 真实使用量 */
    @Excel(name = "真实使用量")
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
    public void setMRPNum(Double MRPNum) 
    {
        this.MRPNum = MRPNum;
    }

    public Double getMRPNum() 
    {
        return MRPNum;
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
            .append("MRPNum", getMRPNum())
            .append("actualUsage", getActualUsage())
            .append("id", getId())
            .toString();
    }
}
