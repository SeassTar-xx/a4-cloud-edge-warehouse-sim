package com.algorithm.algorithm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algorithm.algorithm.common.Result;
import com.algorithm.algorithm.service.NewAlgorithmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/NewAlgorithm")
public class NewAlgorithmController {
    @Autowired
    NewAlgorithmService newAlgorithmService;

    @GetMapping("/cal/{id}")
    public Result cal(@PathVariable String id) {
        newAlgorithmService.newAlgorithm(id);
        return Result.success();
    }
    
}
