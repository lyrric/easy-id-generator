package com.github.lyrric.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created on 2019/3/14.
 *
 * @author wangxiaodong
 */
@ConfigurationProperties(prefix = "easy.generator.server")
@Data
public class ServerConfigProperties {
    /**
     * lock key
     */
    private String redisLockKey = "id:generator:lock";
    /**
     * lock time,unit millisecond
     */
    private int redisLockTime = 5000;
    /**
     * to save id list
     */
    private String idListRedisKey  = "id:generator:list";
    /**
     * id list min size
     */
    private Integer idListMinSize = 200;
    /**
     * each time add id number
     */
    private Integer idListIncreaseNumber = 50;
}
