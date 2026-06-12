package com.algorithm.algorithm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algorithm.algorithm.entity.BOM;
import com.algorithm.algorithm.entity.Inventory;
import com.algorithm.algorithm.entity.ItemMaster;
import com.algorithm.algorithm.entity.MPS;
import com.algorithm.algorithm.entity.MRP;
import com.algorithm.algorithm.entity.PurchaseOrder;
import com.algorithm.algorithm.entity.ResultBom;
import com.algorithm.algorithm.entity.SystemInfoN;
import com.algorithm.algorithm.mapper.BomMapper;
import com.algorithm.algorithm.mapper.InventoryMapper;
import com.algorithm.algorithm.mapper.ItemMasterMapper;
import com.algorithm.algorithm.mapper.MPSMapper;
import com.algorithm.algorithm.mapper.MRPInfoMapper;
import com.algorithm.algorithm.mapper.MRPMapper;
import com.algorithm.algorithm.mapper.OPMInfoMapper;
import com.algorithm.algorithm.mapper.PurchaseOrderMapper;
import com.algorithm.algorithm.mapper.SysteminfoMapper;


@Service
public class NewAlgorithmService {
    @Autowired
    MPSMapper mpsMapper;
    @Autowired
    BomMapper bomMapper;
    @Autowired
    ItemMasterMapper itemMasterMapper;
    @Autowired
    MRPMapper mrpMapper;
    @Autowired
    SysteminfoMapper systeminfoMapper;
    @Autowired
    InventoryMapper inventoryMapper;
    @Autowired
    PurchaseOrderMapper purchaseOrderMapper;
    @Autowired
    MRPInfoMapper mrpInfoMapper;
    @Autowired
    OPMInfoMapper opmInfoMapper;

         
    /**
     * 主体逻辑
     * @param MPSid
     */
    public void newAlgorithm(String MPSid) {
        List<ResultBom> resultBoms = deItem(MPSid);
        for (ResultBom resultBom : resultBoms) {
            if (resultBom.getCGC().equals("A")) {
                mrp(resultBom);
                continue;
            }else if (resultBom.getCGC().equals("C2")) {
                continue;
            }else{
                String MRPid = mrp(resultBom);
                if (MRPid.isEmpty()) {
                    continue;
                }
                orderNum(MRPid, resultBom.getItemCode());
                continue;
            }
        }
    }

    /**
     * 将订单放入mrp表中
     *
     * @param sum
     * @param bom
     * @param mps
     * @param itemMaster
     */
    private String setOrder(ResultBom resultBom, double demandNum) { // 把结果放入mrp表里面
            MRP mrp = new MRP();
            mrp.setId(resultBom.getItemCode() + "-" + System.currentTimeMillis());
            mrp.setItemCode(resultBom.getItemCode());
            mrp.setPlanedAmount(demandNum);
            mrp.setDemandTime(resultBom.getDemandTime());
            mrp.setOrderTime(mrp.getDemandTime().minusDays(itemMasterMapper.selectByItemCode(resultBom.getItemCode()).getLeadTime()));
            mrpMapper.insertMRP(mrp);
            return mrp.getId();
    }



