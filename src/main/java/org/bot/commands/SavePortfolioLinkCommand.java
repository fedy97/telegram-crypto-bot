package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.PortfolioLinkCache;
import org.bot.commands.base.Command;
import org.bot.commands.base.CommandHandler;
import org.bot.commands.base.CommandProcessor;
import org.bot.models.PortfolioLink;
import org.bot.observer.Notifier;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.AddAction;
import org.bot.repositories.PortfolioLinkRepository;
import org.bot.utils.Validator;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SavePortfolioLinkCommand extends Notifier<PortfolioLink> implements Command {

    public SavePortfolioLinkCommand() {
        super();
        registerObserver(PortfolioLinkCache.getInstance());
        registerObserver(PortfolioLinkRepository.getInstance());
        registerObserver(CommandProcessor.getInstance());
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3) {
            log.warn("Invalid command format");
            sendText(update.getMessage().getChatId(), "Invalid command format. Use " + getName() + getDescription());
            return;
        }
        String name = "/" + parts[1].toLowerCase().replace("/", "").replace(" ", "");
        String link = parts[2];
        PortfolioLink portfolioLink = new PortfolioLink();
        portfolioLink.setLink(link);
        portfolioLink.setName(name);
        UpdateRequest<PortfolioLink> updateRequest = new UpdateRequest<>();
        updateRequest.setEntity(portfolioLink);
        if (!Validator.validCoingeckoLink(portfolioLink.getLink())) {
            log.warn("Invalid link");
            sendText(update.getMessage().getChatId(), "Invalid link");
            return;
        }
        notifyObservers(new AddAction<>(updateRequest));
        CommandHandler.getInstance().commands().get("/help").execute(update);
    }

    @Override
    public String getName() {
        return "/saveportfolio";
    }

    @Override
    public String getDescription() {
        return " <name> <link> to save a new portfolio link";
    }

}
