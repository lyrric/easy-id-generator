package com.github.lyrric.core;

import com.github.lyrric.tool.IdGeneratorTool;
import com.github.lyrric.tool.MyBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created on 2020-01-06.
 * 进行初始化操作
 * @author wangxiaodong
 */
@Component
@Order(1)
public class Init implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    private IdGeneratorTool idGeneratorTool;

    @Override
    public void run(String... args) throws Exception {
        logger.info("init add ids");
        idGeneratorTool.addIds();
    }
}
