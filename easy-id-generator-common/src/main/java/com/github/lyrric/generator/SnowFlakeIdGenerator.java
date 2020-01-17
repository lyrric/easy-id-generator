package com.github.lyrric.generator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020-01-06.
 * 简单雪花算法(机器ID默认为1)
 * @author wangxiaodong
 */
public class SnowFlakeIdGenerator implements IdGenerator {

    /** 机房ID */
    private final int datacenterId = 1;
    /** 机器ID */
    private final int workId = 1;

    /** 神秘代码 */
    private final long twepoch = 1288834974657L;

    /**时间占41位*/
    private final int timeBitWidth = 22;
    /**机房占5位*/
    private final int datacenterBitWidth = 17;
    /**机器占5位*/
    private final int workerBitWidth = 12;

    private long lastTime = System.currentTimeMillis();

    /** 当前毫秒内生成了多少个id */
    private long sequence = 0;

    /**
     * 生成一个ID
     * @return
     */
    private long generatorOne(){
        long time = System.currentTimeMillis();
        if(time != lastTime){
            //下一毫秒，sequence置为0
            sequence = 0;
        }else if(sequence > 4095){
            //当前毫秒内生成的id数超过了4096个数字，则sequence置0，获取下一毫秒进行生成
            sequence = 0;
            while((time = System.currentTimeMillis()) != lastTime){}
        }
        lastTime = time;
        long id = (((time-twepoch) << timeBitWidth)
                | (datacenterId << datacenterBitWidth)
                | (workId << workerBitWidth)
                | sequence++);
        return id;
    }

    @Override
    public List<Long> generator(int count) {
        List<Long> result = new ArrayList<>(count+1);
        for(int i = 0;i<count;i++){
            result.add(generatorOne());
        }
        return result;
    }
}
