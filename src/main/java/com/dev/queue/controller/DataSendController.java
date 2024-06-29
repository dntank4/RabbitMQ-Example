package com.dev.queue.controller;

import com.dev.queue.model.Book;
import com.dev.queue.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DataSendController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${spring.rabbitmq.routingKey}")
    String routingKey;

    @GetMapping("/send")
    public String sendMessageToQueue(@RequestBody Book book) {
        log.info("MSG Received {}", book);
        try {
            rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(book));
            // Delay to Process The Data through Listener
            Thread.sleep(1000);
            return "SUCCESS COUNT: - " + CommonUtils.getSuccessCount() + System.lineSeparator() + "ERROR COUNT: - " + CommonUtils.getErrorCount();
        } catch (JsonProcessingException | InterruptedException e) {
            return "MSG WASN'T SENT TO QUEUE" + System.lineSeparator() + e.getMessage();
        }

    }

    @GetMapping("/sendMultiple")
    public String sendMultipleMessageToQueue(@RequestParam Integer count) throws JsonProcessingException, InterruptedException {
        StringBuilder sb = new StringBuilder();
        if (count > 1) {
            log.info("MSG Received To Send {} Data", count);
            for (int i = 1; i <= count; i++) {
                rabbitTemplate.convertAndSend(routingKey, objectMapper.writeValueAsString(new Book(i, "Harry Potter" + i)));
                sb.append("MSG SENT TO QUEUE : ").append(new Book(i, "Harry Potter" + i));
                sb.append(System.lineSeparator());
            }
            // Delay to Process The Data through Listener
            Thread.sleep(1000);
            return "SUCCESS COUNT: - " + CommonUtils.getSuccessCount() + System.lineSeparator() + "ERROR COUNT: - " + CommonUtils.getErrorCount();
        } else {
            return "COUNT SHOULD BE > 1 ";
        }
    }


    @GetMapping("/getCount")
    public String getCount() {
        log.info("Request Received For Success & Error Count");
        return "SUCCESS COUNT: - " + CommonUtils.getSuccessCount() + System.lineSeparator() + "ERROR COUNT: - " + CommonUtils.getErrorCount();
    }

    @GetMapping("/sampleFailTest")
    public String sampleFailTest() throws InterruptedException {
        log.info("Request Received For Sample Fail Test");
        // Skipped '}' to Generate Test Case
        rabbitTemplate.convertAndSend(routingKey, "{\"id\": 1, \"name\": \"Harry Potter\"");
        // Delay to Process The Data through Listener
        Thread.sleep(1000);
        return "SUCCESS COUNT: - " + CommonUtils.getSuccessCount() + System.lineSeparator() + "ERROR COUNT: - " + CommonUtils.getErrorCount();
    }
}
