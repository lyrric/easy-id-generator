package com.github.lyrric.controller;

import com.github.lyrric.generator.EasyIdGenerator;
import com.github.lyrric.generator.IdGenerator;
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
    String index(){
        return easyIdGenerator.get();
    }


}
