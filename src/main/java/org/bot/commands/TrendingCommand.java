package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.models.Trending;
import org.bot.utils.CoingeckoFacade;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
        return "show trending coins on Coingecko";
    }

    private String buildTrendingResponse() {
        CoingeckoFacade coingeckoFacade = CoingeckoFacade.getInstance();
        Trending trending = coingeckoFacade.getTrendingCoins();
        return trending.toString();
    }

    @Override
    public void accept(CommandVisitor visitor) {
        // no need to be visited
    }
}
