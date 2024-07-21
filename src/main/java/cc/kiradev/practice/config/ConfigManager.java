package cc.kiradev.practice.config;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.utils.CC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ConfigManager {

    @Getter private static ConfigManager instance;

    private final Map<String, File> configFiles = new HashMap<>();
    private final Map<String, FileConfiguration> fileConfigurations = new HashMap<>();

    private final FileConfiguration locationsConfig;
    private final FileConfiguration messagesConfig;
    private final FileConfiguration scoreboardConfig;

    private final String[] configFileNames = {
            "locations.yml", "messages.yml", "scoreboard.yml"
    };

    public ConfigManager() {
        for (String fileName : configFileNames) {
            loadConfig(fileName);
        }

        instance = this;

        locationsConfig = getConfig("locations.yml");
        messagesConfig = getConfig("messages.yml");
        scoreboardConfig = getConfig("scoreboard.yml");

    }

    private void loadConfig(String fileName) {
        File configFile = new File(Radium.getInstance().getDataFolder(), fileName);
        configFiles.put(fileName, configFile);
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            Radium.getInstance().saveResource(fileName, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        fileConfigurations.put(fileName, config);
    }

    public void reloadConfigs() {
        for (String fileName : configFileNames) {
            loadConfig(fileName);
        }
    }

    public void saveConfigs() {
        for (Map.Entry<String, FileConfiguration> entry : fileConfigurations.entrySet()) {
            String fileName = entry.getKey();
            FileConfiguration config = entry.getValue();
            File configFile = configFiles.get(fileName);
            saveConfig(configFile, config);
        }
    }

    public void saveConfig(File configFile, FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(configFile);
            fileConfiguration.load(configFile);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&4[!] Error occurred while saving config: &f" + configFile.getName()));
        }
    }

    public FileConfiguration getConfig(String fileName) {
        return fileConfigurations.get(fileName);
    }

    public File getConfigFile(String fileName) {
        return configFiles.get(fileName);
    }
}

