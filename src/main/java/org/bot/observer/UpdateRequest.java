package org.bot.observer;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UpdateRequest<T> {
    @Setter
    private T entity;
    @Setter
    private String col;
    @Setter
    private String val;

}
