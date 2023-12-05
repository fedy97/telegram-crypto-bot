package org.bot.commands.base;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.exceptions.CommandExecutionException;
import org.bot.utils.exceptions.InvalidCommandException;
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

    public void register(Command command) {
        if (command.isUsable()) commands.put(command.getName(), command);
    }

    public void unregister(String commandName) {
        commands.remove(commandName);
    }

    public void handle(String input, Update update) throws TelegramApiException {
        String firstWordCommand = input.split(" ")[0];
        if (commands.containsKey(firstWordCommand)) {
            log.info(update.getMessage().getFrom().getUserName() + ", " + update.getMessage().getFrom().getFirstName() + ": " + input);
            Command command = commands.get(firstWordCommand);
            try {
                // Validate first the command
                command.accept(new ValidatingCommandVisitor(update));
                // Execute the command
                command.execute(update);
            } catch (InvalidCommandException e) {
                log.warn(e.getMessage());
                command.sendText(update.getMessage().getChatId(), e.getMessage() + ". Use " + command.getName() + " " + command.getDescription());
            } catch (CommandExecutionException e) {
                log.warn(e.getMessage());
                command.sendText(update.getMessage().getChatId(), e.getMessage());
            }
        }
    }

    public Map<String, Command> commands() {
        return commands;
    }
}
