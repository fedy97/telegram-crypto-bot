package org.bot.commands.base;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class ScheduledCommandDecorator extends CommandDecorator {
    protected ScheduledCommandDecorator(Command command) {
        super(command);
    }

    @Override
    public void execute(Update update) throws TelegramApiException {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    command.execute(update);
                } catch (TelegramApiException e) {
                    log.error(e.getMessage());
                    timer.cancel();
                }
            }
        }, 0, 10 * 1000L); // checks every 10 seconds
    }

    @Override
    public String getName() {
        return command.getName();
    }

    @Override
    public String getDescription() {
        return command.getDescription();
    }
}
