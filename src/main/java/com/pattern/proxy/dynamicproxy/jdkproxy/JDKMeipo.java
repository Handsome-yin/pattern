package com.pattern.proxy.dynamicproxy.jdkproxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 */
public class JDKMeipo implements InvocationHandler {

    private Object target;

    public Object getInstance(Object target) throws Exception {
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object obj = null;
        if (method.getName().equals("findLove")) {
            before();
            obj = method.invoke(this.target, args);
            System.out.println(method.getName());
            after();
        } else {
            obj = method.invoke(this.target, args);
        }

        return obj;
    }

    private void before() {
        System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求");
        System.out.println("开始物色");
    }

    private void after() {
        System.out.println("OK的话，准备办事");
    }
}
