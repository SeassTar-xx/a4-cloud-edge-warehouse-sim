package com.ruoyi.bom.domain;

import com.ruoyi.common.annotation.Excel;


/**
 * bomHead对象 bomHead对象
 * 
 * @author diaoaonan
 * @date 2024-02-24
 */
public class BomHead {

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String itemCode;

    /** BOM编码 */
    @Excel(name = "BOM编码")
    private String bomCode;

    public BomHead(Bom bom){
        this.itemCode = bom.getItemCode();
        this.bomCode = bom.getBomCode();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBomCode() {
        return bomCode;
    }

    public void setBomCode(String bomCode) {
        this.bomCode = bomCode;
    }

}
