package com.open.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送消息
 * @author Administrator
 * @date 2023/12/5 16:50
 * @version 1.0
 */
@RestController
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送消息
     * @author Administrator
     * @date 2023/12/5 16:54
     * @param msg
     * @param key
     * @return java.lang.String
    **/
    @GetMapping("sendMsg")
    public String sendMsg(@RequestParam String msg, @RequestParam String key) {
        rabbitTemplate.convertAndSend(RabbitConfig.ITEM_TOPIC_EXCHANGE, key, msg);
        return "send message success~";
    }


}
