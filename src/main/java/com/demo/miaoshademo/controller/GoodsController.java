package com.demo.miaoshademo.controller;

import com.demo.miaoshademo.Interceptor.AccessLimit;
import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.service.IGoodService;
import com.demo.miaoshademo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    IGoodService goodService;


    @AccessLimit(seconds = 10, maxCount = 5, needLogin = false)
    @GetMapping("/list")
    public Result getList() {
        List<GoodsVo> list = goodService.getGoodsList();
        return ResultGenerator.genSuccessResult(list);
    }

    //todo 接口限流 缓存[done]
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = false)
    @GetMapping("/detail")
    public Result detail(@RequestParam int goodsId) {
        GoodsVo goodsDetail = goodService.getGoodsById(goodsId);
        return ResultGenerator.genSuccessResult(goodsDetail);
    }


}
