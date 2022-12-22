package org.bot.commands;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.base.Command;
import org.bot.models.Coin;
import org.bot.repositories.MongoCoinRepository;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class SaveCoinCommand implements Command {

    public SaveCoinCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        String[] parts = update.getMessage().getText().split(" ");
        if (parts.length != 3) {
            log.error("Invalid command format");
            sendText(update.getMessage().getChatId(), "Invalid command format. Use /save <ticker> <buy price>");
            return;
        }
        String ticker = parts[1].toUpperCase();
        double price;
        try {
            price = Double.parseDouble(parts[2].replace(",", "."));
        } catch (NumberFormatException e) {
            log.error("Invalid Price: " + parts[2]);
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
