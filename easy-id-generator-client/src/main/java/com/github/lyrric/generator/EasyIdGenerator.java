package com.github.lyrric.generator;

import com.github.lyrric.properties.ClientConfigProperties;
import com.github.lyrric.tool.IdGeneratorTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
@Component
public class EasyIdGenerator {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;
    @Resource
    private ClientConfigProperties clientConfigProperties;
    @Resource
    private IdGeneratorTool idGeneratorTool;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private GeneratorThread generatorThread = new GeneratorThread();

    /**
     * 获取ID
     * @return
     */
    public Long get(){
        Long id = redisTemplate.opsForList().rightPop(clientConfigProperties.getIdListRedisKey());
        int minSize = clientConfigProperties.getIdListMinSize();
        if("client".equals(clientConfigProperties.getGeneratorModel())){
            //noinspection ConstantConditions
            if(redisTemplate.opsForList().size(clientConfigProperties.getIdListRedisKey()) < minSize){
                executorService.submit(generatorThread);
            }
        }
        return id;
    }

    class GeneratorThread implements Runnable{

        private Logger logger = LoggerFactory.getLogger(this.getClass());

        @Override
        public void run() {
            idGeneratorTool.addIds();
        }
    }

}
