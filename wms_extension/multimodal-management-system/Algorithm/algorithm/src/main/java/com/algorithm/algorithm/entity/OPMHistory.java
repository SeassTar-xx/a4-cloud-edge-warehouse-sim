package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：历史表二（记录C2类物料的历史用量）
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OPMHistory {
    private String id;
    private String itemCode;
    private int cycleNum;
    private double actualUsage;

}
