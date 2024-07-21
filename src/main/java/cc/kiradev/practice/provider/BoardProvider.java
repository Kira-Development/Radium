package cc.kiradev.practice.provider;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.config.ConfigManager;
import cc.kiradev.practice.enums.UserState;
import cc.kiradev.practice.user.User;
import cc.kiradev.practice.utils.CC;
import io.github.thatkawaiisam.assemble.AssembleAdapter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BoardProvider implements AssembleAdapter {
    @Override
    public String getTitle(Player player) {
        return CC.translate(ConfigManager.getInstance().getScoreboardConfig().getString("SCOREBOARD.TITLE"));
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();
        User user = Radium.getInstance().getUserManager().getByUuid(player.getUniqueId());
        if(user.getUserState().equals(UserState.LOBBY)) {
            for(String sb : ConfigManager.getInstance().getScoreboardConfig().getStringList("SCOREBOARD.LINES.LOBBY")) {
                String msg = sb;
                msg = msg.replaceAll("<online>", String.valueOf(Bukkit.getOnlinePlayers().size()));
                lines.add(PlaceholderAPI.setPlaceholders(player, msg));
            }
        }
        return lines;
    }
}
