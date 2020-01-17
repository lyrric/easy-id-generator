package com.github.lyrric.task;

import com.github.lyrric.core.ServerConfigProperties;
import com.github.lyrric.generator.SnowFlakeIdGenerator;
import com.github.lyrric.generator.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-01-07.
 *
 * @author wangxiaodong
 */
@Component
public class GeneratorTask {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    @Resource
    private ServerConfigProperties serverConfigProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    private IdGenerator idGenerator;

   @PostConstruct
   private void init(){
       //如果未自定义id生成策略，则使用默认的策略
       if(idGenerator == null){
           this.idGenerator = new SnowFlakeIdGenerator();
       }
   }


    /**
     * 定时任务，检查id数量是否小于最小值时，生成id
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void task(){
        int minSize = serverConfigProperties.getIdListMinSize();
        String redisLockKey = serverConfigProperties.getRedisLockKey();
        String idListKey = serverConfigProperties.getIdListRedisKey();
        int redisLockTime = serverConfigProperties.getRedisLockTime();
        int count = serverConfigProperties.getIdListIncreaseNumber();
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
