package org.bot.observer.actions;

import org.bot.commands.base.CommandProcessor;
import org.bot.observer.UpdateRequest;
import org.bot.utils.Data;

public abstract class Action<T> {

    protected UpdateRequest<T> updateRequest;

    protected Action(UpdateRequest<T> updateRequest) {
        this.updateRequest = updateRequest;
    }

    public abstract void updateData(Data<T> subject);

    public abstract void updateCommands(CommandProcessor commandProcessor);
}
