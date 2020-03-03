//package com.demo.miaoshademo.redis;
//
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class RedisService {
//
//    @Autowired
//    private StringRedisTemplate redisTemplate;
//
//    public String test1() {
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        list.add("3");
//
//        String res = redisTemplate.opsForValue().get("lily");
//        redisTemplate.opsForValue().increment("lily");
////        // 向redis存入List
////        stringRedisTemplate.opsForList().leftPushAll("qq2", list);
////        // 从redis获取List
////        stringRedisTemplate.opsForList().range("qwe", 0, -1).forEach(value -> {
////            System.out.println(value);
////        });
//        return res;
//    }
//
//    /**
//     * 设置失效时间
//     * @param key
//     * @param value
//     * @return
//     */
////    public Long setnx(String key ,String value){
////
////        Long result = null;
////        try {
////            jedis = jedisPool.getResource();
////            result = jedis.setnx(key,value);
////        }catch (Exception e){
////            log.error("expire key:{} error",key,e);
////            jedisPool.returnResource(jedis);
////            return  result;
////        }
////        jedisPool.returnResource(jedis);
////        return  result;
////
////    }
//    /**
//     * 设置key的有效期，单位是秒
//     * @param key
//     * @param exTime
//     * @return
//     */
////    public Long expire(String key,int exTime){
////        Jedis jedis = null;
////        Long result = null;
////        try {
////            jedis =  jedisPool.getResource();
////            result = jedis.expire(key,exTime);
////        } catch (Exception e) {
////            log.error("expire key:{} error",key,e);
////            jedisPool.returnBrokenResource(jedis);
////            return result;
////        }
////        jedisPool.returnResource(jedis);
////        return result;
////    }
//
//    /**
//     * 获取当个对象
//     */
//    public <T> T getObject(KeyPrefix prefix, String key, Class<T> clazz) {
//        //生成真正的key
//        String realKey = prefix.getPrefix() + key;
//        String str = redisTemplate.opsForValue().get(realKey);
//        return JSONObject.parseObject(str, clazz);
//    }
//
//    public String get(String key) {
//        String str = redisTemplate.opsForValue().get(key);
//        return str;
//    }
//
//
//    public void set(String key, String value) {
//        redisTemplate.opsForValue().set(key,value);
//    }
//
//    /**
//     * 设置对象
//     */
//    public <T> void setObject(KeyPrefix prefix, String key, T value) {
//        String realKey = prefix.getPrefix() + key;
//        redisTemplate.opsForValue().set(realKey,JSONObject.toJSONString(value));
//    }
//
//    /**
//     * 判断key是否存在
//     */
//    public <T> boolean exists(KeyPrefix prefix, String key) {
//        String realKey = prefix.getPrefix() + key;
//        String res = redisTemplate.opsForValue().get(realKey);
//        return res == null ? false : true;
//    }
//
//    /**
//     * 删除
//     */
//    public boolean delete(KeyPrefix prefix, String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            //生成真正的key
//            String realKey = prefix.getPrefix() + key;
//            long ret = jedis.del(realKey);
//            return ret > 0;
//        } finally {
//            returnToPool(jedis);
//        }
//    }
//
//    /**
//     * 增加值
//     */
//    public <T> Long incr(KeyPrefix prefix, String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            //生成真正的key
//            String realKey = prefix.getPrefix() + key;
//            return jedis.incr(realKey);
//        } finally {
//            returnToPool(jedis);
//        }
//    }
//
//    /**
//     * 减少值
//     */
//    public <T> Long decr(KeyPrefix prefix, String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            //生成真正的key
//            String realKey = prefix.getPrefix() + key;
//            return jedis.decr(realKey);
//        } finally {
//            returnToPool(jedis);
//        }
//    }
//
//    public Long del(String key) {
//        Jedis jedis = null;
//        Long result = null;
//        try {
//            jedis = jedisPool.getResource();
//            result = jedis.del(key);
//        } catch (Exception e) {
//            log.error("del key:{} error", key, e);
//            jedisPool.returnBrokenResource(jedis);
//            return result;
//        }
//        jedisPool.returnResource(jedis);
//        return result;
//    }
//
//
//    public boolean delete(KeyPrefix prefix) {
//        if (prefix == null) {
//            return false;
//        }
//        List<String> keys = scanKeys(prefix.getPrefix());
//        if (keys == null || keys.size() <= 0) {
//            return true;
//        }
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            jedis.del(keys.toArray(new String[0]));
//            return true;
//        } catch (final Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    public List<String> scanKeys(String key) {
//        Jedis jedis = null;
//        try {
//            jedis = jedisPool.getResource();
//            List<String> keys = new ArrayList<String>();
//            String cursor = "0";
//            ScanParams sp = new ScanParams();
//            sp.match("*" + key + "*");
//            sp.count(100);
//            do {
//                ScanResult<String> ret = jedis.scan(cursor, sp);
//                List<String> result = ret.getResult();
//                if (result != null && result.size() > 0) {
//                    keys.addAll(result);
//                }
//                //再处理cursor
//                cursor = ret.getStringCursor();
//            } while (!cursor.equals("0"));
//            return keys;
//        } finally {
//            if (jedis != null) {
//                jedis.close();
//            }
//        }
//    }
//
//    public static <T> String beanToString(T value) {
//        if (value == null) {
//            return null;
//        }
//        Class<?> clazz = value.getClass();
//        if (clazz == int.class || clazz == Integer.class) {
//            return "" + value;
//        } else if (clazz == String.class) {
//            return (String) value;
//        } else if (clazz == long.class || clazz == Long.class) {
//            return "" + value;
//        } else {
//            return JSON.toJSONString(value);
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> T stringToBean(String str, Class<T> clazz) {
//        if (str == null || str.length() <= 0 || clazz == null) {
//            return null;
//        }
//        if (clazz == int.class || clazz == Integer.class) {
//            return (T) Integer.valueOf(str);
//        } else if (clazz == String.class) {
//            return (T) str;
//        } else if (clazz == long.class || clazz == Long.class) {
//            return (T) Long.valueOf(str);
//        } else {
//            return JSON.toJavaObject(JSON.parseObject(str), clazz);
//        }
//    }
//
//    private void returnToPool(Jedis jedis) {
//        if (jedis != null) {
//            jedis.close();
//        }
//    }
//}
