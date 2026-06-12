package com.ruoyi.bom.domain;

import java.util.Date;

/**
 * 功能：物料分解实例
 */
public class ResultBom {
    private String itemCode;
    private String CGC;
    private Double demandNum;
    private Date demandTime;
    private String sourceCode;

    public ResultBom() {
    }

    public ResultBom(String itemCode, String CGC, Double demandNum, Date demandTime, String sourceCode) {
        this.itemCode = itemCode;
        this.CGC = CGC;
        this.demandNum = demandNum;
        this.demandTime = demandTime;
        this.sourceCode = sourceCode;
    }

    /**
    * 对resultBom进行浅拷贝的构造方法
    *
    * @param resultBom 需要被拷贝的resulBom实例
    */
    public ResultBom(ResultBom resultBom) {
        this.itemCode = resultBom.getItemCode();
        this.CGC = resultBom.getCGC();
        this.demandNum = resultBom.getDemandNum();
        this.demandTime = resultBom.getDemandTime();
        this.sourceCode = resultBom.getSourceCode();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getCGC() {
        return CGC;
    }

    public void setCGC(String CGC) {
        this.CGC = CGC;
    }

    public Double getDemandNum() {
        return demandNum;
    }

    public void setDemandNum(Double demandNum) {
        this.demandNum = demandNum;
    }

    public Date getDemandTime() {
        return demandTime;
    }

    public void setDemandTime(Date demandTime) {
        this.demandTime = demandTime;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}
