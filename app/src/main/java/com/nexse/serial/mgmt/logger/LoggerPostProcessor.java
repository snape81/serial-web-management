package com.nexse.serial.mgmt.logger;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;


public class LoggerPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                if (field.getAnnotation(Log.class) != null) {
                        Logger logger = LoggerFactory.getLogger(bean.getClass());
                        field.set(bean,logger);
                }
            }
        });

        return null;
    }
}
