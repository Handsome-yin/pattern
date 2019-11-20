package com.pattern.proxy.dynamicproxy.jdkproxy;


import com.pattern.proxy.Person;

/**
 *
 */
public class Girl implements Person {
    public void findLove() {
        System.out.println("高富帅");
        System.out.println("身高180cm");
        System.out.println("有6块腹肌");
    }

    public Girl() {
        System.out.println("construting ...");
    }

    @Override
    public String toString() {
        System.out.println("Girl  is tostring");
        return "Girl  is tostring";//super.toString();
    }
}
