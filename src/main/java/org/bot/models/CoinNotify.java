package org.bot.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Entity(name = "coin_notifiers")
@Getter
@Setter
public class CoinNotify extends Coin {
    private Integer percentageChange;
    private Long chatId;

    public CoinNotify() {
        super();
    }

    @Override
    public String toString() {
        return "CoinNotify{" +
                "id=" + getId() +
                ", percentageChange=" + percentageChange +
                ", chatId=" + chatId +
                '}';
    }
}
