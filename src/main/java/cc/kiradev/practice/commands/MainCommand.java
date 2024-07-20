package cc.kiradev.practice.commands;

import cc.kiradev.practice.config.ConfigManager;
import cc.kiradev.practice.utils.CC;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("radium")
@CommandPermission("radium.admin")
@Description("The main command for Radium")
public class MainCommand extends BaseCommand {

    @Default
    @HelpCommand
    public void onDefault(Player player) {
        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&c&lRadium Practice Core"));
        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&r &7&o* &f/radium setspawn &7- &8(&7&oSet the lobby location&8)"));
        player.sendMessage(CC.translate("&r &7&o* &f/radium reload &7- &8(&7&oReload Radium's configurations&8)"));
        player.sendMessage(CC.CHAT_BAR);
    }
    @Subcommand("reload")
    @Description("Reload Radium")
    public void onReload(Player player) {
        ConfigManager.getInstance().reloadConfigs();
        player.sendMessage(CC.translate(ConfigManager.getInstance().getMessagesConfig().getString("RELOAD")));
    }
    @Subcommand("setspawn")
    @Description("Set the spawn location")
    public void setSpawn(Player player) {
        FileConfiguration config = ConfigManager.getInstance().getLocationsConfig();

        config.set("Spawn.World", player.getLocation().getWorld().getName());
        config.set("Spawn.X", player.getLocation().getX());
        config.set("Spawn.Y", player.getLocation().getY());
        config.set("Spawn.Z", player.getLocation().getZ());
        config.set("Spawn.Yaw", player.getLocation().getYaw());
        config.set("Spawn.Pitch", player.getLocation().getPitch());
        ConfigManager.getInstance().saveConfig(ConfigManager.getInstance().getConfigFile("locations.yml"), config);
        player.sendMessage(CC.translate(ConfigManager.getInstance().getMessagesConfig().getString("SETSPAWN")));
    }
}
