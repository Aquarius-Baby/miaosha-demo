package com.demo.miaoshademo.redis;

public class KeyMiaoshaUser extends BaseKey {

    public KeyMiaoshaUser(String prefix, String key) {
        super(prefix, key);
    }

    public static KeyMiaoshaUser getByToken(String key) {
        return new KeyMiaoshaUser("token", key);
    }

    public KeyMiaoshaUser getByMobile(String key) {
        return new KeyMiaoshaUser("mobile", key);
    }

    public KeyMiaoshaUser getById(String key) {
        return new KeyMiaoshaUser("id", key);
    }

}
