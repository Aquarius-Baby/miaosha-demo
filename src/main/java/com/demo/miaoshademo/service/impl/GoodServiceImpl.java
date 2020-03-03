package com.demo.miaoshademo.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.dao.GoodsMapper;
import com.demo.miaoshademo.redis.KeyGoods;
import com.demo.miaoshademo.service.IGoodService;
import com.demo.miaoshademo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodServiceImpl implements IGoodService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    GoodsMapper goodsMapper;


    @Override
    public List<GoodsVo> getGoodsList() {
        List<GoodsVo> list = goodsMapper.getGoodsList();
        return list;
    }

    @Override
    @Transactional()
    public GoodsVo getGoodsById(long goodId) {
        String realKey = KeyGoods.getById(goodId).getRealKey();
        String redisStr = redisTemplate.opsForValue().get(realKey);
        GoodsVo goodsVo = JSONObject.parseObject(redisStr, GoodsVo.class);
        if (null != goodsVo) {
            return goodsVo;
        }
        goodsVo = goodsMapper.getGoodDetail(goodId);
        redisTemplate.opsForValue().set(realKey, JSONObject.toJSONString(goodsVo));
        return goodsVo;
    }


}
