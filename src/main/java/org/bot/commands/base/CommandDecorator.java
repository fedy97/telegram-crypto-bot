package org.bot.commands.base;

import org.bot.visitor.CommandVisitor;

public abstract class CommandDecorator implements Command {
    protected Command command;

    protected CommandDecorator(Command command) {
        this.command = command;
    }

    @Override
    public void accept(CommandVisitor visitor) {
        command.accept(visitor);
    }

    @Override
    public boolean isUsable() {
        return command.isUsable();
    }
}
