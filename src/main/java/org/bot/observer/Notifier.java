package org.bot.observer;

import org.bot.observer.actions.Action;

import java.util.HashSet;
import java.util.Set;

public class Notifier<T> {

    protected final Set<Observer<T>> observers;

    protected Notifier() {
        this.observers = new HashSet<>();
    }

    public void registerObserver(Observer<T> observer) {
        observers.add(observer);
    }

    public void notifyObservers(Action<T> action) {
        for (Observer<T> observer : observers) {
            observer.update(action);
        }
    }



}
