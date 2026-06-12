package com.ruoyi.algorithm.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.algorithm.domain.Opmhistory;
import com.ruoyi.algorithm.domain.Opminfo;
import com.ruoyi.algorithm.mapper.OpmhistoryMapper;
import com.ruoyi.algorithm.mapper.OpminfoMapper;
import com.ruoyi.algorithm.mapper.SysteminfoMapper;
import com.ruoyi.algorithm.service.StrategySuper;
import com.ruoyi.bom.domain.ResultBom;
import com.ruoyi.commodity.domain.Commodity;
import com.ruoyi.commodity.mapper.CommodityMapper;
import com.ruoyi.stock.domain.Stock;
import com.ruoyi.stock.mapper.StockMapper;

@Service
public class StrategyOp implements StrategySuper {
    @Autowired
    private StockMapper stockMapper;
    @Autowired 
    private OpminfoMapper opminfoMapper;
    @Autowired
    private MrpServiceImpl mrpServiceImpl;
    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private SysteminfoMapper systeminfoMapper;
    @Autowired 
    private OpmhistoryMapper opmhistoryMapper;

    @Override
    public void setResult(List<ResultBom> resultBoms){
        for (ResultBom resultBom : resultBoms) {
            if (inquireItemStatus(resultBom)) {
                String itemCode = resultBom.getItemCode();
                // TODO 将该方法转变为一个静态方法 2024/2/22
                double demandAmount = getDifference(itemCode);
                
                // 这里将需求时间更改为：  当前日期  +  C2物料提前期
                Commodity commodity = commodityMapper.selectCommodityByItemCode(itemCode);
                if (commodity == null) {
                    continue;
                }
                Calendar calendar = Calendar.getInstance();
                long leadTime = commodity.getLeadTime() == null ? 0L : commodity.getLeadTime();
                calendar.add(Calendar.DAY_OF_MONTH, (int) leadTime);
                resultBom.setDemandTime(calendar.getTime());
                
                mrpServiceImpl.setOrder(resultBom, demandAmount);
            }
        }
    };

    
    /**
     * 功能： 查询库存是否小于订货点
     *
     * @return 库存小于订货点时返回0，大于时返回1
     */
    public boolean inquireItemStatus(ResultBom resultBom) {
        Stock inventory = stockMapper.selectOneStockByItemCode(resultBom.getItemCode());
        Opminfo opminfo = opminfoMapper.selectOpminfoByItemCode(resultBom.getItemCode());
        if (inventory == null || opminfo == null || opminfo.getOrderPoint() == null) {
            return false;
        }
        double stock = inventory.getStock() == null ? 0D : inventory.getStock();
        double assignMount = inventory.getAssignMount() == null ? 0D : inventory.getAssignMount();
        if (stock - assignMount < opminfo.getOrderPoint()) {
            // 上面还需要减去 已分配量
            return true; // 库存小于订货点，需要订货
        }
        return false;
    }


    /**
     * 功能： 根据 订货点法历史信息表(opmhistory) 中的数据更新 订货点法信息表(opminfo)中的 平均日用量(dailyUsage) 和 订货点(orderPoint)
     */
    public void updateAverageAndOP() {
        double sum = 0;
        double dailyUsage = 0;
        double orderPoint = 0;
        int maxNum = 3;
        Long currentCycle = systeminfoMapper.selectSysteminfo().getCurrentCycleNum(); // 取得当前的周期号
        List<String> C2itemCodes = commodityMapper.selectItemCodeByCGC("C2").stream().map(Commodity::getItemCode).collect(Collectors.toList());

        for (String itemCode : C2itemCodes) {
            sum = 0;
            dailyUsage = 0;

            for (int i = maxNum - 1; i > -1; i--) {
                Opmhistory opmhistory = new Opmhistory();
                opmhistory.setItemCode(itemCode);
                opmhistory.setCycleNum(currentCycle.longValue() - i);
                Opmhistory history = opmhistoryMapper.selectOneOpmHistory(opmhistory);
                if (history != null && history.getActualUsage() != null) {
                    sum += history.getActualUsage();
                }
            }
            dailyUsage = sum / (double) (systeminfoMapper.selectSysteminfo().getCycleLen() * maxNum);
            Commodity itemMaster = commodityMapper.selectCommodityByItemCode(itemCode);
            orderPoint = itemMaster.getSafeStoke() + itemMaster.getLeadTime() * dailyUsage;

            Opminfo newOpminfo = new Opminfo();
            newOpminfo.setItemCode(itemCode);
            newOpminfo.setDailyUsage(dailyUsage);
            newOpminfo.setOrderPoint(orderPoint);
            opminfoMapper.updateOpminfo(newOpminfo);
        }
    }

    // TODO 将该方法转变为一个静态方法 2024/2/22
    /**
     * 获取对应物料当前库存和订货点法中的最大值之间的差值
     * @param itemCode 物料编码
     * @return 差值
     */
    public double getDifference(String itemCode){
        Opminfo opminfo = opminfoMapper.selectOpminfoByItemCode(itemCode);
        Stock stock = stockMapper.selectOneStockByItemCode(itemCode);
        double maxPoint = opminfo == null || opminfo.getMaxPoint() == null ? 0D : opminfo.getMaxPoint();
        double currentStock = stock == null || stock.getStock() == null ? 0D : stock.getStock();
        return Math.max(maxPoint - currentStock, 0D);
    }
}
