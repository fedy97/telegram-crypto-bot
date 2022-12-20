package org.bot.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private static CommandHandler instance;
    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    private CommandHandler(){
        // empty
    }

    // Map of available commands
    private final Map<String, Command> commands = new HashMap<>();

    public void register(Command handler) {
        commands.put(handler.getName(), handler);
    }

    public void handle(String command, Update update) throws TelegramApiException {
        if (commands.containsKey(command)) {
            // Execute the command
            Command handler = commands.get(command);
            handler.execute(update);
        } else {
            // Unknown command
            NotFoundCommand.getInstance().execute(update);
        }
    }

    public Map<String, Command> commands() {
        return commands;
    }
}