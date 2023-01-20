package org.bot.visitor;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.exceptions.InvalidCommandException;
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
        if (!link.contains("coingecko.com") || !link.contains("portfolios/public/"))
            throw new InvalidCommandException();
    }

    @Override
    public void visitSaveCoinCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3)
            throw new InvalidCommandException();
        try {
            Double.parseDouble(parts[2].replace(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidCommandException();
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
        visitDeleteCoinCommand();
    }

    @Override
    public void visitSaveCoinNotifyCommand() {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3)
            throw new InvalidCommandException();
        try {
            Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException();
        }
    }
}
