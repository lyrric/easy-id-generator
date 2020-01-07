package com.github.lyrric.generator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020-01-07.
 *
 * @author wangxiaodong
 */
public class MyGenerator implements IdGenerator {

    @Override
    public List<String> generator(String lastId, int count) {
        List<String> result = new ArrayList<>(count+1);
        int init;
        if(lastId == null){
            init = 1000;
        }else{
            init = Integer.parseInt(lastId);
        }
        result.add(String.valueOf(init));
        for (int i = 0; i < count; i++) {
            result.add(String.valueOf(init++));
        }
        return result;
    }
}
