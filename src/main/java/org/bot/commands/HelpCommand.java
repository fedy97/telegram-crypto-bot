package org.bot.commands;

import org.bot.commands.base.Command;
import org.bot.commands.base.CommandHandler;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class HelpCommand implements Command {

    public HelpCommand() {
        super();
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String commands = buildCommands();
        sendText(chatId, commands);
    }

    @Override
    public String getName() {
        return "/help";
    }

    @Override
    public String getDescription() {
        return "list of all commands";
    }

    private String buildCommands() {
        String response = "Here is a list of all the available commands:\n";
        StringBuilder sb = new StringBuilder(response);
        for (Command cmd : CommandHandler.getInstance().commands().values()) {
            if (cmd.getName().equals(getName()))
                continue;
            sb.append(cmd.getName());
            sb.append(" --> ");
            sb.append(cmd.getDescription());
            sb.append("\n");
        }
        return sb.toString();
    }

}
