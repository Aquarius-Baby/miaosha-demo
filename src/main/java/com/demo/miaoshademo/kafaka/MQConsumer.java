package com.demo.miaoshademo.kafaka;

import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.common.UserContext;
import com.demo.miaoshademo.entity.MiaoshaOrder;
import com.demo.miaoshademo.entity.MiaoshaUser;
import com.demo.miaoshademo.service.IGoodService;
import com.demo.miaoshademo.service.IMiaoshaService;
import com.demo.miaoshademo.service.IOrderService;
import com.demo.miaoshademo.vo.GoodsVo;
import com.demo.miaoshademo.vo.MiaoShaMessageVo;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.Executors;

@Component
public class MQConsumer {

    @Autowired
    IGoodService goodService;

    @Autowired
    IOrderService orderService;
    @Autowired
    IMiaoshaService miaoshaService;

    @KafkaListener(topics = {"miaosha"})
    public void receive(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment) throws InterruptedException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String res = (String) kafkaMessage.get();
            MiaoShaMessageVo message = JSONObject.parseObject(res, MiaoShaMessageVo.class);
            System.out.println("receive message: " + JSONObject.toJSONString(message));

            //扣库存 下订单

            long goodsId = message.getGoodId();
            GoodsVo goods = goodService.getGoodsById(goodsId);
            int stock = goods.getStockCount();
            if (stock <= 0) {
                return;
            }
            //判断是否已经秒杀到了
            MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(message.getUserId(), goodsId);
            if (order != null) {
                return;
            }
            //todo 待修改--- 减库存 下订单 写入秒杀订单
            miaoshaService.doMiaosha(message.getUserId(), goodsId);
        }
        // 模拟提交
        acknowledgment.acknowledge();
    }

    //    @KafkaListener(topics = {"miaosha"})
    public void receiveTest(ConsumerRecord<?, ?> record, Acknowledgment acknowledgment) throws InterruptedException {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            String res = (String) kafkaMessage.get();
            MiaoShaMessageVo message = JSONObject.parseObject(res, MiaoShaMessageVo.class);
//            MiaoShaMessageVo message = JSONObject.parseObject( kafkaMessage.get(),MiaoShaMessageVo.class);
            System.out.println("receive message: " + JSONObject.toJSONString(message));
        }
        // 模拟提交
        acknowledgment.acknowledge();
    }
}
