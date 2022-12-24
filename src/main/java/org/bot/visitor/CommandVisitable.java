package org.bot.visitor;

public interface CommandVisitable {
    void accept(CommandVisitor visitor);
}
