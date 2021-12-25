# IOTA staking info bot

A simple Discord bot that provides up-to-date info on the IOTA x SMR x ASMB staking event

Tips: `iota1qrfvzlp6ggleuu8xrh720rzj0ghw7hqkxqt6f8k9caj48xpt3q9csrgklmn`

## Add official bot to your server

Invite url: https://discord.com/api/oauth2/authorize?client_id=924138704535162910&permissions=67108864&scope=bot

Please note: to operate flawlessly the bot only needs permission to change its own nickname.

## Running your own instance

**Java 17 is required!**

1. Clone repository
2. Run `mvn clean package`
3. Artifact is in `target/` (`iota-staking-x.x.x.jar`)
4. Run artifact: `IOTASTAKING_TOKEN=your_discord_bot_token java -jar iota-staking-*.jar -Xmx 206M`

## Images

![Bot ping](https://i.imgur.com/fsc1LTW.png)

![Bot info](https://i.imgur.com/yQwoqYU.png)