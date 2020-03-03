package com.demo.miaoshademo.controller;

import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.Interceptor.AccessLimit;
import com.demo.miaoshademo.common.CmsStatus;
import com.demo.miaoshademo.common.Result;
import com.demo.miaoshademo.common.ResultGenerator;
import com.demo.miaoshademo.common.UserContext;
import com.demo.miaoshademo.entity.MiaoshaOrder;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.kafaka.MQProvider;
import com.demo.miaoshademo.redis.KeyGoods;
import com.demo.miaoshademo.service.IMiaoshaService;
import com.demo.miaoshademo.service.IOrderService;
import com.demo.miaoshademo.vo.GoodsVo;
import com.demo.miaoshademo.vo.MiaoShaMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    IMiaoshaService miaoshaService;

    @Autowired
    IOrderService orderService;

    @Autowired
    MQProvider provider;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public HashMap<Long, Boolean> localOverMap = new HashMap();

    // todo 请求的限流[done] 消息队列[done] redis库存[done]
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @PostMapping("/doMiaosha")
    public Result doMiaosha(@RequestBody MiaoShaMessageVo vo) {
        Long goodsId = vo.getGoodId();
        MiaoshaUser miaoshaUser = UserContext.getUser();
        vo.setUserId(miaoshaUser.getId());

        //是否已经秒杀过
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), vo.getGoodId());
        if (order != null) {
            return ResultGenerator.genFailResult(CmsStatus.REPEAT_MIAOSHA.getMessage());
        }
        // 内存标记，减少对redis的访问
        boolean over = localOverMap.getOrDefault(goodsId, false);
        if (over) {
            return ResultGenerator.genFailResult(CmsStatus.MIAO_SHA_OVER.getMessage());
        }

        //redis库存判断
        String goodsRealKey = KeyGoods.getById(goodsId).getRealKey();
        String redisStr = redisTemplate.opsForValue().get(goodsRealKey);
        if(redisStr != null){
            GoodsVo goodsVo = JSONObject.parseObject(redisStr, GoodsVo.class);
//             String res = redisTemplate.opsForHash().get(goodsRealKey, '1');
            if (goodsVo.getStockCount() <= 0) {
                localOverMap.put(goodsId, true);
                return ResultGenerator.genFailResult(CmsStatus.MIAO_SHA_OVER.getMessage());
            }
        }
//        if(redisStr != null){
//            long stock = JSONObject.parseObject(redisStr, Long.class);
//            redisTemplate.opsForHash().get(goodsRealKey, '1');
//            if (stock <= 0) {
//                localOverMap.put(goodsId, true);
//                return ResultGenerator.genFailResult(CmsStatus.MIAO_SHA_OVER.getMessage());
//            }
//        }

        // 发送至消息队列
        provider.sendMessage(vo);
        return ResultGenerator.genSuccessResult();
    }


}
