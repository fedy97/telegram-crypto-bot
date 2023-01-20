package org.bot.observer.actions;

import org.bot.observer.UpdateRequest;
import org.bot.utils.Store;

public class DeleteAction<T> extends Action<T>{
    public DeleteAction(UpdateRequest<T> updateRequest) {
        super(updateRequest);
    }

    @Override
    public void updateData(Store<T> subject) {
        subject.deleteByValue(updateRequest.getCol(), updateRequest.getVal());
    }

}
