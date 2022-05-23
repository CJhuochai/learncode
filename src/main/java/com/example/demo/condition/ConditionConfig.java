package com.example.demo.condition;

import cn.hutool.core.lang.id.NanoId;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2022-02
 **/
@Configuration
public class ConditionConfig {

    @Bean
    @Conditional(value = {WinCondition.class})
    public void win(){
        System.out.println("----------------------------------windows");
    }
    @Bean
    @Conditional(value = {LinCondition.class})
    public void lin(){
        System.out.println("----------------------------------linux");
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConditionConfig.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        final String s1 = NanoId.randomNanoId();
        final String s = NanoIdUtils.randomNanoId();
        System.out.println(s1 + ":" + s);

        final String a = SecureUtil.md5("af91c438880fc7f7171a2722d7064ec4");

        Set set = new HashSet();
        System.out.println(a);
    }
}
