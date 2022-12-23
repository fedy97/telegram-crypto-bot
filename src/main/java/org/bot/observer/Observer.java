package org.bot.observer;

import org.bot.observer.actions.Action;

public interface Observer<T> {
    void update(Action<T> action);
}
