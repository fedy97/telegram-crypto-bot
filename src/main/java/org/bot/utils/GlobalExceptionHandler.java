package org.bot.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // log the exception
        log.error("Uncaught exception occurred", throwable);
    }
}