    /**
     * 以下三部分是BOM分解的主体函数
     */
    private List<ResultBom> deItem(String id) { // 开关
        MPS mps = mpsMapper.selectByID(id);
        List<BOM> boms = bomMapper.selectAll();
        List<ResultBom> resultBoms = new ArrayList<>();
        guide(resultBoms, mps, boms);
        return resultBoms;
    }
    private int addAndJudge(List<ResultBom> resultBoms, BOM bom, MPS mps) { // 增、改结果
        boolean flag = false;
        int count = 0; // 用于计数，记录有多少次更改，详细看下面guide函数里面的描述
        for (int i = 0; i < resultBoms.size() - count; i++) {
            ResultBom resultBom = resultBoms.get(i);
            if (resultBom.getItemCode().equals(bom.getItemCode())) {
                resultBom.setDemandNum(resultBom.getDemandNum() + bom.getNum() * mps.getDemandNum());
                resultBoms.remove(i);
                i--;
                resultBoms.add(resultBom);
                flag = true;
                count++;
            }
        }
        if (!flag) {
            ResultBom resultBom = new ResultBom();
            ItemMaster itemMaster = itemMasterMapper.selectByItemCode(bom.getItemCode());
            resultBom.setCGC(itemMaster.getCGC());
            resultBom.setDemandNum(mps.getDemandNum() * bom.getNum());
            resultBom.setItemCode(bom.getItemCode());
            resultBom.setDemandTime(mps.getDemandTime());
            resultBoms.add(resultBom);

        }
        return count;
    }
    private void guide(List<ResultBom> resultBoms, MPS mps, List<BOM> boms) { // 总控

        for (int i = 0; i < boms.size(); i++) { // 这是对最初的物料进行分解为次一级的，并加在结果里面

            BOM bom = boms.get(i);
            if (bom.getFaItemCode() != null && bom.getFaItemCode().equals(mps.getItemCode())) {
                addAndJudge(resultBoms, bom, mps);
            }
        }

        for (int i = 0; i < resultBoms.size(); i++) { // 对结果进行遍历操作
            boolean flag = false; // 判断是否需要删除，如果是有子物料，则删除
            ResultBom resultBom2 = resultBoms.get(i); // 得到第i个结果

            mps = new MPS(mps.getId(), resultBom2.getItemCode(), resultBom2.getDemandNum(), mps.getOrderTime(),
                    resultBom2.getDemandTime());
            // 更新mps（这第i个物料成为新的产品）

            for (int j = 0; j < boms.size(); j++) { // 对这第i个物料进行分解

                BOM bom = boms.get(j);
                if (bom.getFaItemCode() != null && bom.getFaItemCode().equals(mps.getItemCode())) {
                    addAndJudge(resultBoms, bom, mps);
                    for (int tem = 0; tem < resultBoms.size(); tem++) {
                        ResultBom resultBom = resultBoms.get(tem);
                        if (resultBom.getItemCode().equals(resultBom2.getItemCode()))
                            i = tem;
                    }
                    flag = true; // 如果有子项，则flag置1，待会删除此结果
                }
            }

            if (resultBom2.getItemCode().equals(mps.getItemCode()) && flag) {
                resultBoms.remove(i); // 如果删除第i个，则i要自减，如上述，变为D H I，接下来该对H即i=1,那么i自减后变为0，循环开始的时候自增变为1
                i--;
            }
        }
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
    private String mrp(ResultBom resultBom) {
        if (vaildInventoryCalculation(resultBom) - resultBom.getDemandNum() > 0) {
            // 库存充足，直接返回
            return "";
        } else {
            double demandNum = plannedInputsCalculation(plannedOutputsCalculation(resultBom.getDemandNum() - vaildInventoryCalculation(resultBom), resultBom), resultBom);
            String id = setOrder(resultBom, demandNum);
            return id;
        }
    }
    /**
     * 功能： 有效库存计算
     *
     * @param
     * @return
     */
    private double vaildInventoryCalculation(ResultBom resultBom) {
        Inventory inventory = inventoryMapper.selectByItemCode(resultBom.getItemCode());
        List<PurchaseOrder> purchaseOrders = purchaseOrderMapper.selectByItemCode(resultBom.getItemCode());
        ItemMaster itemMaster = itemMasterMapper.selectByItemCode(resultBom.getItemCode());
        double sumArrivalNum = 0;
        for (int i = 0; i < purchaseOrders.size(); i++) {
            PurchaseOrder purchaseOrder = purchaseOrders.get(i);
            if (purchaseOrder.getArrivalTime().compareTo(resultBom.getDemandTime()) < 0
                    && purchaseOrder.getStatus() == 0) {

                sumArrivalNum += purchaseOrder.getPurchaseVolume();
            }
        }
        if (resultBom != null) {
            return inventory.getStoNow() + sumArrivalNum - inventory.getStoDown() - itemMaster.getSafeStoke();
        }

        return -1; // 返回错误信息：resultBom是null
    }
    /**
     * 功能： 计划产出量计算
     *
     * @param
     * @return plannedOutputs
     */
    private double plannedOutputsCalculation(double NR, ResultBom resultBom) {
        SystemInfoN systemInfo = systeminfoMapper.selectSysteminfo();
        ItemMaster itemMaster = itemMasterMapper.selectByItemCode(resultBom.getItemCode());
        double PlannedOutput = 0; // 计划产出量
        if (systemInfo.getLotsPrinciple() == 0) { // 最小订货量原则
            PlannedOutput = (NR < itemMaster.getMinOrder()) ? itemMaster.getMinOrder() : NR;
            return PlannedOutput;
        } else {
            if (systemInfo.getLotsPrinciple() == 1) { // 最小包装量原则
                if (itemMaster.getMinPackage() > 0) { // 先判断最小包装量是否大于0，如果不大于0，则返回-1错误。
                    if (NR % itemMaster.getMinPackage() == 0) { // 再判断，如果NR（净需求量）可以整除最小包装量，则返回净需求量作为计划产出量
                        return NR;
                    } else {
                        return itemMaster.getMinPackage() * (Math.ceil(NR / itemMaster.getMinPackage()));
                        // 如果不能整除，则用最小包装量乘以（NR除以最小包装量向上取整）
                    }
                } else {
                    return -1; // 最小包装数设置错误
                }
            }

        }
        return -2; // 错误信息：系统信息记录错误
    }
    /**
     * 功能： 计划投入量计算
     *
     * @param
     * @return
     */
    private double plannedInputsCalculation(double plannedOutput, ResultBom resultBom) {
        ItemMaster itemMaster = itemMasterMapper.selectByItemCode(resultBom.getItemCode());
        if (itemMaster.getScrapRate() < 0 || itemMaster.getScrapRate() >= 1.0) {
            return -3; // 返回错误信息:废品率错误
        } else {
            return plannedOutput / (1 - itemMaster.getScrapRate());
        }
    }

    /**
     * 混合运算比普通mrp多补充的一部分
     */
    private void orderNum(String id, String itemCode){             //计算订单发出量为	MRP数量 / 平均MRP可用率，并直接修改到原mrp表中
        MRP mrp = mrpMapper.selectById(id);
        double tem = 0;
        if(mrp!=null)
            tem = mrp.getPlanedAmount() / mrpInfoMapper.selectAverageAvailabilityByItemCode(itemCode);
        if (tem > opmInfoMapper.selectByItemCode(itemCode).getMaxPoint()) {
            if (systeminfoMapper.selectSysteminfo().getExceedPossible() == 0) {
                System.out.println("error");
                return;
            }
        }
        mrp.setPlanedAmount(tem);
        mrpMapper.updateMRP(mrp);
    }

}