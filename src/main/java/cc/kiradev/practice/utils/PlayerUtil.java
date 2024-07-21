package cc.kiradev.practice.utils;

import cc.kiradev.practice.config.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class PlayerUtil {

    public static void resetPlayer(Player player) {
        player.setGameMode(GameMode.SURVIVAL);

        player.closeInventory();
        player.getInventory().clear();
        player.getInventory().setHeldItemSlot(0);
        player.getInventory().setArmorContents(null);
        player.updateInventory();

        player.setHealth(20.0D);
        player.setFoodLevel(20);
        player.setSaturation(12.8F);
        player.setMaximumNoDamageTicks(20);
        player.setFireTicks(0);
        player.setFallDistance(0.0F);

        player.setLevel(0);
        player.setExp(0.0F);

        player.setWalkSpeed(0.2F);
        player.setFlySpeed(0.2F);
        player.setAllowFlight(false);

        player.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(player::removePotionEffect);
        ((CraftPlayer) player).getHandle().getDataWatcher().watch(9, (byte) 0);

    }


    public static boolean isInvEmpty(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                return false;
            }
        }

        return true;
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
