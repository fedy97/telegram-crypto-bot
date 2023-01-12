package org.bot.commands.base;

import lombok.extern.slf4j.Slf4j;
import org.bot.commands.PortfolioCommand;
import org.bot.models.PortfolioLink;
import org.bot.utils.fetchers.PortfolioLinkFetcherFactory;

import java.util.List;

@Slf4j
public class CommandProcessor {

    private static CommandProcessor instance;
    private final CommandHandler commandHandler;

    private CommandProcessor() {
        this.commandHandler = CommandHandler.getInstance();
    }

    public static CommandProcessor getInstance() {
        if (instance == null) {
            instance = new CommandProcessor();
        }
        return instance;
    }

    public void registerPortfolioCommands() {
        // fetch all portfolio links and put them in cache
        List<PortfolioLink> portfolioLinks = PortfolioLinkFetcherFactory.getInstance().createDataFetcher().fetchAll();
        // register all links available in cache
        for (PortfolioLink portfolioLink : portfolioLinks) {
            if (!commandHandler.commands().containsKey(portfolioLink.getName()))
                // here we need to create the customizable command for portfolio,
                // then we delegate the register process to the handler
                this.commandHandler.register(new PortfolioCommand(portfolioLink));
        }
    }

    public void unregisterCommand(String commandName) {
        this.commandHandler.unregister(commandName);
    }

}