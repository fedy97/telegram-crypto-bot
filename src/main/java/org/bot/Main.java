package org.bot;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.MongoConfig;
import org.bot.utils.exceptions.GlobalExceptionHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class Main {
    public static void main(String[] args) throws TelegramApiException {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        MongoConfig.addShutdownHook();
        // Create the Telegram Bots API
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        // Register the bot
        botsApi.registerBot(MyBot.getInstance());
        log.info("Starting bot...");

        /* ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
           TaskHandler invoker = new TaskHandler(executor);

           Task<CoinNotify> coinNotifyTask = new NotifyPercentageChangeTask(10 * 60);
           invoker.invoke(coinNotifyTask); */

    }
}
