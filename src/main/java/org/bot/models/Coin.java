package org.bot.models;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.bot.utils.Utils;

import javax.rmi.CORBA.Util;

@Slf4j
public class Coin {

    private String coinName;
    private String ticker;
    private double price;
    private String change24;
    private double multiplier;

    private final StringBuilder sb;


    public Coin() {
        this.sb = new StringBuilder();
        this.price = 0.0;
        this.change24 = "0%";
        this.multiplier = 0;
    }

    public static Coin fromRawCoin(String raw) {
        Coin coin = new Coin();
        try {
            coin.setCoinName(raw.split(" ")[0]);
            coin.setTicker(raw.split("\\(")[1].split("\\)")[0]);
            coin.setPrice(Double.parseDouble(raw.split("<td data-sort='")[1].split("'")[0]));
            coin.setChange24(raw.split("data-show-solid-arrow=\"false\">")[2].split("<")[0]);
        } catch (Exception e) {
            // continue
        }
        return coin;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getChange24() {
        return change24;
    }

    public void setChange24(String change24) {
        this.change24 = change24;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
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
        sb.append("\n");
        return sb.toString();
    }
}
