package com.demo.miaoshademo.service;

import com.demo.miaoshademo.vo.GoodsVo;

import java.util.List;

public interface IGoodService {

    List<GoodsVo> getGoodsList();

    GoodsVo getGoodsById(long goodId);


}
