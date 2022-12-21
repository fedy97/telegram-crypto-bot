package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.repositories.MongoCoinRepository;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class SaveCoinCommand implements Command {

    public SaveCoinCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3) {
            sendText(update.getMessage().getChatId(), "Invalid command format. Use /save <ticker> <price>");
            return;
        }
        String ticker = parts[1];
        double price;
        try {
            price = Double.parseDouble(parts[2]);
        } catch (NumberFormatException e) {
            sendText(update.getMessage().getChatId(), "Invalid price: " + parts[2]);
            return;
        }
        Coin coin = new Coin();
        coin.setTicker(ticker);
        coin.setPrice(price);
        MongoCoinRepository.getInstance().save(coin);
        sendText(update.getMessage().getChatId(), "Coin saved");
    }

    @Override
    public String getName() {
        return "/save";
    }

    @Override
    public String getDescription() {
        return "to save a new coin with its buy price";
    }

}
