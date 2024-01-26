package com.archaic.archaicranks.Json;

import com.archaic.archaicranks.ArchaicRanks;
import com.archaic.archaicranks.Helper.PlayerData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private static final Type PLAYER_DATA_LIST_TYPE = new TypeToken<List<PlayerData>>() {}.getType();

    private static <T> void writeDataToFile(List<T> data, File dataFile) {
        try (FileWriter writer = new FileWriter(dataFile)) {
            gson.toJson(data, writer);
            ArchaicRanks.logger.info("Data written successfully to: " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            ArchaicRanks.logger.info("An error occurred while writing data to the file " + e);
        }
    }

    private static <T> void addDataToFile(List<T> existingData, T newData, File dataFile) {
        if (!existingData.contains(newData)) {
            existingData.add(newData);
            writeDataToFile(existingData, dataFile);
        }
    }

    public static void addPlayerDataToFile(PlayerData newData, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);
        addDataToFile(existingData, newData, dataFile);
    }

    public static List<PlayerData> readExistingPlayerDataFromFile(File dataFile) {
        try (FileReader reader = new FileReader(dataFile)) {
            return gson.fromJson(reader, PLAYER_DATA_LIST_TYPE);
        } catch (IOException e) {
            ArchaicRanks.logger.error("An error occurred while reading player data from the file: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static boolean doesPlayerExistByName(String playerName, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);

        for (PlayerData playerData : existingData) {
            if (playerData.getUsername().equals(playerName)) {
                return true; // Player found
            }
        }

        return false; // Player not found
    }

    public static PlayerData getPlayerDataByName(String playerName, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);

        for (PlayerData playerData : existingData) {
            if (playerData.getUsername().equals(playerName)) {
                return playerData;
            }
        }

        return null; // Player data not found
    }

    public static void updatePlayerDataInFile(PlayerData updatedData, File dataFile) {
        List<PlayerData> existingData = readExistingPlayerDataFromFile(dataFile);
        for (int i = 0; i < existingData.size(); i++) {
            PlayerData existingPlayerData = existingData.get(i);
            if (existingPlayerData.getUuid().equals(updatedData.getUuid())) {
                existingData.set(i, updatedData);
                writeDataToFile(existingData, dataFile);
                return;
            }
        }
    }
}
