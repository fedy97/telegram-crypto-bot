# Telegram Crypto Bot

[![Build and Deploy](https://github.com/fedy97/telegram-crypto-bot/actions/workflows/deploy.yml/badge.svg)](https://github.com/fedy97/telegram-crypto-bot/actions/workflows/deploy.yml)

### Features:

- [x] Built with [TelegramBots](https://github.com/rubenlagus/TelegramBots) in `Java`
- [x] Track Coingecko Portfolio (create it [here](https://www.coingecko.com/en/portfolio), copy the public address and save it in the bot with `/saveportfolio`, or delete it with `/deleteportfolio`)
- [x] MongoDB Integration (save your coins buy price with `/save`,`/delete`,`/deleteall` and see their multipliers after have saved a portfolio)
- [x] Coingecko Trending coins with `/trend` command
- [x] Caching System
- [x] Telegram Authorization
- [x] Maven Build
- [x] Dockerized image
- [x] Deployable on VPS
- [x] Full CI/CD Flow with Actions
- [x] Withdraw funds/check chains/check balance/trade/deposit with Kucoin


### Design Patterns:

- [x] Command (to handle the Telegram commands)
- [x] Singleton (to reuse objects and spare memory and for the caching system)
- [x] Decorator (authorization check for some commands)
- [x] Proxy (caching system)
- [x] Flyweight (caching system)
- [x] Facade (to hide Coingecko operations to build the portfolio)
- [x] Factory (to create the right strategy)
- [x] Strategy (to change behaviour based on add/delete/deleteall actions)
- [x] Observer (to notify observers of a added/removed portfolio)
- [x] Visitor (to validate commands)


### How to Use it:

To run the bot, you need to clone this prokect and define the following Environmental Variables:

- `TELEGRAM_BOT_NAME` = Ask BotFather in Telegram
- `TELEGRAM_BOT_TOKEN` = Ask BotFather in Telegram
- `MONGO_DB_URI` = Optional, To connect to MongoDB. Some commands requires this to be set, list is below
- `TG_ADMIN` = Optional, Telegram username of the owner, you have to set yours otherwise some commands will not be authorized. Check below for a full list
- `COINS_COLLECTION` = Optional, name of the collection of your saved coins, default `coins`
- `PORTFOLIO_LINKS_COLLECTION` = Optional, name of the collection of your coingecko portfolios, default `portfolio_links`
- `KUCOIN_API_KEY` = Optional, set it if you want to use Kucoin functions. [Create Kucoin Keys](https://www.kucoin.com/support/360015102174)
- `KUCOIN_SECRET_KEY` = Optional, set it if you want to use Kucoin functions
- `KUCOIN_PASSPHRASE` = Optional, set it if you want to use Kucoin functions

You can start the bot with the command `docker-compose up --build -d`

Then go to your instance of the Telegram bot and type `/start`.

#### Create a Coingecko portfolio
Go [here](https://www.coingecko.com/en/portfolio) and create a public portfolio, like in the image below:

<img width="1396" alt="image" src="https://github.com/fedy97/telegram-crypto-bot/assets/47827254/6fe64a47-bb2e-4476-b59f-48f1a269e42c">

### Commands
#### Public Commands
Here is the list of commands that do not require authorization, so every user can run these commands:

- `/start`: welcome message
- `/help`: lists of all available commands
- `/trend`: shows Coingecko trending coins
- `/<name of the saved portfolio>`: shows the list of the coins saved in the coingecko portfolio
- `/chains`: it checks which chains are available for deposit/withdraw for a specific coin, for a specific platform
- `/deposit`: it gives the deposit addresses, based on the platform specified and ticker

#### Authorized Commands
Here is the list of commands that require authorization, defined by setting the `TG_ADMIN` environmental variable:

- `/save`: it saves a coin buy price, for example `/save BTC 15000`. Prices are in $ so that they can be compared with the coingecko current prices, when you run the `/<portfolio name>` command. (requires `MONGO_DB_URI`)
- `/delete`: it deletes a coin buy price (requires `MONGO_DB_URI`)
- `/saveportfolio`: it saves a coingecko portfolio, then you can fetch it by executing `/<portfolio name>` (requires `MONGO_DB_URI`)
- `/deleteportfolio`: it deletes a portfolio (requires `MONGO_DB_URI`)
- `/prices`: it lists all saved coins' buy prices (requires `MONGO_DB_URI`)
- `/withdraw`: it withdraw funds from different platforms. Right now only Kucoin is available
- `/balance`: it checks balance for the specified exchange (platform)
- `/trade`: it performs a buy/sell action, by specifying the type (limit/market), ticker, amount and optionally the limit price

#### Example Usage

First, you add your coingecko portfolio to the bot, like this:

<img width="348" alt="image" src="https://github.com/fedy97/telegram-crypto-bot/assets/47827254/70ab8df3-d586-4fce-b6b3-06f68fae1ef6">

After that, you can call the portfolio command to show coins present in the coingecko portfolio, with some additional information:

<img width="958" alt="image" src="https://github.com/fedy97/telegram-crypto-bot/assets/47827254/87051b40-1e56-4f5c-b7ee-fa3a8ace17bf">


