package com.pattern.adapter.loginadapter.v2.adapters;


import com.pattern.adapter.loginadapter.ResultMsg;

/**
 *  .
 */
public class LoginForTelAdapter implements LoginAdapter {
    public boolean support(Object adapter) {
        return adapter instanceof LoginForTelAdapter;
    }
    public ResultMsg login(String id, Object adapter) {
        return null;
    }
}
