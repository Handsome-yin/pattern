package com.pattern.proxy.dynamicproxy.jdkproxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 *
 */
public class JDKProxyTest {

    public static void main(String[] args) {
        try {
            Girl girl = new Girl();
            System.out.println("");
            Object obj = new JDKMeipo().getInstance(new Girl());
            Method method = obj.getClass().getMethod("findLove",null);
            method.invoke(obj);

            //obj.findLove();

            //$Proxy0
//            byte [] bytes = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
//            FileOutputStream os = new FileOutputStream("E://$Proxy0.class");
//            os.write(bytes);
//            os.close();

        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
