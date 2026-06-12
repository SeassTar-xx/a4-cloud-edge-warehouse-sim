package com.algorithm.algorithm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithm.algorithm.common.Result;
import com.algorithm.algorithm.service.OPMService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



/**
 *  功能：测试功能模块2和3的端口
 *  作者： diaoaonan
 */
@RestController
@RequestMapping("/OPM")
public class OPMCroller {
    @Autowired
    OPMService opmService;

    /**
     * 功能：测试端口是否畅通
     * @return
     */
    @GetMapping("/")
    public Result hello() {
        return Result.success();
    }

    /**
     * 功能： 测试功能模块2
     * @param itemCode
     * @return
     */
    @GetMapping("/inquireItemStatus/{itemCode}")
    public Result inquireItemStatus(@PathVariable String itemCode) {
        int status = opmService.inquireItemStatus(itemCode);
        return Result.success(status);
    }
    
    /**
     * 功能： 测试功能模块3
     * @return
     */
    @PutMapping("/OPMinfoUpdate")
    public Result getMethodName() {
        opmService.updateAverageAndOP();
        return Result.success("success");
    }
    
}
