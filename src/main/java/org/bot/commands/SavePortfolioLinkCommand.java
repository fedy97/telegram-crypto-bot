package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.PortfolioLinkCacheProxy;
import org.bot.commands.base.Command;
import org.bot.commands.base.CommandHandler;
import org.bot.commands.base.CommandProcessor;
import org.bot.models.PortfolioLink;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.AddAction;
import org.bot.repositories.PortfolioLinkRepository;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SavePortfolioLinkCommand extends Notifier<PortfolioLink> implements Command {

    public SavePortfolioLinkCommand() {
        super();
        registerObserver(PortfolioLinkCacheProxy.getInstance());
        registerObserver(PortfolioLinkRepository.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String name = "/" + parts[1].toLowerCase().replace("/", "").replace(" ", "");
        String link = parts[2];
        PortfolioLink portfolioLink = new PortfolioLink();
        portfolioLink.setLink(link);
        portfolioLink.setName(name);
        UpdateRequest<PortfolioLink> updateRequest = new UpdateRequest<>();
        updateRequest.setEntity(portfolioLink);
        notifyObservers(new AddAction<>(updateRequest));
        CommandProcessor.getInstance().registerPortfolioCommands();
        CommandHandler.getInstance().commands().get("/help").execute(update);
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitSavePortofolioLinkCommand();
    }

    @Override
    public String getName() {
        return "/saveportfolio";
    }

    @Override
    public String getDescription() {
        return "<name> <link> to save a new portfolio link";
    }

}
