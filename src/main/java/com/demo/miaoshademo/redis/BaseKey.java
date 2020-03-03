package com.demo.miaoshademo.redis;

import com.demo.miaoshademo.common.Constant;

public class BaseKey implements KeyPrefix {

    private int expireSeconds;

    private String prefix;

    private String key;

    private String realKey;

    public BaseKey(int expireSeconds, String prefix, String key) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
        this.key = key;
    }

    public BaseKey(String prefix, String key) {
        this(Constant.DEFAULT_EXPIRE_SECOND, prefix, key);
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRealKey() {
        String className = getClass().getSimpleName();
        return className+":"+prefix+"_"+key;
    }

    public void setRealKey(String realKey) {
        this.realKey = realKey;
    }

//    @Override
//    public int expireSeconds() {
//        return expireSeconds;
//    }
//
//    public String getPrefix() {
//        String className = getClass().getSimpleName();
//        return className + ":" + prefix;
//    }

}
