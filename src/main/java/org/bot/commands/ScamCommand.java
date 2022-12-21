package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.models.Portfolio;
import org.bot.utils.CoingeckoFacade;
import org.bot.utils.Utils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;


public class ScamCommand implements Command {

    public ScamCommand() {
        super();
    }

    @Override
    public void execute(Update update) {
        long chatId = update.getMessage().getChatId();
        try {
            String response = buildScamSalesResponse();
            sendText(chatId, response);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "/scam";
    }

    @Override
    public String getDescription() {
        return "show scam sales portfolio";
    }

    private String buildScamSalesResponse() throws IOException {
        CoingeckoFacade coingeckoFacade = CoingeckoFacade.getInstance();
        Portfolio portfolio = coingeckoFacade.getCoingeckoPortfolio(Utils.getEnvVar("CG_URL_SHARED"));
        return portfolio.toString();
    }

}
