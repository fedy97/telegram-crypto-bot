package org.bot.observer.actions;

import org.bot.observer.UpdateRequest;
import org.bot.utils.Store;

public class AddAction<T> extends Action<T> {
    public AddAction(UpdateRequest<T> updateRequest) {
        super(updateRequest);
    }

    public void updateData(Store<T> subject) {
        subject.save(updateRequest.getEntity());
    }

}
