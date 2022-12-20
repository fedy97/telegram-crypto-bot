package org.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class HelpCommand extends Command {

    public HelpCommand() {
        super();
        setName("/help");
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        long chatId = update.getMessage().getChatId();
        String commands = buildCommands();
        sendText(chatId, commands);
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
