package com.ruoyi.returnorder.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * returnorder对象 returnorder
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
public class Returnorder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 退货单编码 */
    @Excel(name = "退货单编码")
    private String orderCode;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 退货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnTime;

    /** 退货数量 */
    @Excel(name = "退货数量")
    private Double returnNum;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setItemCode(String itemCode) 
    {
        this.itemCode = itemCode;
    }

    public String getItemCode() 
    {
        return itemCode;
    }
    public void setReturnTime(Date returnTime) 
    {
        this.returnTime = returnTime;
    }

    public Date getReturnTime() 
    {
        return returnTime;
    }
    public void setReturnNum(Double returnNum) 
    {
        this.returnNum = returnNum;
    }

    public Double getReturnNum() 
    {
        return returnNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderCode", getOrderCode())
            .append("itemCode", getItemCode())
            .append("returnTime", getReturnTime())
            .append("returnNum", getReturnNum())
            .toString();
    }
}
