package com.demo.miaoshademo.dao;

import com.demo.miaoshademo.entity.Goods;
import com.demo.miaoshademo.entity.MiaoshaGoods;
import com.demo.miaoshademo.vo.GoodsVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    List<GoodsVo> getGoodsList();

    @Select("select g.*, mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id" +
            " where g.id = #{goodId}")
    GoodsVo getGoodDetail(long goodId);

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStock(MiaoshaGoods g);

}
