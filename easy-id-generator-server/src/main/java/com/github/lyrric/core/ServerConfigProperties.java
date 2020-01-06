package com.github.lyrric.core;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

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
    private String lockRedisKey = "id:generator:lock";
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
