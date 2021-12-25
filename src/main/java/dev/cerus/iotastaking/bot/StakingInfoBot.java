package dev.cerus.iotastaking.bot;

import dev.cerus.iotastaking.api.TokenInfo;
import dev.cerus.iotastaking.bot.util.FormatUtil;
import java.text.SimpleDateFormat;
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

    private JDA jda;

    public void init(final String token) {
        try {
            this.jda = JDABuilder.create(token, List.of()).build().awaitReady();
        } catch (final InterruptedException | LoginException e) {
            e.printStackTrace();
        }
    }

    public void updatePresence(final TokenInfo info) {
        final double percent = ((double) info.getStakedTokens() / (double) info.getMigratedTokens()) * 100D;
        final String infoStr = String.format(
                "%.4f%% (%s of %s)",
                percent,
                FormatUtil.formatIota(info.getStakedTokens()),
                FormatUtil.formatIota(info.getMigratedTokens())
        );

        for (final Guild guild : this.jda.getGuilds()) {
            if (guild.getSelfMember().hasPermission(Permission.NICKNAME_CHANGE)) {
                guild.getSelfMember().modifyNickname(infoStr).queue();
            }
        }

        final String watching = String.format(
                "%.4f%% @ %s",
                percent,
                new SimpleDateFormat("dd MMM HH:mm z")
                        .format(new Date(info.getLastUpdated()))
        );
        this.jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.watching(watching));
    }

    public void shutdown() {
        this.jda.shutdown();
    }

}
