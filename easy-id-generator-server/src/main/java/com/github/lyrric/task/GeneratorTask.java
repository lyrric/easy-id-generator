package com.github.lyrric.task;

import com.github.lyrric.core.ServerConfigProperties;
import com.github.lyrric.generator.DefaultIdGenerator;
import com.github.lyrric.generator.IdGenerator;
import com.github.lyrric.util.MyBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.CoderResult;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2020-01-07.
 *
 * @author wangxiaodong
 */
@Component
public class GeneratorTask {

    @Resource
    private StringRedisTemplate template;
    @Resource
    private ServerConfigProperties serverConfigProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private IdGenerator idGenerator;

    @Autowired(required = false)
    public GeneratorTask(IdGenerator idGenerator) {
        //如果未自定义id生成策略，则使用默认的策略
        if(idGenerator == null){
            this.idGenerator = new DefaultIdGenerator();
        }else{
            this.idGenerator = idGenerator;
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
        Long size = template.opsForList().size(idListKey);
        //如果存在的ID数量小于最小值就开始生成ID
        if(size == null || size < minSize){
            //Redis分布式锁
            //noinspection ConstantConditions
            if(template.opsForValue().setIfAbsent(redisLockKey, String.valueOf(System.currentTimeMillis()), redisLockTime, TimeUnit.MILLISECONDS)){
                try {
                    String lastId = template.opsForList().leftPop(idListKey);
                    logger.info("-----------------------------------------redis pop--------------------------------------");
                    logger.info("-----------------------------------------last id is {}--------------------------------------", lastId);
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
