package com.dev.queue.listener;

import com.dev.queue.model.Book;
import com.dev.queue.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RabbitMQListener {


    @Autowired
    ObjectMapper objectMapper;

    @RabbitListener(queues = "${spring.rabbitmq.queueName}")
    public void listenMessage(String msg) {
        try {
            log.info("Received {}", objectMapper.readValue(msg, Book.class));
            CommonUtils.logSuccess();
        } catch (Exception e) {
            log.error("Received {}", msg);
            log.error(e.getMessage());
            CommonUtils.logError();
        }
    }
}
