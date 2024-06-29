package com.dev.queue.utils;

import org.springframework.stereotype.Component;

import static com.dev.queue.QueueApplication.errorCount;
import static com.dev.queue.QueueApplication.successCount;

@Component
public class CommonUtils {
    public static synchronized void logSuccess() {
        successCount++;
    }

    public static synchronized void logError() {
        errorCount++;
    }

    public static synchronized int getSuccessCount() {
        return successCount;
    }

    public static synchronized int getErrorCount() {
        return errorCount;
    }
}
