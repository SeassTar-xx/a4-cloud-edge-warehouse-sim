package com.ruoyi.algorithm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.bom.domain.ResultBom;

@Service
public class StrategyContext {
    @Autowired
    private StrategyMrp strategyMrp;
    @Autowired
    private StrategyNew strategyNew;
    @Autowired
    private StrategyOp strategyOp;

    public void setResult(List<ResultBom> resultBoms){
        List<ResultBom> resultBoms2 = new ArrayList<>();
        resultBoms2 = resultBoms.stream().filter(resultBom -> "A".equals(resultBom.getCGC())).collect(Collectors.toList());
        strategyMrp.setResult(resultBoms2);

        Predicate<ResultBom> predicate1 = resultBom -> "C1".equals(resultBom.getCGC());
        Predicate<ResultBom> predicate2 = resultBom -> "B".equals(resultBom.getCGC());
        resultBoms2 = resultBoms.stream()
            .filter(predicate1.or(predicate2))
            .collect(Collectors.toList());
        strategyNew.setResult(resultBoms2);
        
        resultBoms2 = resultBoms.stream().filter(resultBom -> "C2".equals(resultBom.getCGC())).collect(Collectors.toList());
        strategyOp.setResult(resultBoms2);
    }
    
}
