package org.bot.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.bot.utils.Utils;

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
    private String change24;
    private String multiplier;

    private final StringBuilder sb;


    public Coin() {
        this.sb = new StringBuilder();
        this.price = 0.0;
        this.change24 = "0%";
        this.multiplier = "";
    }

    @Override
    public String toString() {
        if (sb.length() > 0)
            sb.setLength(0);
        sb.append(coinName);
        sb.append(" (");
        Utils.wrapStringWith(sb, ticker, "*");
        sb.append("): ");
        Utils.wrapStringWith(sb, "$", "`");
        Utils.wrapStringWith(sb, Utils.roundFloat(price, 4).toString(), "`");
        sb.append(" (");
        Utils.wrapStringWith(sb, change24, "_");
        sb.append(") ");
        sb.append(Utils.findEmoji(Double.parseDouble(change24.substring(0, change24.length() - 1))));
        sb.append(" ").append(multiplier);
        sb.append("\n");
        return sb.toString();
    }

    public String toShortString() {
        if (sb.length() > 0)
            sb.setLength(0);
        Utils.wrapStringWith(sb, ticker, "*");
        sb.append(": ");
        Utils.wrapStringWith(sb, Utils.roundFloat(price, 5).toString(), "`");
        sb.append("\n");
        return sb.toString();
    }
}
