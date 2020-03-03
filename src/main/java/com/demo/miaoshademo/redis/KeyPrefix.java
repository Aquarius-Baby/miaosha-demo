package com.demo.miaoshademo.redis;

public interface KeyPrefix {
    int expireSeconds();

    String getPrefix();

}
