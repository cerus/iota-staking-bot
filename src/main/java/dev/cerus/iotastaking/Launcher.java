package dev.cerus.iotastaking;

import dev.cerus.iotastaking.api.IotaApi;
import dev.cerus.iotastaking.api.TokenInfo;
import dev.cerus.iotastaking.bot.StakingInfoBot;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Launcher {

    public static void main(final String[] args) {
        final IotaApi iotaApi = new IotaApi();
        final StakingInfoBot bot = new StakingInfoBot();
        bot.init(System.getenv("IOTASTAKING_TOKEN"));

        final ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            final TokenInfo info = iotaApi.getLatestTokenInfo();
            bot.updatePresence(info);
            System.out.println("Update: " + info);
        }, 0, 5, TimeUnit.MINUTES);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            exec.shutdownNow();
            bot.shutdown();
            System.out.println("Bye bye!");
        }));
    }

}
