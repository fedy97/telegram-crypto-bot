package org.bot.observer.actions;

import org.bot.observer.UpdateRequest;
import org.bot.utils.Store;

public abstract class Action<T> {

    protected UpdateRequest<T> updateRequest;

    protected Action(UpdateRequest<T> updateRequest) {
        this.updateRequest = updateRequest;
    }

    public abstract void updateData(Store<T> subject);

}
