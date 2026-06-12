package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 功能：mrp信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MRPInfo {
    String itemCode;
    double averageAvailability;
}
