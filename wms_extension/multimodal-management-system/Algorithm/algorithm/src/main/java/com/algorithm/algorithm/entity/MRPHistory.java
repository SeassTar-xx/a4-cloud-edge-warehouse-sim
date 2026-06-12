package com.algorithm.algorithm.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：历史表一（记录B、C1类物料的历史MRP运算物料用量，与实际用量）
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MRPHistory {
    private String id;
    private String itemCode;
    private int cycleNum;
    private double MRPNum;
    private double actualUsage;
    
}
