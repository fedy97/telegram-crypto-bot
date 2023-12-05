package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.operations.Operations;
import org.bot.operations.OperationsDispatcher;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Slf4j
public class WithdrawCommand implements Command {

    public WithdrawCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String platform = parts[1].toLowerCase().trim();
        String ticker = parts[2].toUpperCase().trim();
        Double amount = Double.parseDouble(parts[3].replace(",", ".").trim());
        String chain = parts[4].trim();
        String address = parts[5].trim();

        Operations operations = OperationsDispatcher.getInstance().getOperations(platform);
        operations.withdraw(ticker, amount, chain, address);

        sendText(update.getMessage().getChatId(), "Withdraw started");
    }

    @Override
    public String getName() {
        return "/withdraw";
    }

    @Override
    public String getDescription() {
        return "<platform> <ticker> <amount> <chain> <address> to withdraw your funds";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitWithdrawCommand();
    }
}
