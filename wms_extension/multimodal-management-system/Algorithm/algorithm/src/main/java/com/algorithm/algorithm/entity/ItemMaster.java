package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：物料主文件
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMaster {
    private String itemCode;
    private int leadTime;
    private double safeStoke;
    private String CGC;
    private double minOrder;
    private double minPackage;
    private double scrapRate;
}
