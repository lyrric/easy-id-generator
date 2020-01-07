package com.github.lyrric.controller;

import com.github.lyrric.core.ServerConfigProperties;
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
    private ServerConfigProperties serverConfigProperties;

    @GetMapping(value = "/test")
    String index(int id){
        return serverConfigProperties.getIdListDefaultSize().toString();
    }


}
