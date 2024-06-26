package org.bot.visitor;

import lombok.extern.slf4j.Slf4j;
import org.bot.operations.OperationsDispatcher;
import org.bot.utils.exceptions.InvalidCommandException;
import org.bot.utils.exceptions.PlatformNotAvailableException;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class ValidatingCommandVisitor implements CommandVisitor {
    private final Update update;

    public ValidatingCommandVisitor(Update update) {
        this.update = update;
    }

    @Override
    public void visitSavePortofolioLinkCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3)
            throw new InvalidCommandException();
        String link = parts[2];
        if (!link.contains("coinmarketcap.com/watchlist"))
            throw new InvalidCommandException("Portfolio link not valid");
    }

    @Override
    public void visitSaveCoinCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3)
            throw new InvalidCommandException();
        try {
            Double.parseDouble(parts[2].replace(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    @Override
    public void visitDeleteCoinCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 2)
            throw new InvalidCommandException();
    }

    @Override
    public void visitDeletePortfolioLinkCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 2)
            throw new InvalidCommandException();
    }

    @Override
    public void visitSaveCoinNotifyCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3)
            throw new InvalidCommandException();
        try {
            Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }

    @Override
    public void visitWithdrawCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 6)
            throw new InvalidCommandException();
        try {
            Double.parseDouble(parts[3].replace(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(e.getMessage());
        }
        String platform = parts[1];
        try {
            OperationsDispatcher.getInstance().getOperations(platform);
        } catch (PlatformNotAvailableException e) {
            throw new InvalidCommandException("Available platforms are: " + OperationsDispatcher.getInstance().getAvailablePlatforms());
        }
    }

    @Override
    public void visitAvailableChainsCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3)
            throw new InvalidCommandException();
        String platform = parts[1];
        try {
            OperationsDispatcher.getInstance().getOperations(platform);
        } catch (PlatformNotAvailableException e) {
            throw new InvalidCommandException("Available platforms are: " + OperationsDispatcher.getInstance().getAvailablePlatforms());
        }
    }

    @Override
    public void visitDepositCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3 && parts.length != 4)
            throw new InvalidCommandException();
        String platform = parts[1];
        try {
            OperationsDispatcher.getInstance().getOperations(platform);
        } catch (PlatformNotAvailableException e) {
            throw new InvalidCommandException("Available platforms are: " + OperationsDispatcher.getInstance().getAvailablePlatforms());
        }
    }

    @Override
    public void visitBalanceCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 2)
            throw new InvalidCommandException();
        String platform = parts[1];
        try {
            OperationsDispatcher.getInstance().getOperations(platform);
        } catch (PlatformNotAvailableException e) {
            throw new InvalidCommandException("Available platforms are: " + OperationsDispatcher.getInstance().getAvailablePlatforms());
        }
    }

    @Override
    public void visitTradeCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 6 && parts.length != 7)
            throw new InvalidCommandException();
        String platform = parts[1];
        try {
            OperationsDispatcher.getInstance().getOperations(platform);
        } catch (PlatformNotAvailableException e) {
            throw new InvalidCommandException("Available platforms are: " + OperationsDispatcher.getInstance().getAvailablePlatforms());
        }
        String action = parts[2];
        if (!action.equalsIgnoreCase("buy") && !action.equalsIgnoreCase("sell"))
            throw new InvalidCommandException("Action available are `sell` or `buy`");
        String type = parts[4];
        if (!type.equalsIgnoreCase("market") && !type.equalsIgnoreCase("limit"))
            throw new InvalidCommandException("Types available are `market` or `limit`");
        try {
            Double.parseDouble(parts[5].replace(",", "."));
            if (parts.length == 7) Double.parseDouble(parts[6].replace(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException(e.getMessage());
        }
    }
}
