package cc.kiradev.practice;

import cc.kiradev.practice.listeners.UserListener;
import cc.kiradev.practice.managers.MongoManager;
import cc.kiradev.practice.user.User;
import cc.kiradev.practice.user.UserManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class Radium extends JavaPlugin {

    @Getter
    private static Radium instance;
    private MongoManager mongoManager;
    private UserManager userManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getManagers();
        getListeners();

    }

    @Override
    public void onDisable() {
        for(User user : this.userManager.getAllUsers()) {
            this.userManager.saveUser(user);
        }
        this.mongoManager.disconnect();
    }
    public void getManagers() {
        this.mongoManager = new MongoManager();
        this.userManager = new UserManager();
    }
    public void getListeners() {
        Arrays.asList(
                new UserListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
}
