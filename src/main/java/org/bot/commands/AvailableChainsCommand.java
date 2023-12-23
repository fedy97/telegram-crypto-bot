package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.operations.Operations;
import org.bot.operations.OperationsDispatcher;
import org.bot.utils.formatters.ChainsDecorator;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;


@Slf4j
public class AvailableChainsCommand implements Command {

    public AvailableChainsCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String platform = parts[1].toLowerCase().trim();
        String ticker = parts[2].toUpperCase().trim();

        Operations operations = OperationsDispatcher.getInstance().getOperations(platform);
        Map<String, Double> chains = operations.getAvailableChains(ticker);

        sendText(update.getMessage().getChatId(), "Available chains for " + ticker + ":\n" + new ChainsDecorator(chains));
    }

    @Override
    public String getName() {
        return "/chains";
    }

    @Override
    public String getDescription() {
        return "<platform> <ticker> to see which chains are available for withdraw/deposit";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitAvailableChainsCommand();
    }
}
