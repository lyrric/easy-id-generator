package com.github.lyrric;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created on 2018/6/6.
 *
 * @author wangxiaodong
 */
@SpringBootApplication
public class ClientDemoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ClientDemoApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class, args);
    }
}
