package com.ruoyi.algorithm.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.algorithm.mapper.MrpMapper;
import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.algorithm.service.IMrpService;
import com.ruoyi.bom.domain.ResultBom;
import com.ruoyi.commodity.domain.Commodity;
import com.ruoyi.commodity.mapper.CommodityMapper;
import com.ruoyi.purchaseorder.domain.Purchaseorder;
import com.ruoyi.purchaseorder.mapper.PurchaseorderMapper;
import com.ruoyi.stock.domain.Stock;
import com.ruoyi.stock.mapper.StockMapper;
import com.ruoyi.stock.service.impl.StockServiceImpl;
import com.ruoyi.unit.mapper.UnitMapper;

/**
 * mrpService业务层处理
 *
 * @author diaoaonan
 * @date 2024-02-14
 */
@Service
public class MrpServiceImpl implements IMrpService {
    @Autowired
    private MrpMapper mrpMapper;
    @Autowired
    private CommodityMapper commodityMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private PurchaseorderMapper purchaseorderMapper;
    @Autowired
    private StockServiceImpl stockServiceImpl;

    /**
     * 查询mrp
     *
     * @param id mrp主键
     * @return mrp
     */
    @Override
    public Mrp selectMrpById(Long id) {
        return mrpMapper.selectMrpById(id);
    }

    /**
     * 查询mrp列表
     *
     * @param mrp mrp
     * @return mrp
     */
    @Override
    public List<Mrp> selectMrpList(Mrp mrp) {
        return mrpMapper.selectMrpList(mrp);
    }

    @Override
    public List<Mrp> selectMrpByMrpid(String mrpid) {
        return mrpMapper.selectMrpByMrpid(mrpid);
    }

    /**
     * 新增mrp
     *
     * @param mrp mrp
     * @return 结果
     */
    @Override
    public int insertMrp(Mrp mrp) {
        return mrpMapper.insertMrp(mrp);
    }

    /**
     * 修改mrp
     *
     * @param mrp mrp
     * @return 结果
     */
    @Override
    public int updateMrp(Mrp mrp) {
        return mrpMapper.updateMrp(mrp);
    }

    /**
     * 批量删除mrp
     *
     * @param ids 需要删除的mrp主键
     * @return 结果
     */
    @Override
    public int deleteMrpByIds(Long[] ids) {
        return mrpMapper.deleteMrpByIds(ids);
    }

    @Override
    public int deleteMrpByMrpid(String mrpid) {
        return mrpMapper.deleteMrpByMrpid(mrpid);
    }

    /**
     * 删除mrp信息
     *
     * @param id mrp主键
     * @return 结果
     */
    @Override
    public int deleteMrpById(Long id) {
        return mrpMapper.deleteMrpById(id);
    }

    /**
     * 将订单放入mrp表中
     *
     * @param sum
     * @param bom
     * @param mps
     * @param itemMaster
     */
    @Override
    public Long setOrder(ResultBom resultBom, Double demandNum) { // 把结果放入mrp表里面
        Commodity commodity = commodityMapper.selectCommodityByItemCode(resultBom.getItemCode());
        if (commodity == null) {
            throw new IllegalArgumentException("物料编码" + resultBom.getItemCode() + "不存在，请先维护物料基础信息");
        }
        Mrp mrp = new Mrp();
        mrp.setItemCode(resultBom.getItemCode());
        mrp.setDemandAmount(resultBom.getDemandNum());
        mrp.setPlanedAmount(demandNum);
        mrp.setDemandTime(resultBom.getDemandTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mrp.getDemandTime());
        long leadTime = commodity.getLeadTime() == null ? 0L : commodity.getLeadTime();
        calendar.add(Calendar.DAY_OF_MONTH, (int) -leadTime);
        mrp.setOrderTime(calendar.getTime());//mrp.getDemandTime().minusDays(commodity.getLeadTime())
        mrp.setItemName(commodity.getName());
        if (commodity.getUid() != null && unitMapper.selectUnitByUid(commodity.getUid()) != null) {
            mrp.setUnit(unitMapper.selectUnitByUid(commodity.getUid()).getUnit());
        }
        mrp.setMrpid(resultBom.getSourceCode());
        mrp.setStatusCode(0);
        mrpMapper.insertMrp(mrp);
        return mrp.getId();
    }

