package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：订货点法物料平均日用量、订货点、最高值（C2物料用）
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OPMInfo {
    private String itemCode;
    private double dailyUsage;
    private double orderPoint;
    private double maxPoint;
}
