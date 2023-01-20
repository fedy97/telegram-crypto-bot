package org.bot.tasks.base;

import lombok.Getter;
import org.bot.observer.Notifier;
import org.bot.utils.TelegramSender;

@Getter
public abstract class Task<T> extends Notifier<T> implements Runnable, TelegramSender {

    private final Integer seconds;

    protected Task(Integer seconds) {
        this.seconds = seconds;
    }
}
