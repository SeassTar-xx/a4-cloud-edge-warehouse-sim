package com.algorithm.algorithm.entity;

import java.time.LocalDateTime;

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
public class PurchaseOrder {
    private String id;//主键
    private String itemCode;
    private double purchaseVolume;
    private LocalDateTime arrivalTime;
    private int status;

}
