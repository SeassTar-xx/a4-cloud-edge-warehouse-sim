package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：库存信息
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private String itemCode;
    private double stoNow;
    private double stoPlan;
    private double stoDown;

}
