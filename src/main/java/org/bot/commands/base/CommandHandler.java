package org.bot.commands.base;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.InvalidCommandException;
import org.bot.visitor.ValidatingCommandVisitor;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandHandler {

    private static CommandHandler instance;
    // Map of available commands
    private final Map<String, Command> commands = new HashMap<>();

    private CommandHandler() {
    }

    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    public void register(Command handler) {
        commands.put(handler.getName(), handler);
    }

    public void unregister(String commandName) {
        commands.remove(commandName);
    }

    public void handle(String command, Update update) throws TelegramApiException {
        log.info(update.getMessage().getFrom().getUserName() + ", " + update.getMessage().getFrom().getFirstName() + ": " + command);
        String firstWordCommand = command.split(" ")[0];
        if (commands.containsKey(firstWordCommand)) {
            Command handler = commands.get(firstWordCommand);
            try {
                // Validate first the command
                handler.accept(new ValidatingCommandVisitor(update));
                // Execute the command
                handler.execute(update);
            } catch (InvalidCommandException | NumberFormatException e) {
                log.warn("Invalid command format");
                handler.sendText(update.getMessage().getChatId(), "Invalid command format. Use " + handler.getName() + " " + handler.getDescription());
            }
        }
    }

    public Map<String, Command> commands() {
        return commands;
    }
}