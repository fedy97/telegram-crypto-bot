package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.models.Trending;
import org.bot.providers.CoingeckoProvider;
import org.bot.providers.DataProvider;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

@Slf4j
public class TrendingCommand implements Command {

    public TrendingCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String response = buildTrendingResponse();
        sendText(chatId, response);
    }

    @Override
    public String getName() {
        return "/trend";
    }

    @Override
    public String getDescription() {
        return "show trending coins";
    }

    private String buildTrendingResponse() {
        try {
            DataProvider provider = CoingeckoProvider.getInstance();
            Trending trending = provider.getTrendingCoins();
            return trending.toString();
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            return e.getMessage();
        }
    }
}
