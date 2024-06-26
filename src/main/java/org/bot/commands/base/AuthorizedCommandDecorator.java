package org.bot.commands.base;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.EnvVars;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class AuthorizedCommandDecorator extends CommandDecorator {
    public AuthorizedCommandDecorator(Command command) {
        super(command);
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        // Perform authorization check here
        if (!isAuthorized(update)) {
            log.info("Not Authorized");
            sendText(update.getMessage().getChatId(), "You are not authorized");
            return;
        }
        // Delegate to the original command's execute method if the authorization check passes
        command.execute(update);
    }

    @Override
    public String getName() {
        return command.getName();
    }

    @Override
    public String getDescription() {
        return command.getDescription();
    }

    private boolean isAuthorized(Update update) {
        String username = update.getMessage().getFrom().getUserName();
        return username != null && username.equals(EnvVars.getEnvVar("TG_ADMIN", null));
    }

}
