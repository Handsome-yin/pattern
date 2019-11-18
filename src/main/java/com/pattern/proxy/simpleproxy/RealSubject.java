package com.pattern.proxy.simpleproxy;

/**
 *  .
 */
public class RealSubject implements Subject{
    @Override
    public void request() {
        System.out.println("real service is called.");
    }
}
