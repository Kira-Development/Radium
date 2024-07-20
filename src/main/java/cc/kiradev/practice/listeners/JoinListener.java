package cc.kiradev.practice.listeners;

import cc.kiradev.practice.Radium;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Radium.getInstance().getLobbyManager().setInLobby(player);
    }

}
