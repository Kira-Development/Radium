package cc.kiradev.practice.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@AllArgsConstructor
public class Arena {

    private String name;
    private Location playerOneSpawn;
    private Location playerTwoSpawn;
    private ItemStack icon;
    private boolean enabled;
    private ArenaType type;

    public enum ArenaType {
        SHARED,
        STANDALONE
    }

    public boolean isComplete() {
        return playerOneSpawn != null && playerTwoSpawn != null;
    }
}