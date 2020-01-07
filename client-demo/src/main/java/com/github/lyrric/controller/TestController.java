package com.github.lyrric.controller;

import com.github.lyrric.generator.EasyIdGenerator;
import com.github.lyrric.generator.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/test")
    String index(int count){
        for(int i=0;i<count;i++){
            logger.info(easyIdGenerator.get());
        }
        return "";
    }


}
