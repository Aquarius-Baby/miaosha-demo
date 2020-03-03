package com.demo.miaoshademo.service.impl;

import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import com.demo.miaoshademo.dao.GoodsMapper;
import com.demo.miaoshademo.entity.MiaoshaGoods;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.entity.OrderInfo;
import com.demo.miaoshademo.service.IMiaoshaService;
import com.demo.miaoshademo.service.IOrderService;
import com.demo.miaoshademo.service.IUserService;
import com.demo.miaoshademo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaServiceImpl implements IMiaoshaService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    IOrderService orderService;

    @Autowired
    IUserService userService;

    @Transactional
    @Override
    public Result doMiaosha(long userId, long goodsId) {
        //减库存 下订单
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goodsId);
        int ret = goodsMapper.reduceStock(g);
        if (ret < 0) {
            return ResultGenerator.genFailResult("库存不足！");
        }
        GoodsVo goodsVo = goodsMapper.getGoodDetail(goodsId);
        MiaoshaUser user = userService.getById(userId);
        OrderInfo orderInfo = orderService.addOrder(user, goodsVo);
        return ResultGenerator.genSuccessResult(orderInfo);
    }


}
