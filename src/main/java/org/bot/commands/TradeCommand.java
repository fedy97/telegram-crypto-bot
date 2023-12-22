package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.operations.Operations;
import org.bot.operations.OperationsDispatcher;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
public class TradeCommand implements Command {

    public TradeCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String platform = parts[1].toLowerCase().trim();
        String action = parts[2].toUpperCase().trim();
        String ticker = parts[3].toUpperCase().trim();
        String type = parts[4].toUpperCase().trim();
        Double amount = Double.parseDouble(parts[5].replace(",", ".").trim());
        Double price = parts.length == 7 ? Double.parseDouble(parts[6].replace(",", ".").trim()) : null;

        Operations operations = OperationsDispatcher.getInstance().getOperations(platform);
        operations.trade(action, ticker, type, amount, price);

        sendText(update.getMessage().getChatId(), "Trade placed");
    }

    @Override
    public String getName() {
        return "/trade";
    }

    @Override
    public String getDescription() {
        return "<platform> <action: buy|sell> <ticker> <type: market|limit> <amount> <price?> to trade";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitTradeCommand();
    }
}
