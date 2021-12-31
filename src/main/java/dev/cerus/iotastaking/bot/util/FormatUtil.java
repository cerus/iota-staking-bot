package dev.cerus.iotastaking.bot.util;

public class FormatUtil {

    public static long KI = 1000;
    public static long MI = 1000 * KI;
    public static long GI = 1000 * MI;
    public static long TI = 1000 * GI;
    public static long PI = 1000 * TI;

    public static String formatIota(final long iota) {
        if (iota < KI) {
            return iota + " i";
        } else if (iota < MI) {
            return format(iota, KI) + " Ki";
        } else if (iota < GI) {
            return format(iota, MI) + " Mi";
        } else if (iota < TI) {
            return format(iota, GI) + " Gi";
        } else if (iota < PI) {
            return format(iota, TI) + " Ti";
        } else {
            return format(iota, PI) + " Pi";
        }
    }

    private static String format(final long n, final long s) {
        return String.format("%.2f", (double) n / (double) s);
    }

}
