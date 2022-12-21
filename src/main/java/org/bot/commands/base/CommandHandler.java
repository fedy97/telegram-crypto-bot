package org.bot.commands.base;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.NotFoundCommand;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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
        log.info(update.getMessage().getFrom().getUserName() + ", " + update.getMessage().getFrom().getFirstName() + ": " + command);
        String firstWordCommand = command.split(" ")[0];
        if (commands.containsKey(firstWordCommand)) {
            // Execute the command
            Command handler = commands.get(firstWordCommand);
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