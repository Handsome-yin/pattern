package com.pattern.singleton.lazy;


public class LazyTwo {

    private LazyTwo(){}

    private static LazyTwo lazy = null;

    //synchronized 加在方法上会锁着整个类 建议加在方法中 双重锁定 只会锁定当前方法
    public static synchronized LazyTwo getInstance(){

        if(lazy == null){
            lazy = new LazyTwo();
        }
        return lazy;

    }
    //防止序列化破环单例
    private  Object readResolve(){
        return  getInstance();
    }
}
