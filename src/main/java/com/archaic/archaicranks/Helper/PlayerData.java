package com.archaic.archaicranks.Helper;

import com.archaic.archaicranks.ArchaicRanks;
import com.archaic.archaicranks.Json.JsonHelper;

import static com.archaic.archaicranks.Json.JsonHelper.updatePlayerDataInFile;

public class PlayerData {
    private final String username;
    private final String uuid;
    private int rank;


    public PlayerData (String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
        this.rank = 0;
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setRank(int rank) {
        this.rank = rank;
        updatePlayerDataInFile(this, ArchaicRanks.playerDataFile);
    }
}
