package com.algorithm.algorithm.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：MRP报表
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MRP {
    private String id;
    private String itemCode;
    private double planedAmount;
    private LocalDateTime orderTime;
    private LocalDateTime demandTime;
}
