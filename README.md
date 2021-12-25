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
4. Run the artifact
    1. The old-fashioned way: `IOTASTAKING_TOKEN=your_discord_bot_token java -jar iota-staking-*.jar -Xmx 206M`
    2. Using docker-compose: Open the spoiler below

<details>
  <summary>Using docker-compose</summary>

Example `docker-compose.yml`

> Requires an environment variable called `IOTASTAKING_TOKEN` for the Discord bot token (can be in a `.env` file)

```yaml
version: '3.3'

services:
  bot:
    container_name: "iota_staking_bot"
    image: "openjdk:17"
    volumes:
      - "./data:/opt/data"
    command: "bash -c \"cd /opt/data && IOTASTAKING_TOKEN=\\\"${IOTASTAKING_TOKEN}\\\" java -jar iota-staking-*.jar\""
```

</details>

## Images

![Bot ping](https://i.imgur.com/fsc1LTW.png)

![Bot info](https://i.imgur.com/yQwoqYU.png)