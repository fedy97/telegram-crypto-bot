package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.operations.Operations;
import org.bot.operations.OperationsDispatcher;
import org.bot.utils.exceptions.PlatformNotAvailableException;
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
        // /withdraw <platform> <ticker> <amount> <chain> <address>
        String[] parts = update.getMessage().getText().split(" ");
        String platform = parts[1];
        try {
            Operations operations = OperationsDispatcher.getInstance().getOperations(platform);
            operations.withdraw();
        } catch (PlatformNotAvailableException e) {
            log.warn(e.getMessage());
            sendText(update.getMessage().getChatId(), "Invalid platform " + platform + ". Available platforms are: " + OperationsDispatcher.getInstance().getAvailablePlatforms());
        }
        sendText(update.getMessage().getChatId(), "done");
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
