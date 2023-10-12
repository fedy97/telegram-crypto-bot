package org.bot.visitor;

public interface CommandVisitable {
    default void accept(CommandVisitor visitor) {
        // default to no action
    }
}
