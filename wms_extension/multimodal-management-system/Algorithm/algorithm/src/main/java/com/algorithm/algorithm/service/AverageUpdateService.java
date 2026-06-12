package com.algorithm.algorithm.service;

import com.algorithm.algorithm.entity.MRPHistory;
import com.algorithm.algorithm.entity.MRPInfo;
import com.algorithm.algorithm.entity.SystemInfoN;
import com.algorithm.algorithm.mapper.MRPHistoryMapper;
import com.algorithm.algorithm.mapper.MRPInfoMapper;
import com.algorithm.algorithm.mapper.SysteminfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 功能：MRP平均可用率更新
 */
@Service
public class AverageUpdateService {
    @Autowired
    MRPHistoryMapper mrpHistoryMapper;
    @Autowired
    SysteminfoMapper systeminfoMapper;
    @Autowired
    MRPInfoMapper mrpInfoMapper;

    public double currentMRP(String itemCode) { // 计算本期MRP可用率 = MRP数量 / 本期实际用量
        SystemInfoN systemInfo = systeminfoMapper.selectSysteminfo();
        MRPHistory mrpHistory = new MRPHistory();
        if (systemInfo != null) {
            mrpHistory = mrpHistoryMapper.selectByItemCodeAndCycleNum(itemCode, systemInfo.getCurrentCycleNum());
        }
        if (mrpHistory != null) {
            return mrpHistory.getMRPNum() / mrpHistory.getActualUsage();
        } else {
            return -1; // 返回错误
        }
    }

    public void averageMRP(String itemCode, int maxNum) { // 计算平均MRP可用率 = 前三个(此处设置为maxNum)周期的MRP的可用率的平均值
        SystemInfoN systemInfo = systeminfoMapper.selectSysteminfo();
        MRPHistory mrpHistory = new MRPHistory();
        int count = 0; // count用于计数，记有多少个真实记录（可能会有某个周期不存在使用）
        double sum = 0;
        for (int i = systemInfo.getCurrentCycleNum(); i > systemInfo.getCurrentCycleNum() - maxNum; i--) {

            if (i >= 1) { // 默认最早周期是第一周期
                count++;
                mrpHistory = mrpHistoryMapper.selectByItemCodeAndCycleNum(itemCode, i);
                double tem = 0;
                if (mrpHistory != null)
                    tem = mrpHistory.getMRPNum() / mrpHistory.getActualUsage();
                if (tem != 0) {
                    sum += tem;
                } else {
                    count--;
                }
            }
        }
        MRPInfo mrpInfo = new MRPInfo(itemCode, sum / count);
        mrpInfoMapper.insertMRPInfo(mrpInfo);
    }
}
