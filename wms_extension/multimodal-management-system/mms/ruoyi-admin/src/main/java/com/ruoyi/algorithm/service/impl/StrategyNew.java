package com.ruoyi.algorithm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.algorithm.domain.Mrpinfo;
import com.ruoyi.algorithm.domain.Systeminfo;
import com.ruoyi.algorithm.mapper.MrpMapper;
import com.ruoyi.algorithm.mapper.MrpinfoMapper;
import com.ruoyi.algorithm.mapper.SysteminfoMapper;
import com.ruoyi.algorithm.service.StrategySuper;
import com.ruoyi.bom.domain.ResultBom;

@Service
public class StrategyNew implements StrategySuper{
    @Autowired
    private MrpMapper mrpMapper;
    @Autowired
    private MrpinfoMapper mrpinfoMapper;
    @Autowired
    private SysteminfoMapper systeminfoMapper;
    @Autowired 
    private MrpServiceImpl mrpServiceImpl;
    @Autowired  
    private StrategyOp strategyOp;
    

    @Override
    public void setResult(List<ResultBom> resultBoms){
        for (ResultBom resultBom : resultBoms) {
            Long temp = mrpServiceImpl.mrp(resultBom);
            if(temp != null){
                orderNum(temp.longValue());
            }
        }
    };

    /**
     * 混合运算比普通mrp多补充的一部分
     */
    private void orderNum(long id){//计算订单发出量为	MRP数量 / 平均MRP可用率，并直接修改到原mrp表中
        Mrp mrp = mrpMapper.selectMrpById(id);
        if (mrp == null) {
            return;
        }
        String itemCode = mrp.getItemCode();
        Mrpinfo mrpinfo = mrpinfoMapper.selectMrpinfoByItemCode(itemCode);
        double averageAvailability = mrpinfo == null || mrpinfo.getAverageAvailability() == null
                || mrpinfo.getAverageAvailability() == 0D ? 1D : mrpinfo.getAverageAvailability();
        double tem = (mrp.getPlanedAmount() == null ? 0D : mrp.getPlanedAmount()) / averageAvailability;
        Systeminfo systeminfo = systeminfoMapper.selectSysteminfo();
        if (systeminfo != null && systeminfo.getExceedPossible() == 0) {
            if (tem > strategyOp.getDifference(itemCode)) {
                mrp.setStatusCode(1);
            }
        }
        mrp.setPlanedAmount(tem);
        mrpMapper.updateMrp(mrp);
    }


}
