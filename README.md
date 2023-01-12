# Telegram Crypto Bot

[![Build and Deploy](https://github.com/fedy97/telegram-crypto-bot/actions/workflows/deploy.yml/badge.svg)](https://github.com/fedy97/telegram-crypto-bot/actions/workflows/deploy.yml)

### Features:

- [x] Built with [TelegramBots](https://github.com/rubenlagus/TelegramBots) in `Java`
- [x] Track Coingecko Portfolio (see all your coins' current prices with `/portfolio`)
- [x] MongoDB Integration (save your coins buy price with `/save`,`/delete`,`/deleteall`)
- [x] Caching System
- [x] Telegram Authorization
- [x] Maven Build
- [x] Dockerized image
- [x] DockerHub upload
- [x] Deployed on Oracle VPS
- [x] Full CI/CD Flow with Actions


### Design Patterns:

- [x] Command (to handle the Telegram commands)
- [x] Singleton (to reuse objects and spare memory and for the caching system)
- [x] Decorator (authorization check for some commands)
- [x] Proxy (caching system)
- [x] Facade (to hide Coingecko operations to build the portfolio)
- [x] Factory (to create the right strategy)
- [x] Strategy (to change behaviour based on 1. add/delete/deleteall actions 2. fetch from db/cache)
- [x] Observer (to notify observers of a added/removed portfolio)
- [x] Visitor (to validate commands)


#### How to Use it:

To run the bot, you need to define the following Environmental Variables:

- `TELEGRAM_BOT_NAME` = Ask BotFather in Telegram
- `TELEGRAM_BOT_TOKEN` = Ask BotFather in Telegram
- `MONGO_DB_URI` = To connect to MongoDB
- `COINS_COLLECTION` = Optional, name of the collection of your saved coins, default `coins`
- `PORTFOLIO_LINKS_COLLECTION` = Optional, name of the collection of your coingecko portfolios, default `portfolio_links`
- `TG_ADMIN` = Telegram username of the owner, you can set yours

Then go to your instance of the Telegram bot and type `/start`
