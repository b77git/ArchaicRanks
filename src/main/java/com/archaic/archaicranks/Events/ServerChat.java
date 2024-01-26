package com.archaic.archaicranks.Events;

import com.archaic.archaicranks.ArchaicRanks;
import com.archaic.archaicranks.ConfigHandler;
import com.archaic.archaicranks.Helper.PlayerData;
import com.archaic.archaicranks.Json.JsonHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import netscape.javascript.JSObject;

public class ServerChat {
    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        String username = event.getPlayer().getDisplayName().getUnformattedText();
        PlayerData playerData = JsonHelper.getPlayerDataByName(username, ArchaicRanks.playerDataFile);
        int playerRank = playerData.getRank();

        String CHAT_PREFIX = ConfigHandler.RANK_CONFIGS.get(ConfigHandler.RankCategory.getByIndex(playerRank)).prefix.replaceAll("&", "§");;
        // Get the original chat message
        String originalMessage = event.getMessage();

        // Add the prefix to the message
        String prefixedMessage = CHAT_PREFIX + " <" + username + "> " + originalMessage;

        // Create a new TextComponentString with the prefixed message
        TextComponentString newMessage = new TextComponentString(prefixedMessage);

        // Set the new message in the event
        event.setComponent(newMessage);
    }
}