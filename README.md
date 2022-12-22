# Telegram Crypto Bot

[![Build and Deploy](https://github.com/fedy97/telegram-crypto-bot/actions/workflows/deploy.yml/badge.svg)](https://github.com/fedy97/telegram-crypto-bot/actions/workflows/deploy.yml)

### Features:

- [x] Built with [TelegramBots](https://github.com/rubenlagus/TelegramBots) in `Java`
- [x] Track Coingecko Portfolio
- [x] MongoDB Integration
- [x] Caching System
- [x] Telegram Authorization
- [x] Maven Build
- [x] Dockerized image
- [x] DockerHub upload
- [x] Deployed on Oracle VPS
- [x] Full CI/CD Flow with Actions


### Design Patterns:

- [x] Command
- [x] Singleton
- [x] Decorator
- [x] Facade
- [x] Repository


#### How to Use it:

To run the bot, you need to define the following Environmental Variables:

- `TELEGRAM_BOT_NAME` = Ask BotFather in Telegram
- `TELEGRAM_BOT_TOKEN` = Ask BotFather in Telegram
- `CG_URL_PRIVATE` = Coingecko public URL of your portfolio
- `CG_URL_SHARED` = Optionally, Coingecko public URL of another portfolio
- `MONGO_DB_URI` = To connect to MongoDB
- `COINS_COLLECTION` = Name of the collection, you could use `coins`
- `TG_ADMIN` = Telegram username of the owner, you can set yours

Then go to your instance of the Telegram bot and type `/start`
