package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.operations.Operations;
import org.bot.operations.OperationsDispatcher;
import org.bot.utils.formatters.BalanceDecorator;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;


@Slf4j
public class BalanceCommand implements Command {

    public BalanceCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String platform = parts[1].toLowerCase().trim();

        Operations operations = OperationsDispatcher.getInstance().getOperations(platform);
        Map<String, Double> balance = operations.getBalance();

        sendText(update.getMessage().getChatId(), new BalanceDecorator(balance.toString()).toString());
    }

    @Override
    public String getName() {
        return "/balance";
    }

    @Override
    public String getDescription() {
        return "<platform> to see the balance of your account";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitBalanceCommand();
    }
}
