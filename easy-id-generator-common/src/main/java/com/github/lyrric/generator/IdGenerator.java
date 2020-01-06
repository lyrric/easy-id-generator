package com.github.lyrric.generator;

import java.util.List;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
public interface IdGenerator {

    /**
     * 生产id
     * @param lastId 最后一个id
     * @param count 生成的数量
     * @return
     */
    List<String> generator(String lastId, int count);
}
