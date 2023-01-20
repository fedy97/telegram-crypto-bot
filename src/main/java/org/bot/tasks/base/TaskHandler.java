package org.bot.tasks.base;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TaskHandler {

    private final ScheduledExecutorService executor;

    public TaskHandler(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public void invoke(Task<?> task) {
        executor.scheduleAtFixedRate(task, 0, task.getSeconds(), TimeUnit.SECONDS);
        log.info("Start executing " + task.getClass().getSimpleName());
    }
}