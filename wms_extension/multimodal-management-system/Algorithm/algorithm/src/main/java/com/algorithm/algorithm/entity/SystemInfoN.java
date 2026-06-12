package com.algorithm.algorithm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 名称：系统信息
 * 作者：diaoaonan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoN {
    private int currentCycleNum;
    private int cycleLen;
    private int exceedPossible;
    private int lotsPrinciple;
}
