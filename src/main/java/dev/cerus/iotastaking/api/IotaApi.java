package dev.cerus.iotastaking.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IotaApi {

    public long getStakedTokensAmount() {
        try {
            final String result = this.makeRequest("https://chrysalis.iota.org/api/staking", "GET");
            return Long.parseLong(result);
        } catch (final IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public TokenInfo getLatestTokenInfo() {
        final JsonObject obj = this.getLatestTokenInfoRaw();
        return new TokenInfo(
                obj.get("migratedTokens").getAsLong(),
                obj.get("migrationAddresses").getAsLong(),
                this.getStakedTokensAmount(),
                obj.get("lockedTokens").getAsLong(),
                obj.get("lastUpdated").getAsLong()
        );
    }

    private JsonObject getLatestTokenInfoRaw() {
        final JsonArray array;
        try {
            final String result = this.makeRequest("https://chrysalis.iota.org/api/tokens", "GET");
            array = JsonParser.parseString(result).getAsJsonArray();
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }

        JsonObject latest = null;
        for (final JsonElement element : array) {
            if (element.isJsonObject()) {
                final JsonObject obj = element.getAsJsonObject();
                if (latest == null || obj.get("lastUpdated").getAsLong() > latest.get("lastUpdated").getAsLong()) {
                    latest = obj;
                }
            }
        }
        return latest;
    }

    private String makeRequest(final String url, final String method) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("User-Agent", "cerus/iota-staking");
        connection.setDoInput(true);

        InputStream in;
        try {
            in = connection.getInputStream();
        } catch (final IOException ignored) {
            in = connection.getErrorStream();
        }

        final StringBuilder buffer = new StringBuilder();
        int b;
        while ((b = in.read()) != -1) {
            buffer.append((char) b);
        }
        return buffer.toString();
    }

}
