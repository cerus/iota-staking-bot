package dev.cerus.iotastaking.api;

public class TokenInfo {

    private final long migratedTokens;
    private final long migrationAddresses;
    private final long stakedTokens;
    private final long lockedTokens;
    private final long lastUpdated;

    public TokenInfo(final long migratedTokens,
                     final long migrationAddresses,
                     final long stakedTokens,
                     final long lockedTokens,
                     final long lastUpdated) {
        this.migratedTokens = migratedTokens;
        this.migrationAddresses = migrationAddresses;
        this.stakedTokens = stakedTokens;
        this.lockedTokens = lockedTokens;
        this.lastUpdated = lastUpdated;
    }

    public boolean isStakingActive() {
        return this.stakedTokens != Long.MIN_VALUE;
    }

    public long getMigratedTokens() {
        return this.migratedTokens;
    }

    public long getMigrationAddresses() {
        return this.migrationAddresses;
    }

    public long getStakedTokens() {
        return this.stakedTokens;
    }

    public long getLockedTokens() {
        return this.lockedTokens;
    }

    public long getLastUpdated() {
        return this.lastUpdated;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
                "migratedTokens=" + this.migratedTokens +
                ", migrationAddresses=" + this.migrationAddresses +
                ", stakedTokens=" + this.stakedTokens +
                ", lockedTokens=" + this.lockedTokens +
                ", lastUpdated=" + this.lastUpdated +
                '}';
    }

}
