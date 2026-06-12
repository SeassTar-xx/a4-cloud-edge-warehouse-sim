package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 功能：物料分解实例
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultBom {
    private String  itemCode;
    private String CGC;
    private double demandNum;
    private LocalDateTime demandTime;
}
