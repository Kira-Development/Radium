package cc.kiradev.practice.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
@AllArgsConstructor
public class Arena {

    private String name;
    private Location a; // Red
    private Location b; // Blue
    private ItemStack icon;
    private boolean enabled;

}
