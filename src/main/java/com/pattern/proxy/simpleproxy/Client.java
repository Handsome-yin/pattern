package com.pattern.proxy.simpleproxy;

/**
 *  .
 */
public class Client {

    public static void main(String[] args) {

        Proxy proxy = new Proxy(new RealSubject());
        proxy.request();

    }

}
