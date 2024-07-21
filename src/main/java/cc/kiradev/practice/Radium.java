package cc.kiradev.practice;

import cc.kiradev.practice.commands.MainCommand;
import cc.kiradev.practice.config.ConfigManager;
import cc.kiradev.practice.listeners.JoinListener;
import cc.kiradev.practice.listeners.UserListener;
import cc.kiradev.practice.managers.LobbyManager;
import cc.kiradev.practice.managers.MongoManager;
import cc.kiradev.practice.provider.BoardProvider;
import cc.kiradev.practice.user.User;
import cc.kiradev.practice.user.UserManager;
import co.aikar.commands.PaperCommandManager;
import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

@Getter
public class Radium extends JavaPlugin {

    @Getter
    private static Radium instance;
    private MongoManager mongoManager;
    private UserManager userManager;
    private ConfigManager configManager;
    private PaperCommandManager paperCommandManager;
    private LobbyManager lobbyManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getManagers();
        getListeners();
        getCommands();
        getScoreboard();

    }

    @Override
    public void onDisable() {
        for(User user : this.userManager.getAllUsers()) {
            this.userManager.saveUser(user);
        }
        this.mongoManager.disconnect();
    }
    public void getManagers() {
        this.configManager = new ConfigManager();
        this.mongoManager = new MongoManager();
        this.userManager = new UserManager();
        this.paperCommandManager = new PaperCommandManager(this);
        this.lobbyManager = new LobbyManager();
    }
    public void getListeners() {
        Arrays.asList(
                new UserListener(),
                new JoinListener()
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }
    public void getCommands() {
        Arrays.asList(
                new MainCommand()
        ).forEach(command -> paperCommandManager.registerCommand(command));
    }
    public void getScoreboard() {
        Assemble assemble = new Assemble(this, new BoardProvider());
        assemble.setTicks(20);
        assemble.setAssembleStyle(AssembleStyle.MODERN);
    }
}
