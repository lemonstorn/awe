package com.zh.awe.web.factory;

import jakarta.annotation.Nonnull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * spring bean 工厂 用于获取容器中spring bean
 * 1. 通过实现ApplicationContextAware接口，注入ApplicationContext
 * 2. 通过实现ImportBeanDefinitionRegistrar接口，将自己注册到spring容器中
 * @author zh 2023/7/8 3:06
 */
public class WebBeanFactory implements ApplicationContextAware, ImportBeanDefinitionRegistrar {
    private static ApplicationContext ctx;

    @Override
    public void registerBeanDefinitions(@Nonnull AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(WebBeanFactory.class);
        registry.registerBeanDefinition("webBeanFactory", beanDefinition);
    }

    @Override
    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    /**
     * 通过class获取对象
     */
    public static <T> T getBean(Class<T> clazz){
        return ctx.getBean(clazz);
    }

    /**
     * 获取所有类型为requiredType的对象
     * @param requiredType 返回对象类型
     * @return  Map<String, T> 返回requiredType类型对象
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> requiredType){
         return ctx.getBeansOfType(requiredType);
    }
}
