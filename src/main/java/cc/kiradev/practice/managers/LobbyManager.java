package cc.kiradev.practice.managers;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.config.ConfigManager;
import cc.kiradev.practice.enums.UserState;
import cc.kiradev.practice.user.User;
import cc.kiradev.practice.utils.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LobbyManager {

    public void setInLobby(Player player) {
        User user = Radium.getInstance().getUserManager().getByUuid(player.getUniqueId());
        user.setUserState(UserState.LOBBY);
        PlayerUtil.resetPlayer(player);
        teleportToSpawn(player);
    }
    public static void teleportToSpawn(Player player) {
        Location location = player.getLocation();
        float yaw = ConfigManager.getInstance().getLocationsConfig().getInt("Spawn.Yaw");
        float pitch = ConfigManager.getInstance().getLocationsConfig().getInt("Spawn.Pitch");
        double x = ConfigManager.getInstance().getLocationsConfig().getDouble("Spawn.X");
        double y = ConfigManager.getInstance().getLocationsConfig().getDouble("Spawn.Y");
        double z = ConfigManager.getInstance().getLocationsConfig().getDouble("Spawn.Z");

        location.setYaw(yaw);
        location.setPitch(pitch);
        location.setY(y);
        location.setX(x);
        location.setZ(z);

        player.teleport(location);

    }
}
