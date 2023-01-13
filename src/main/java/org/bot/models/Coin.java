package org.bot.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bot.utils.EmojiFactory;
import org.bot.utils.NumberOperations;
import org.bot.utils.formatters.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Slf4j
@Getter
@Setter
@Entity(name = "coins")
public class Coin {

    @Id
    private String id;
    private String coinName;
    private String ticker;
    private double price;
    private String link;
    private String change24;
    private String multiplier;

    private final StringBuilder sb;


    public Coin() {
        this.sb = new StringBuilder();
        this.price = 0.0;
        this.change24 = "0%";
        this.multiplier = "";
        this.link = "";
    }

    @Override
    public String toString() {
        if (sb.length() > 0)
            sb.setLength(0);
        sb.append(new ToLinkDecorator(coinName, link));
        sb.append(" (");
        sb.append(new ToBoldDecorator(ticker));
        sb.append("): ");
        sb.append(new ToCodeDecorator("$"));
        sb.append(new ToCodeDecorator(NumberOperations.roundFloat(price, 4).toString()));
        sb.append(" (");
        sb.append(new ToItalicDecorator(change24));
        sb.append(") ");
        sb.append(EmojiFactory.findEmoji(Double.parseDouble(change24.substring(0, change24.length() - 1))));
        sb.append(" ").append(multiplier);
        sb.append("\n");
        return sb.toString();
    }

    public String toShortString() {
        if (sb.length() > 0)
            sb.setLength(0);
        sb.append(new ToBoldDecorator(ticker));
        sb.append(": ");
        sb.append(new ToCodeDecorator(NumberOperations.roundFloat(price, 5).toString()));
        sb.append("\n");
        return sb.toString();
    }
}
