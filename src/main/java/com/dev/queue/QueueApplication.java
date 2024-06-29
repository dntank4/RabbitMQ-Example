package com.dev.queue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QueueApplication {

    public static int successCount;
    public static int errorCount;

    public static void main(String[] args) {
        SpringApplication.run(QueueApplication.class, args);
    }

}
