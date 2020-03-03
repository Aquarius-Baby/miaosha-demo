package com.demo.miaoshademo.redis;

public class KeyUser extends BaseKey {

    public KeyUser(int expireSeconds, String prefix, String key) {
        super(expireSeconds, prefix, key);
    }

    private KeyUser(String prefix, String key) {
        super(prefix, key);
    }

    public static KeyUser getById(String key) {
        return new KeyUser("id", key);
    }

    public KeyUser getByName(String key) {
        return new KeyUser("name", key);
    }

}
