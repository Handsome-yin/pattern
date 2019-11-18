package com.pattern.delegate.simple;

/**
 *
 */
public class Boss {

    public void command(String command,Leader leader){
        leader.doing(command);
    }

}

