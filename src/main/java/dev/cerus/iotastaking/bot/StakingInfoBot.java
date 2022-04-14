package dev.cerus.iotastaking.bot;

import dev.cerus.iotastaking.api.TokenInfo;
import dev.cerus.iotastaking.bot.util.FormatUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;

public class StakingInfoBot {

    private final List<Double> percentageRecords = new ArrayList<>();
    private int ticks = 0;
    private JDA jda;

    public void init(final String token) {
        try {
            this.jda = JDABuilder.create(token, List.of()).build().awaitReady();
        } catch (final InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public void updatePresence(final TokenInfo info) {
        final String infoStr;
        final String watching;

        if (info.isStakingActive()) {
            final double percent = ((double) info.getStakedTokens() / (double) info.getMigratedTokens()) * 100D;
            this.percentageRecords.add(percent);
            if (this.percentageRecords.size() > 60) {
                this.percentageRecords.remove(0);
            }

            final double changeInLastHour = percent - this.percentageRecords.get(0);
            String diffStr = String.format("%.4f", changeInLastHour);
            if (changeInLastHour >= 0) {
                diffStr = "+" + diffStr;
            }

            System.out.println("Change in last hour: " + diffStr + " " + String.format("%f", changeInLastHour) + " " + String.format("%.4f", percent - this.percentageRecords.get(0)));
            infoStr = String.format(
                    "%.4f%% (%s of %s)",
                    percent,
                    FormatUtil.formatIota(info.getStakedTokens()),
                    FormatUtil.formatIota(info.getMigratedTokens())
            );

            if (this.ticks % 2 == 0) {
                watching = String.format(
                        "1h: %s%% | %s",
                        diffStr,
                        new SimpleDateFormat("dd MMM HH:mm z")
                                .format(new Date(info.getLastUpdated()))
                );
            } else {
                watching = String.format(
                        "%.4f%% @ %s",
                        percent,
                        new SimpleDateFormat("dd MMM HH:mm z")
                                .format(new Date(info.getLastUpdated()))
                );
            }
        } else {
            infoStr = "Staking is not active";
            watching = "Staking is not active";
        }

        for (final Guild guild : this.jda.getGuilds()) {
            if (guild.getSelfMember().hasPermission(Permission.NICKNAME_CHANGE)) {
                System.out.println("Updating on guild '" + guild.getName() + "'");
                guild.getSelfMember().modifyNickname(infoStr).queue();
            }
        }

        this.jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching(watching));
        this.ticks++;
    }

    public void shutdown() {
        this.jda.shutdown();
    }

}
