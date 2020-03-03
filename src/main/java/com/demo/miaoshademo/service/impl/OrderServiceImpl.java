package com.demo.miaoshademo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.dao.OrderMapper;
import com.demo.miaoshademo.dao.UserMapper;
import com.demo.miaoshademo.entity.MiaoshaOrder;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.entity.OrderInfo;
import com.demo.miaoshademo.redis.KeyMiaoshaUser;
import com.demo.miaoshademo.redis.KeyOrder;
import com.demo.miaoshademo.service.IOrderService;
import com.demo.miaoshademo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public OrderInfo addOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);

//        user = userMapper.getById(user.getId());
//        orderInfo.setUserId(Long.valueOf(user.getNickname()));
        orderInfo.setUserId(user.getId());
        orderMapper.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        orderMapper.insertMiaoshaOrder(miaoshaOrder);

        // 放入缓存
        String realKey = KeyOrder.getByUserIdAndGoodId(user.getId(), goods.getId()).getRealKey();
        redisTemplate.opsForValue().set(realKey, JSONObject.toJSONString(miaoshaOrder));
        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId) {
        String realKey = KeyOrder.getByUserIdAndGoodId(userId, goodsId).getRealKey();
        String redisStr = redisTemplate.opsForValue().get(realKey);
        MiaoshaOrder miaoshaOrder = JSONObject.parseObject(redisStr, MiaoshaOrder.class);
//        miaoshaOrder= orderMapper.getMiaoshaOrderByUserIdGoodsId(userId,goodsId);
        return miaoshaOrder;
    }
}
