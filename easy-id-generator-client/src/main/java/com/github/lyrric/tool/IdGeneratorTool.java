package com.github.lyrric.tool;

import com.github.lyrric.generator.DefaultIdGenerator;
import com.github.lyrric.generator.IdGenerator;
import com.github.lyrric.properties.ClientConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
@Component
public class IdGeneratorTool {

    @Resource
    private StringRedisTemplate template;
    @Resource
    private ClientConfigProperties clientConfigProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private IdGenerator idGenerator = new DefaultIdGenerator();

    public void addIds(){
        int minSize = clientConfigProperties.getIdListMinSize();
        String redisLockKey = clientConfigProperties.getRedisLockKey();
        String idListKey = clientConfigProperties.getIdListRedisKey();
        int redisLockTime = clientConfigProperties.getRedisLockTime();
        int count = clientConfigProperties.getIdListIncreaseNumber();
        Long size = template.opsForList().size(idListKey);
        //如果存在的ID数量小于最小值就开始生成ID
        if(size == null || size < minSize){
            //Redis分布式锁
            //noinspection ConstantConditions
            if(template.opsForValue().setIfAbsent(redisLockKey, String.valueOf(System.currentTimeMillis()), redisLockTime, TimeUnit.MILLISECONDS)){
                try {
                    String lastId = template.opsForList().leftPop(idListKey);
                    template.opsForList().leftPushAll(idListKey, idGenerator.generator(lastId, count));
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    template.delete(redisLockKey);
                }
            }
        }
    }
}