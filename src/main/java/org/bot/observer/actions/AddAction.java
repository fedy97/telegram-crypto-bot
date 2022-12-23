package org.bot.observer.actions;

import org.bot.commands.base.CommandProcessor;
import org.bot.observer.UpdateRequest;
import org.bot.utils.Data;

public class AddAction<T> extends Action<T> {
    public AddAction(UpdateRequest<T> updateRequest) {
        super(updateRequest);
    }

    @Override
    public void updateData(Data<T> subject) {
        subject.save(updateRequest.getEntity());
    }

    @Override
    public void updateCommands(CommandProcessor commandProcessor) {
        commandProcessor.registerPortfolioCommands();
    }
}
