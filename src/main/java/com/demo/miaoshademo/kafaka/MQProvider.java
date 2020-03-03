package com.demo.miaoshademo.kafaka;

import com.alibaba.fastjson.JSONObject;
import com.demo.miaoshademo.vo.MiaoShaMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MQProvider {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(MiaoShaMessageVo vo) {
        vo.setSendTime(new Date());
        System.out.println("send: " + JSONObject.toJSONString(vo));
        kafkaTemplate.send("miaosha", JSONObject.toJSONString(vo));
    }
}
