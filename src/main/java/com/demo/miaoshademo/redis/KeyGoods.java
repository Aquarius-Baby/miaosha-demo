package com.demo.miaoshademo.redis;

public class KeyGoods extends BaseKey {

    public KeyGoods(String prefix, String key) {
        super(prefix, key);
    }

    public static KeyGoods getById(long key) {
        return new KeyGoods("id", "" + key);
    }

    public KeyGoods getByName(String key) {
        return new KeyGoods("name", key);
    }


}
