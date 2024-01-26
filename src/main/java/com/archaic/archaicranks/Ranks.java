package com.archaic.archaicranks;

import com.archaic.archaicranks.Helper.PlayerData;
import com.archaic.archaicranks.Json.JsonHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Objects;

public class Ranks {
    public static void validate_item(EntityPlayer player, String picked_item){
        String username = player.getDisplayName().getUnformattedText();
        PlayerData playerData = JsonHelper.getPlayerDataByName(username, ArchaicRanks.playerDataFile);
        int playerRank = playerData.getRank();

        String expectedItem = ConfigHandler.RANK_CONFIGS.get(ConfigHandler.RankCategory.getByIndex(playerRank)).itemRequired;

        if (Objects.equals(picked_item, expectedItem)){
            rankup(playerData, playerRank);
            declareRankup(username, playerRank + 1);
        }
    }

    public static void rankup(PlayerData player, int rank){
        player.setRank(rank + 1);
    }

    public static void declareRankup(String username, int rank) {
        MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

        String archaicPrefix = "&7[&bArchaic Ranks&7]&r";
        String rank_prefix = ConfigHandler.RANK_CONFIGS.get(ConfigHandler.RankCategory.getByIndex(rank)).prefix;
        String message = archaicPrefix + " " + username + " has ranked up to " + rank_prefix + "!".replaceAll("&", "§");;;

        if (server != null) {
            for (EntityPlayer player : server.getPlayerList().getPlayers()) {
                player.sendMessage(new TextComponentString(colorize(message)));
            }
        }
    }

    private static String colorize(String message) {
        return message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
    }
}
