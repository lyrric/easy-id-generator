package com.github.lyrric.config;

import com.github.lyrric.core.ServerConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created on 2020-01-07.
 *
 * @author wangxiaodong
 */

@Configuration
@EnableConfigurationProperties(ServerConfigProperties.class)
@ComponentScan(basePackages = "com.github.lyrric")
public class WebConfiguration {
}
