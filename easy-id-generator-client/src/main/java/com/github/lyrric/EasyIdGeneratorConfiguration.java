package com.github.lyrric;

import com.github.lyrric.properties.ClientConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
@Configuration
@EnableConfigurationProperties(ClientConfigProperties.class)
@ComponentScan(basePackages = "com.github.lyrric")
public class EasyIdGeneratorConfiguration {


}
