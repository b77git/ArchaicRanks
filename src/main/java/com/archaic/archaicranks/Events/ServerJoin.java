package com.archaic.archaicranks.Events;

import com.archaic.archaicranks.ArchaicRanks;
import com.archaic.archaicranks.Helper.PlayerData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import static com.archaic.archaicranks.Json.JsonHelper.addPlayerDataToFile;
import static com.archaic.archaicranks.Json.JsonHelper.doesPlayerExistByName;

public class ServerJoin {

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        String playerName = event.player.getDisplayName().getUnformattedText();
        String playerUUID = event.player.getUniqueID().toString();

        if (!doesPlayerExistByName(playerName, ArchaicRanks.playerDataFile)) {
            PlayerData newPlayerData = new PlayerData(playerName, playerUUID);
            addPlayerDataToFile(newPlayerData, ArchaicRanks.playerDataFile);
        }
    }
}
