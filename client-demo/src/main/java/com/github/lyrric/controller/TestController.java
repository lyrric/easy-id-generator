package com.github.lyrric.controller;

import com.github.lyrric.generator.EasyIdGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
public class TestController {

    @Resource
    private EasyIdGenerator easyIdGenerator;

    @GetMapping(value = "/test")
    Long test(int count){
        for (int i = 0; i < count; i++) {
            System.out.println(easyIdGenerator.get());
        }
        return easyIdGenerator.get();
    }
}
