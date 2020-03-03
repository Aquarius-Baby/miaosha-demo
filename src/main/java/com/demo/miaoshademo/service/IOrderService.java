package com.demo.miaoshademo.service;

import com.demo.miaoshademo.entity.MiaoshaOrder;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.entity.OrderInfo;
import com.demo.miaoshademo.vo.GoodsVo;

public interface IOrderService {
    OrderInfo addOrder(MiaoshaUser user, GoodsVo goods);

    OrderInfo getOrderById(long orderId);

    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(long userId, long goodsId);
}
