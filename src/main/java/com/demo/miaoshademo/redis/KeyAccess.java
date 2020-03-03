package com.demo.miaoshademo.redis;

public class KeyAccess extends BaseKey {

    public KeyAccess(int expireSeconds, String prefix, String key) {
        super(expireSeconds, prefix, key);
    }

    public static KeyAccess getById(int expireSeconds, String key) {
        return new KeyAccess(expireSeconds, "access", key);
    }


}
