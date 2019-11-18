package com.pattern.proxy.dynamicproxy.gpproxy;

import com.pattern.proxy.Person;
import com.pattern.proxy.dynamicproxy.jdkproxy.Girl;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;

/**
 *
 */
public class GPProxyTest {

    public static void main(String[] args) {
        try {

            //JDK动态代理的实现原理
            Person obj = (Person) new GPMeipo().getInstance(new Girl());
            System.out.println(obj.getClass());
            obj.findLove();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
