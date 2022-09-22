package com.example.demo.liability;

import com.example.demo.proxy.cglib.CglibProxy;
import org.springframework.cglib.proxy.Callback;

/** 责任链模式
 * @create: 2022-09
 **/
public class TestMain {

    public static void main(String[] args) {
        Handler.Builder<Person> builder = new Handler.Builder<>();
        final Person person = new Person("陈", "");
       builder
            .addHandler(new UserHandler())
            .addHandler(new NameHandler())
            .addHandler(new PassWordHandler());
        final Handler<Person> check = builder.check();
        Callback[] callbacks = {
                new CglibProxy(check)};
        final Handler<Person> cglibProxy = CglibProxy.createCglibProxy(check, callbacks);
        cglibProxy.doHandler(person);
    }
}
