package com.demo.miaoshademo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.Interceptor.AccessLimit;
import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.entity.OrderInfo;
import com.demo.miaoshademo.service.IMiaoshaService;
import com.demo.miaoshademo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    IMiaoshaService miaoshaService;

    @Autowired
    IOrderService orderService;

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @GetMapping("/detail")
    public Result detail(long orderId) {
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        return ResultGenerator.genSuccessResult(orderInfo);
    }

}
