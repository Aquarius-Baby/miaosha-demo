package com.demo.miaoshademo.redis;

public class KeyOrder extends BaseKey {

    public KeyOrder(int expireSeconds, String prefix, String key) {
        super(expireSeconds, prefix, key);
    }

    public KeyOrder(String prefix, String key) {
        super(prefix, key);
    }

    public static KeyOrder getByUserIdAndGoodId(long userId, long goodsId) {
        return new KeyOrder("id", "" + userId + "_" + goodsId);
    }


}
