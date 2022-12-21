package org.bot.commands.base;

public abstract class CommandDecorator implements Command {
    protected Command command;

    protected CommandDecorator(Command command) {
        this.command = command;
    }

}
