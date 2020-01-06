package com.github.lyrric.generator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
public class DefaultIdGenerator implements IdGenerator {

    private static final String PRE_FIX = "id-gen:";

    private static final String FIRST_ID = PRE_FIX.concat("10000");

    @Override
    public List<String> generator(String lastId, int count) {
        if(lastId == null){
            lastId = FIRST_ID;
        }
        BigInteger num = new BigInteger(lastId.replace(PRE_FIX, ""));
        List<String> result = new ArrayList<>(count+1);
        result.add(lastId);
        for(int i = 0;i<count;i++){
            num = num.add(BigInteger.ONE);
            result.add(PRE_FIX.concat(num.toString()));
        }
        return result;
    }
}
