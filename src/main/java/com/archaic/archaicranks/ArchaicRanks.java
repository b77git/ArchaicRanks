package com.archaic.archaicranks;

import com.archaic.archaicranks.Events.ItemPickup;
import com.archaic.archaicranks.Events.ServerChat;
import com.archaic.archaicranks.Events.ServerJoin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(
        modid = ArchaicRanks.MOD_ID,
        name = ArchaicRanks.MOD_NAME,
        version = ArchaicRanks.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
public class ArchaicRanks {

    public static final String MOD_ID = "archaicranks";
    public static final String MOD_NAME = "ArchaicRanks";
    public static final String VERSION = "1.0-SNAPSHOT";

    public static final Logger logger = LogManager.getLogger(ArchaicRanks.MOD_ID);
    public static File datafolderDir;
    public static ConfigHandler configHandler;

    public static File playerDataFile;
    public static File teamDatafile;

    @Mod.Instance(MOD_ID)
    public static ArchaicRanks INSTANCE;

    @EventHandler
    public void serverSetup(FMLServerStartingEvent event) {
        File serverDir = event.getServer().getDataDirectory();
        datafolderDir = new File(serverDir, "RanksData");
        configSetup(serverDir);

        playerDataFile = new File(ArchaicRanks.datafolderDir, "PlayerData.json");

        if (!datafolderDir.exists()) {
            if (datafolderDir.mkdir()) {
                logger.info("EventData folder created at: " + datafolderDir.getAbsolutePath());
            } else {
                logger.error("Failed to create EventData folder!");
            }
        }
    }

    public void configSetup(File serverDir){
        File configDir = new File(serverDir, "config");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        File configFile = new File(configDir, "archaicranks.cfg");
        configHandler = new ConfigHandler();
        configHandler.init(configFile, logger);

        MinecraftForge.EVENT_BUS.register(new ItemPickup());
        MinecraftForge.EVENT_BUS.register(new ServerJoin());
        MinecraftForge.EVENT_BUS.register(new ServerChat());
    }
}