package com.github.lyrric.tool;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created on 2020-01-07.
 *
 * @author wangxiaodong
 */
@Component
public class MyBeanUtil implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }
    public static <T> T getObject(Class<T> clazz) {
        try {
            return ctx.getBean(clazz);
        }catch (Exception e){
            return null;
        }

    }
}
