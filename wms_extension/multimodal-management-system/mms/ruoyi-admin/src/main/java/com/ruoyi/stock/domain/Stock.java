package com.ruoyi.stock.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 初级库存录入对象 stock
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public class Stock extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品编码 */
    @Excel(name = "商品编码")
    private String itemCode;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 单价 */
    @Excel(name = "单价")
    private Double price;

    /** 库存 */
    @Excel(name = "库存")
    private Double stock;

    /** 总成本 */
    @Excel(name = "总成本")
    private Double totalCost;

    /** 已分配量 */
    @Excel(name = "已分配量")
    private Double assignMount;

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
    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getPrice()
    {
        return price;
    }
    public void setStock(Double stock)
    {
        this.stock = stock;
    }

    public Double getStock()
    {
        return stock;
    }
    public void setTotalCost(Double totalCost)
    {
        this.totalCost = totalCost;
    }

    public Double getTotalCost()
    {
        return totalCost;
    }
    public void setAssignMount(Double assignMount)
    {
        this.assignMount = assignMount;
    }

    public Double getAssignMount()
    {
        return assignMount;
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
                .append("price", getPrice())
                .append("stock", getStock())
                .append("totalCost", getTotalCost())
                .append("assignMount", getAssignMount())
                .append("id", getId())
                .toString();
    }
}
