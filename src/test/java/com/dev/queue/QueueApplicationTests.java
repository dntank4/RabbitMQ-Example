package com.dev.queue;

import com.dev.queue.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class QueueApplicationTests {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.routingKey}")
    String routingKey;

    String validJson = "{\"id\": 1, \"name\": \"Harry Potter\"}";
    String inValidJson = "{\"id\": 1, \"name\": \"Harry Potter\"";

    @Test
    void generateSuccessTest() throws InterruptedException {
        rabbitTemplate.convertAndSend(routingKey, validJson);
        Thread.sleep(1000);
        log.info("SUCCESS COUNT: - {}{}ERROR COUNT: - {}", CommonUtils.getSuccessCount(), System.lineSeparator(), CommonUtils.getErrorCount());
    }
    @Test
    void generateErrorTest() throws InterruptedException {
        rabbitTemplate.convertAndSend(routingKey, inValidJson);
        Thread.sleep(1000);
        log.info("SUCCESS COUNT: - {}{}ERROR COUNT: - {}", CommonUtils.getSuccessCount(), System.lineSeparator(), CommonUtils.getErrorCount());
    }

}
