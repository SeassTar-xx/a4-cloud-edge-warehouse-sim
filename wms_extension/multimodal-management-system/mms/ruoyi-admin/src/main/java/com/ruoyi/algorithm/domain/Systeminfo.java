package com.ruoyi.algorithm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * systeminfo对象 systeminfo
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public class Systeminfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 当前周期号 */
    @Excel(name = "当前周期号")
    private Long currentCycleNum;

    /** 周期长度 */
    @Excel(name = "周期长度")
    private Long cycleLen;

    /** 是否允许超出订货点最高阈值 */
    @Excel(name = "是否允许超出订货点最高阈值")
    private int exceedPossible;

    /** 经济批量策略 */
    @Excel(name = "经济批量策略")
    private int lotsPrinciple;

    public void setCurrentCycleNum(Long currentCycleNum) 
    {
        this.currentCycleNum = currentCycleNum;
    }

    public Long getCurrentCycleNum() 
    {
        return currentCycleNum;
    }
    public void setCycleLen(Long cycleLen) 
    {
        this.cycleLen = cycleLen;
    }

    public Long getCycleLen() 
    {
        return cycleLen;
    }
    public void setExceedPossible(int exceedPossible) 
    {
        this.exceedPossible = exceedPossible;
    }

    public int getExceedPossible() 
    {
        return exceedPossible;
    }
    public void setLotsPrinciple(int lotsPrinciple) 
    {
        this.lotsPrinciple = lotsPrinciple;
    }

    public int getLotsPrinciple() 
    {
        return lotsPrinciple;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("currentCycleNum", getCurrentCycleNum())
            .append("cycleLen", getCycleLen())
            .append("exceedPossible", getExceedPossible())
            .append("lotsPrinciple", getLotsPrinciple())
            .toString();
    }
}
