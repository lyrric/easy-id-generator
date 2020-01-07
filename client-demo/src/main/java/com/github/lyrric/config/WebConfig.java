package com.github.lyrric.config;

import com.github.lyrric.generator.IdGenerator;
import com.github.lyrric.generator.MyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2020-01-07.
 *
 * @author wangxiaodong
 */
@Configuration
public class WebConfig {

    /**
     * 自定义id生成策略
     * @return
     */
    @Bean
    public IdGenerator myGenerator(){
        return new MyGenerator();
    }
}
