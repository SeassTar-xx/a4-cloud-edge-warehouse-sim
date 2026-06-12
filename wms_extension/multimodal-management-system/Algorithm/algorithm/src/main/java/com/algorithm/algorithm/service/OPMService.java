package com.algorithm.algorithm.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algorithm.algorithm.entity.Inventory;
import com.algorithm.algorithm.entity.ItemMaster;
import com.algorithm.algorithm.entity.MRP;
import com.algorithm.algorithm.entity.OPMInfo;
import com.algorithm.algorithm.mapper.InventoryMapper;
import com.algorithm.algorithm.mapper.ItemMasterMapper;
import com.algorithm.algorithm.mapper.MRPMapper;
import com.algorithm.algorithm.mapper.OPMHistoryMapper;
import com.algorithm.algorithm.mapper.OPMInfoMapper;
import com.algorithm.algorithm.mapper.SysteminfoMapper;

/**
 * 服务类
 * 功能： 所有订货点法相关的业务逻辑都存在这里
 */
@Service
public class OPMService {

    @Autowired
    InventoryMapper inventoryMapper;
    @Autowired
    OPMInfoMapper opmInfoMapper;
    @Autowired
    MRPMapper mrpMapper;
    @Autowired
    ItemMasterMapper itemMasterMapper;
    @Autowired
    SysteminfoMapper systeminfoMapper;
    @Autowired
    OPMHistoryMapper opmHistoryMapper;

    /**
     * 功能： 查询库存是否小于订货点
     *
     * @return 库存小于订货点时返回0，大于时返回1
     */
    public int inquireItemStatus(String itemCode) {
        Inventory inventory = inventoryMapper.selectByItemCode(itemCode);
        OPMInfo opminfo = opmInfoMapper.selectByItemCode(itemCode);
        if (inventory.getStoNow() < opminfo.getOrderPoint()) {
            placeOrder(itemCode);
            return 0; // 库存小于订货点，需要订货
        }
        return 1;
    }

    /**
     * 功能： 发出订单
     * @param itemCode
     * @return 无
     */
    private void placeOrder(String itemCode) {
        Inventory inventory = inventoryMapper.selectByItemCode(itemCode);
        OPMInfo opminfo = opmInfoMapper.selectByItemCode(itemCode);
        MRP mrp = new MRP();
        mrp.setId(itemCode + "-" + System.currentTimeMillis());
        mrp.setItemCode(itemCode);
        LocalDateTime dateTime = LocalDateTime.now();
        mrp.setOrderTime(dateTime);
        mrp.setDemandTime(dateTime.plusDays(itemMasterMapper.selectByItemCode(itemCode).getLeadTime()));
        // 此处的订单发出量暂时定为 最高值 - 目前存量
        mrp.setPlanedAmount(opminfo.getMaxPoint() - inventory.getStoNow());
        mrpMapper.insertMRP(mrp);
    }

    /**
     * 功能： 根据 订货点法历史信息表(opmhistory) 中的数据更新 订货点法信息表(opminfo)中的 平均日用量(dailyUsage) 和 订货点(orderPoint)
     */
    public void updateAverageAndOP() {
        double sum = 0;
        double dailyUsage = 0;
        double orderPoint = 0;
        int maxNum = 3;
        int currentCycle = systeminfoMapper.selectSysteminfo().getCurrentCycleNum(); // 取得当前的周期号
        String[] C2itemCodes = itemMasterMapper.selectItemCodeByCGC("C2");

        for (String itemCode : C2itemCodes) {
            sum = 0;
            dailyUsage = 0;

            for (int i = maxNum - 1; i > -1; i--) {
                sum += opmHistoryMapper.selectOPMHistoryByCycleAndItemCode(itemCode, currentCycle - i).getActualUsage();
            }
            dailyUsage = sum / (double) (systeminfoMapper.selectSysteminfo().getCycleLen() * maxNum);
            ItemMaster itemMaster = itemMasterMapper.selectByItemCode(itemCode);
            orderPoint = itemMaster.getSafeStoke() + itemMaster.getLeadTime() * dailyUsage;
            opmInfoMapper.updateAverageAndOP(itemCode, dailyUsage, orderPoint);
        }
    }
}
