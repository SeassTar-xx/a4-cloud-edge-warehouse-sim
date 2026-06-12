package com.ruoyi.saleorder.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 销售订单对象 saleorder
 *
 * @author ruoyi
 * @date 2024-02-16
 */
public class Saleorder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 销售日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "销售日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date saleDate;

    /** 产品编号 */
    @Excel(name = "产品编号")
    private String itemCode;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 计划销售量 */
    @Excel(name = "计划销售量")
    private Double planMount;

    /** 单位码 */
    @Excel(name = "单位码")
    private Integer uid;

    /** 关联BOM编码 */
    @Excel(name = "关联BOM编码")
    private String bomCode;

    /** 销售单编码 */
    @Excel(name = "销售单编码")
    private String saleCode;

    /**  */
    private Integer id;

    public void setSaleDate(Date saleDate)
    {
        this.saleDate = saleDate;
    }

    public Date getSaleDate()
    {
        return saleDate;
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
    public void setPlanMount(Double planMount)
    {
        this.planMount = planMount;
    }

    public Double getPlanMount()
    {
        return planMount;
    }
    public void setUid(Integer uid)
    {
        this.uid = uid;
    }

    public Integer getUid()
    {
        return uid;
    }
    public void setBomCode(String bomCode)
    {
        this.bomCode = bomCode;
    }

    public String getBomCode()
    {
        return bomCode;
    }
    public void setSaleCode(String saleCode)
    {
        this.saleCode = saleCode;
    }

    public String getSaleCode()
    {
        return saleCode;
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
                .append("saleDate", getSaleDate())
                .append("itemCode", getItemCode())
                .append("name", getName())
                .append("planMount", getPlanMount())
                .append("uid", getUid())
                .append("bomCode", getBomCode())
                .append("saleCode", getSaleCode())
                .append("id", getId())
                .toString();
    }
}
