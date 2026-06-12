package com.algorithm.algorithm.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：MPS表实体类
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MPS {
    private String id;
    private String itemCode;
    private double demandNum;
    private LocalDateTime orderTime;
    private LocalDateTime demandTime;
    
}
