package org.bot;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.MongoConfig;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            MongoConfig.addShutdownHook();
            // Create the Telegram Bots API
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            // Register the bot
            botsApi.registerBot(MyBot.getInstance());
            log.info("Starting bot...");
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
