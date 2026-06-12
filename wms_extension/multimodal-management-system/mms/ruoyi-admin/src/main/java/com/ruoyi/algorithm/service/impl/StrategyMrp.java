package com.ruoyi.algorithm.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.algorithm.service.StrategySuper;
import com.ruoyi.bom.domain.ResultBom;

@Service
public class StrategyMrp implements StrategySuper{
    @Autowired 
    private MrpServiceImpl mrpServiceImpl;
    
    @Override
    public void setResult(List<ResultBom> resultBoms){
        for (ResultBom resultBom : resultBoms) {
            mrpServiceImpl.mrp(resultBom);
        }
    };

 
}
