package com.ruoyi.bom.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * bom对象 bom
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bom extends TreeEntity<Bom> {
    private static final long serialVersionUID = 1L;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** 数量关系 */
    @Excel(name = "数量关系")
    private Integer num;

    /** 节点id */
    private Long bomid;

    /** 父节点id */
    @Excel(name = "父节点id")
    private Long parentBomId;

    @Excel(name = "节点组装时间")
    private Integer leaderTime;

    /** BOM编码 */
    @Excel(name = "BOM编码")
    private String bomCode;

    public String getBomCode() {
        return bomCode;
    }

    public void setBomCode(String bomCode) {
        this.bomCode = bomCode;
    }

    public Integer getLeaderTime() {
        return leaderTime;
    }

    public void setLeaderTime(Integer leaderTime) {
        this.leaderTime = leaderTime;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getNum() {
        return num;
    }

    public void setBomid(Long bomid) {
        this.bomid = bomid;
    }

    public Long getBomid() {
        return bomid;
    }

    public void setParentBomId(Long parentBomId) {
        this.parentBomId = parentBomId;
    }

    public Long getParentBomId() {
        return parentBomId;
    }

}
