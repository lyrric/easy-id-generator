package com.github.lyrric.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaodong
 * Created by wangxiaodong on 2018/6/6.
 */
@RestController
public class TestController {


    @GetMapping(value = "/test")
    String index(int id){
        return "test";
    }


}
