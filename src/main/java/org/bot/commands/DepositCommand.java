package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.operations.Operations;
import org.bot.operations.OperationsDispatcher;
import org.bot.utils.formatters.DepositDecorator;
import org.bot.utils.formatters.ToBoldDecorator;
import org.bot.visitor.CommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;


@Slf4j
public class DepositCommand implements Command {

    public DepositCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        String platform = parts[1].toLowerCase().trim();
        String ticker = parts[2].toUpperCase().trim();
        String chain = parts.length == 4 ? parts[3].toUpperCase().trim() : null;

        Operations operations = OperationsDispatcher.getInstance().getOperations(platform);
        Map<String, String> depositAddresses = operations.deposit(ticker, chain);

        sendText(update.getMessage().getChatId(), "Deposit addresses for " + new ToBoldDecorator(ticker) + ":\n" + new DepositDecorator(depositAddresses));
    }

    @Override
    public String getName() {
        return "/deposit";
    }

    @Override
    public String getDescription() {
        return "<platform> <ticker> <chain?> to get a list of deposit addresses. Specify the chain to create the specific address for that chain, if not present in the list";
    }

    @Override
    public void accept(CommandVisitor visitor) {
        visitor.visitDepositCommand();
    }
}
