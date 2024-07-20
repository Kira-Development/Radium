package cc.kiradev.practice.listeners;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class UserListener implements Listener {

    private final Radium plugin = Radium.getInstance();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        Player player = Bukkit.getPlayer(event.getUniqueId());
        if (player != null && player.isOnline()) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cYou tried to login too quickly after disconnecting.\n§cTry again in a few seconds.");

            this.plugin.getServer().getScheduler().runTask(this.plugin, () -> player.kickPlayer("§cDuplicate login kick"));
            return;
        }

        User user = this.plugin.getUserManager().getOrCreate(event.getUniqueId());
        this.plugin.getUserManager().saveUser(user);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        User user = this.plugin.getUserManager().getOrCreate(event.getPlayer().getUniqueId());
        if (user == null) {
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cAn error has occurred while loading your profile. Please reconnect.");
            return;
        }

        if (!user.isLoaded()) {
            this.plugin.getUserManager().saveUser(user);
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            event.setKickMessage("§cAn error has occurred while loading your profile. Please reconnect.");
        }
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        handledSaveDate(event.getPlayer());
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {
        handledSaveDate(event.getPlayer());
    }

    private void handledSaveDate(Player player) {
        User user = this.plugin.getUserManager().getOrCreate(player.getUniqueId());
        if (user != null) {
            this.plugin.getUserManager().deleteUser(player.getUniqueId());
        }
    }
}
