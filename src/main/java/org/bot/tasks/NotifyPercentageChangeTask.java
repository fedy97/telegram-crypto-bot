package org.bot.tasks;

import lombok.extern.slf4j.Slf4j;
import org.bot.cache.CacheFlyWeight;
import org.bot.models.Coin;
import org.bot.models.CoinNotify;
import org.bot.models.Portfolio;
import org.bot.models.PortfolioLink;
import org.bot.observer.UpdateRequest;
import org.bot.observer.actions.DeleteAction;
import org.bot.repositories.CoinNotifyRepository;
import org.bot.repositories.PortfolioLinkRepository;
import org.bot.tasks.base.Task;
import org.bot.providers.CoingeckoProvider;
import org.bot.utils.formatters.ToBoldDecorator;

import java.util.List;

@Slf4j
public class NotifyPercentageChangeTask extends Task<CoinNotify> {

    public NotifyPercentageChangeTask(Integer seconds) {
        super(seconds);
        this.observers.add(CoinNotifyRepository.getInstance());
        this.observers.add(CacheFlyWeight.getInstance(CoinNotify.class, CoinNotifyRepository.getInstance()));
    }

    @Override
    public void run() {
        List<CoinNotify> coinsToCheck = CacheFlyWeight.getInstance(CoinNotify.class, CoinNotifyRepository.getInstance()).findAll();
        if (coinsToCheck.isEmpty())
            return;
        List<PortfolioLink> portfolios = CacheFlyWeight.getInstance(PortfolioLink.class, PortfolioLinkRepository.getInstance()).findAll();
        Portfolio portfolio;
        try {
            portfolio = CoingeckoProvider.getInstance().getPortfolio(portfolios.get(0).getLink());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            int i = 0;
            while (i < coinsToCheck.size()) {
                CoinNotify coinNotify = coinsToCheck.get(i);
                String ticker = coinNotify.getTicker().toUpperCase();
                Integer thresholdPerc = coinNotify.getPercentageChange();
                Coin coin = portfolio.getCoins().get(ticker);
                double currChange = Double.parseDouble(coin.getChange24().replace("%", ""));
                if (currChange > thresholdPerc) {
                    // notify listeners
                    UpdateRequest<CoinNotify> updateRequest = new UpdateRequest<>();
                    updateRequest.setCol("_id");
                    updateRequest.setVal(coinNotify.getId());
                    notifyObservers(new DeleteAction<>(updateRequest));
                    sendText(coinNotify.getChatId(), new ToBoldDecorator(ticker) + " is more than " + thresholdPerc + "% up!");
                    i--;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
