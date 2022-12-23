package org.bot.observer.actions;

import org.bot.commands.base.CommandProcessor;
import org.bot.observer.UpdateRequest;
import org.bot.utils.Data;

public class DeleteAllAction<T> extends Action<T> {
    public DeleteAllAction(UpdateRequest<T> updateRequest) {
        super(updateRequest);
    }

    @Override
    public void updateData(Data<T> subject) {
        subject.deleteAll();
    }

    @Override
    public void updateCommands(CommandProcessor commandProcessor) {
        // there is no command yet to delete all commands
    }
}
