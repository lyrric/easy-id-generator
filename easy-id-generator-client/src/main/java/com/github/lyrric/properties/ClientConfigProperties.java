package com.github.lyrric.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created on 2019/3/14.
 *
 * @author wangxiaodong
 */
@ConfigurationProperties(prefix = "easy.generator.client")
@Data
public class ClientConfigProperties {

    /**
     * generator in client or server
     */
    private String generatorModel = "client";
    /**
     * lock key
     */
    private String redisLockKey = "id:generator:lock";
    /**
     * lock time,unit millisecond
     */
    private int redisLockTime = 500;
    /**
     * to save id list
     */
    private String idListRedisKey  = "id:generator:list";
    /**
     * id list default size
     */
    private Integer idListDefaultSize = 100;
    /**
     * id list min size
     */
    private Integer idListMinSize = 50;
    /**
     * each time add id number
     */
    private Integer idListIncreaseNumber = 50;
}