    /**
     * 以下是MRP运算主体逻辑
     */
    /**
     * 功能： 主模块
     *
     * @param
     * @return
     */
    @Override
    public Long mrp(ResultBom resultBom) {
        double validInventory = vaildInventoryCalculation(resultBom);
        double demandNum = Math.max(resultBom.getDemandNum() - validInventory, 0D);
        stockServiceImpl.updateAssignMount(resultBom);
        return setOrder(resultBom, demandNum);
    }

    /**
     * 功能： 有效库存计算
     *
     * @param
     * @return
     */
    private double vaildInventoryCalculation(ResultBom resultBom) {
        Stock inventory = stockMapper.selectOneStockByItemCode(resultBom.getItemCode());
        List<Purchaseorder> purchaseOrders = purchaseorderMapper
                .selectPurchaseorderList(new Purchaseorder(resultBom.getItemCode()));
        Commodity itemMaster = commodityMapper.selectCommodityByItemCode(resultBom.getItemCode());
        double sumArrivalNum = 0;
        for (int i = 0; i < purchaseOrders.size(); i++) {
            Purchaseorder purchaseOrder = purchaseOrders.get(i);
            if (purchaseOrder.getArrivalTime() != null && resultBom.getDemandTime() != null
                    && purchaseOrder.getArrivalTime().compareTo(resultBom.getDemandTime()) < 0
                    && purchaseOrder.getStatus() != null && purchaseOrder.getStatus() == 0) {
                sumArrivalNum += purchaseOrder.getPurchaseVolume() == null ? 0D : purchaseOrder.getPurchaseVolume();
            }
        }
        if (resultBom != null) {
            double stock = inventory == null || inventory.getStock() == null ? 0D : inventory.getStock();
            double assignMount = inventory == null || inventory.getAssignMount() == null ? 0D : inventory.getAssignMount();
            double safeStock = itemMaster == null || itemMaster.getSafeStoke() == null ? 0D : itemMaster.getSafeStoke();
            return stock - assignMount + sumArrivalNum - safeStock;
        }

        return -1; // 返回错误信息：resultBom是null
    }
    // /**
    // * 功能： 计划产出量计算
    // *
    // * @param
    // * @return plannedOutputs
    // */
    // private double plannedOutputsCalculation(double NR, ResultBom resultBom) {
    // SystemInfoN systemInfo = systeminfoMapper.selectSysteminfo();
    // ItemMaster itemMaster =
    // itemMasterMapper.selectByItemCode(resultBom.getItemCode());
    // double PlannedOutput = 0; // 计划产出量
    // if (systemInfo.getLotsPrinciple() == 0) { // 最小订货量原则
    // PlannedOutput = (NR < itemMaster.getMinOrder()) ? itemMaster.getMinOrder() :
    // NR;
    // return PlannedOutput;
    // } else {
    // if (systemInfo.getLotsPrinciple() == 1) { // 最小包装量原则
    // if (itemMaster.getMinPackage() > 0) { // 先判断最小包装量是否大于0，如果不大于0，则返回-1错误。
    // if (NR % itemMaster.getMinPackage() == 0) { //
    // 再判断，如果NR（净需求量）可以整除最小包装量，则返回净需求量作为计划产出量
    // return NR;
    // } else {
    // return itemMaster.getMinPackage() * (Math.ceil(NR /
    // itemMaster.getMinPackage()));
    // // 如果不能整除，则用最小包装量乘以（NR除以最小包装量向上取整）
    // }
    // } else {
    // return -1; // 最小包装数设置错误
    // }
    // }

    // }
    // return -2; // 错误信息：系统信息记录错误
    // }
    // /**
    // * 功能： 计划投入量计算
    // *
    // * @param
    // * @return
    // */
    // private double plannedInputsCalculation(double plannedOutput, ResultBom
    // resultBom) {
    // ItemMaster itemMaster =
    // itemMasterMapper.selectByItemCode(resultBom.getItemCode());
    // if (itemMaster.getScrapRate() < 0 || itemMaster.getScrapRate() >= 1.0) {
    // return -3; // 返回错误信息:废品率错误
    // } else {
    // return plannedOutput / (1 - itemMaster.getScrapRate());
    // }
    // }


}
