package cc.kiradev.practice.managers;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.config.ConfigManager;
import cc.kiradev.practice.enums.UserState;
import cc.kiradev.practice.user.User;
import cc.kiradev.practice.utils.PlayerUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static cc.kiradev.practice.utils.PlayerUtil.teleportToSpawn;

public class LobbyManager {

    public void setInLobby(Player player) {
        User user = Radium.getInstance().getUserManager().getByUuid(player.getUniqueId());
        user.setUserState(UserState.LOBBY);
        PlayerUtil.resetPlayer(player);
        teleportToSpawn(player);
    }
}
