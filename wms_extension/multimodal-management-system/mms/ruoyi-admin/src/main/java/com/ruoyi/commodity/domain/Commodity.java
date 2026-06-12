package com.ruoyi.commodity.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品信息对象 commodity
 *
 * @author ruoyi
 * @date 2024-02-07
 */
public class Commodity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 单位码 */
    @Excel(name = "单位码")
    private Long uid;

    /** 提前期 */
    @Excel(name = "提前期")
    private Long leadTime;

    /** 时间单位 */
    @Excel(name = "时间单位")
    private Long timeUid;

    /** CGC */
    @Excel(name = "CGC")
    private String cgc;

    /** 是否考虑安全库存 */
    @Excel(name = "是否考虑安全库存")
    private Long safeFlag;

    /** 安全库存 */
    @Excel(name = "安全库存")
    private Double safeStoke;

    /** 最大库存量 */
    @Excel(name = "最大库存量")
    private Double maxStoke;

    /** 预计销量 */
    @Excel(name = "预计销量")
    private Double expectSale;

    /** 销量单位 */
    @Excel(name = "销量单位")
    private Long saleUid;

    /** id */
    private Integer id;

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
    public void setUid(Long uid)
    {
        this.uid = uid;
    }

    public Long getUid()
    {
        return uid;
    }
    public void setLeadTime(Long leadTime)
    {
        this.leadTime = leadTime;
    }

    public Long getLeadTime()
    {
        return leadTime;
    }
    public void setTimeUid(Long timeUid)
    {
        this.timeUid = timeUid;
    }

    public Long getTimeUid()
    {
        return timeUid;
    }
    public void setCgc(String cgc)
    {
        this.cgc = cgc;
    }

    public String getCgc()
    {
        return cgc;
    }
    public void setSafeFlag(Long safeFlag)
    {
        this.safeFlag = safeFlag;
    }

    public Long getSafeFlag()
    {
        return safeFlag;
    }
    public void setSafeStoke(Double safeStoke)
    {
        this.safeStoke = safeStoke;
    }

    public Double getSafeStoke()
    {
        return safeStoke;
    }
    public void setMaxStoke(Double maxStoke)
    {
        this.maxStoke = maxStoke;
    }

    public Double getMaxStoke()
    {
        return maxStoke;
    }
    public void setExpectSale(Double expectSale)
    {
        this.expectSale = expectSale;
    }

    public Double getExpectSale()
    {
        return expectSale;
    }
    public void setSaleUid(Long saleUid)
    {
        this.saleUid = saleUid;
    }

    public Long getSaleUid()
    {
        return saleUid;
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
                .append("itemCode", getItemCode())
                .append("name", getName())
                .append("uid", getUid())
                .append("leadTime", getLeadTime())
                .append("timeUid", getTimeUid())
                .append("cgc", getCgc())
                .append("safeFlag", getSafeFlag())
                .append("safeStoke", getSafeStoke())
                .append("maxStoke", getMaxStoke())
                .append("expectSale", getExpectSale())
                .append("saleUid", getSaleUid())
                .append("id", getId())
                .toString();
    }
}
