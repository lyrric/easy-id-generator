package com.github.lyrric.tool;

import com.github.lyrric.generator.IdGenerator;
import com.github.lyrric.generator.SnowFlakeIdGenerator;
import com.github.lyrric.properties.ClientConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
@Component
public class IdGeneratorTool {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private ClientConfigProperties clientConfigProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired(required = false)
    private IdGenerator idGenerator;

    @PostConstruct
    private void init(){
        //如果未自定义id生成策略，则使用默认的策略
        if(idGenerator == null){
            this.idGenerator = new SnowFlakeIdGenerator();
        }
    }

    public void addIds(){
        int minSize = clientConfigProperties.getIdListMinSize();
        String redisLockKey = clientConfigProperties.getRedisLockKey();
        String idListKey = clientConfigProperties.getIdListRedisKey();
        int redisLockTime = clientConfigProperties.getRedisLockTime();
        int count = clientConfigProperties.getIdListIncreaseNumber();
        Long size = redisTemplate.opsForList().size(idListKey);
        //如果存在的ID数量小于最小值就开始生成ID
        if(size == null || size < minSize){
            //Redis分布式锁
            List<Long> ids = idGenerator.generator(count);
            //noinspection ConstantConditions
            if(redisTemplate.opsForValue().setIfAbsent(redisLockKey, System.currentTimeMillis(), redisLockTime, TimeUnit.MILLISECONDS)){
                try {
                    redisTemplate.opsForList().leftPushAll(idListKey, ids);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    redisTemplate.delete(redisLockKey);
                }
            }
        }
    }
}
