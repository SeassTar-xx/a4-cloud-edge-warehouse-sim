package com.ruoyi.mpsPlus.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * mps对象 mps
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public class Mps extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 订单量 */
    @Excel(name = "订单量")
    private Double demandNum;

    /** 需求日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "需求日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date demandTime;

    /** id */
    private Integer id;

    /** 主生产计划表编码 */
    @Excel(name = "主生产计划表编码")
    private String mpsCode;

    /** 关联BOM表编码 */
    @Excel(name = "关联BOM表编码")
    private String bomCode;

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemCode()
    {
        return itemCode;
    }
    public void setDemandNum(Double demandNum)
    {
        this.demandNum = demandNum;
    }

    public Double getDemandNum()
    {
        return demandNum;
    }
    public void setDemandTime(Date demandTime)
    {
        this.demandTime = demandTime;
    }

    public Date getDemandTime()
    {
        return demandTime;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setMpsCode(String mpsCode)
    {
        if(mpsCode!=null)
        this.mpsCode = mpsCode;
        else
            this.mpsCode = "error";
    }

    public String getMpsCode()
    {
        return mpsCode;
    }
    public void setBomCode(String bomCode)
    {
        this.bomCode = bomCode;
    }

    public String getBomCode()
    {
        return bomCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("itemCode", getItemCode())
                .append("demandNum", getDemandNum())
                .append("demandTime", getDemandTime())
                .append("id", getId())
                .append("mpsCode", getMpsCode())
                .append("bomCode", getBomCode())
                .toString();
    }
}
