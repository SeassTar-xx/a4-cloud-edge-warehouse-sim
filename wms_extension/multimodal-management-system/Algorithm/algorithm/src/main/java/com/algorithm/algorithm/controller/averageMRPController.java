package com.algorithm.algorithm.controller;


import com.algorithm.algorithm.common.Result;
import com.algorithm.algorithm.service.AverageUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 测试模块4
 */
@RestController
@RequestMapping("/ave")
public class averageMRPController {
    @Autowired
    AverageUpdateService averageUpdateService;

    @GetMapping("/update/{itemCode}")
    public Result averageUpdate(@PathVariable String itemCode) {
        double average = averageUpdateService.currentMRP(itemCode);
        return Result.success(average);
    }

    @GetMapping("/mrp")
    public Result averageMRP(@RequestParam String itemCode, @RequestParam int maxNum) {
        averageUpdateService.averageMRP(itemCode, maxNum);
        return Result.success();
    }
}




