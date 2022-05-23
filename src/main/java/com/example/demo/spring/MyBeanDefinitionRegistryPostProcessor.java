package com.example.demo.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-05
 **/
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        final AbstractBeanDefinition bean = BeanDefinitionBuilder
                .genericBeanDefinition(String.class)
                .addConstructorArgValue("路人")
                .getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("userNameBean",bean);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        final Iterator<String> beanNamesIterator = configurableListableBeanFactory.getBeanNamesIterator();
        while (beanNamesIterator.hasNext()){
            System.out.println(beanNamesIterator.next());
        }
    }
}
