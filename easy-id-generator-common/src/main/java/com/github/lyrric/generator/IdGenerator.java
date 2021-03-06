package com.github.lyrric.generator;

import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Created on 2020-01-06.
 *
 * @author wangxiaodong
 */
public interface IdGenerator {

    /**
     * 生产id
     * @param count 生成的数量
     * @return
     */
    List<Long> generator(int count);
}
