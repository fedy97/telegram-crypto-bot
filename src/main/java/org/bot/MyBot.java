package org.bot;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.*;
import org.bot.commands.base.AuthorizedCommandDecorator;
import org.bot.commands.base.CommandHandler;
import org.bot.utils.Utils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class MyBot extends TelegramLongPollingBot {

    private static MyBot instance;
    private final CommandHandler commandHandler;

    private MyBot() {
        // Private constructor to prevent instantiation
        commandHandler = CommandHandler.getInstance();
        commandHandler.register(new StartCommand());
        commandHandler.register(new HelpCommand());
        commandHandler.register(new ScamCommand());
        commandHandler.register(new PricesCommand());
        // from here on you need to be admin
        commandHandler.register(new AuthorizedCommandDecorator(new PortfolioCommand()));
        commandHandler.register(new AuthorizedCommandDecorator(new SaveCoinCommand()));
        commandHandler.register(new AuthorizedCommandDecorator(new DeleteCoinCommand()));
        commandHandler.register(new AuthorizedCommandDecorator(new DeleteAllCoinsCommand()));
    }

    public static MyBot getInstance() {
        if (instance == null) {
            instance = new MyBot();
        }
        return instance;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            try {
                commandHandler.handle(messageText, update);
            } catch (TelegramApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Return the bot's username
        return Utils.getEnvVar("TELEGRAM_BOT_NAME");
    }

    @Override
    public String getBotToken() {
        // Return the bot's token
        return Utils.getEnvVar("TELEGRAM_BOT_TOKEN");
    }

}