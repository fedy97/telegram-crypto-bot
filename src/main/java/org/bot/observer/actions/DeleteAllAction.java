package org.bot.observer.actions;

import org.bot.observer.UpdateRequest;
import org.bot.utils.Data;

public class DeleteAllAction<T> extends Action<T> {
    public DeleteAllAction(UpdateRequest<T> updateRequest) {
        super(updateRequest);
    }

    @Override
    public void doAction(Data<T> subject) {
        subject.deleteAll();
    }
}
