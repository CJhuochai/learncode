package com.example.demo.thread;

import com.example.demo.entity.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @program: limiting-demo
 * @description:
 * @author: Jian Chen
 * @create: 2021-11
 **/
public class Test01 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        /*final ArrayList<User> users = new ArrayList<>();
        users.add(new User("chenjian",6));
        users.add(new User("xiaoming",10));

        final Stream<User> stream = users.stream();
        final Map<String, User> collect = stream.collect(Collectors.toMap(user -> user.getName(), user -> user));
        collect.forEach((k,v) -> System.out.println("k:" + k + ",value:" + v));*/

        final Class userClass1 = Class.forName("com.example.demo.entity.User");
        final User classUser = (User) userClass1.newInstance();
        /*final Class userClass2 = User.class;
        final User user1 = (User) userClass1.newInstance();
        System.out.println(user1.toString());

        final Constructor declaredConstructor = userClass1.getDeclaredConstructor(String.class, Integer.class);
        final User user2 = (User) declaredConstructor.newInstance("陈健", 199);
        System.out.println(user2);*/

        /*final Field[] fields = userClass1.getFields();
        for (Field field:fields) {
            System.out.println(field);
        }
        final Field[] declaredFields = userClass1.getDeclaredFields();
        for (Field field:declaredFields) {
            System.out.println(field);
        }*/
        /*final Method[] declaredMethods = userClass1.getDeclaredMethods();
        Stream.of(declaredMethods).forEach(e -> System.out.println(e));*/
        /*final Field publicParm = userClass1.getDeclaredField("publicParm");
        final Field name = userClass1.getDeclaredField("name");
        publicParm.set(classUser,"我是公共参数");
        name.setAccessible(true);
        name.set(classUser,"陈健");
        System.out.println(classUser);*/
        final Method privateToString = userClass1.getDeclaredMethod("privateToString");
        privateToString.setAccessible(true);
        final String invoke = (String) privateToString.invoke(classUser);
        System.out.println(invoke);
    }

}
