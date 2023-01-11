package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.PortfolioLinkCache;
import org.bot.commands.base.Command;
import org.bot.commands.base.CommandHandler;
import org.bot.commands.base.CommandProcessor;
import org.bot.models.PortfolioLink;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.DeleteAction;
import org.bot.repositories.PortfolioLinkRepository;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class DeletePortfolioLinkCommand extends Notifier<PortfolioLink> implements Command {

    public DeletePortfolioLinkCommand() {
        super();
        registerObserver(PortfolioLinkCache.getInstance());
        registerObserver(PortfolioLinkRepository.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String name = parts[1];
        UpdateRequest<PortfolioLink> updateRequest = new UpdateRequest<>();
        updateRequest.setCol("name");
        updateRequest.setVal("/" + name);
        notifyObservers(new DeleteAction<>(updateRequest));
        CommandProcessor.getInstance().unregisterCommand(updateRequest.getVal());
        CommandHandler.getInstance().commands().get("/help").execute(update);
    }

    @Override
    public String getName() {
        return "/deleteportfolio";
    }

    @Override
    public String getDescription() {
        return "<name> to delete a portfolio";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitDeletePortfolioLinkCommand();
    }
}
