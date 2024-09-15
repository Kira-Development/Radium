package cc.kiradev.practice.user;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.enums.UserState;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class User {

    private final UserManager userManager = Radium.getInstance().getUserManager();

    private final UUID uniqueId;

    private boolean loaded;

    private int wins;
    private int losses;
    private int currentWinstreak;
    private int highestWinstreak;
    private int played;
    private int kills;
    private int deaths;
    private int coins;

    private UserState userState = UserState.LOBBY;

    public User(UUID uuid) {
        this.uniqueId = uuid;
        this.loaded = false;

        getUserManager().loadUser(this);
    }
    public boolean isLobby() {
        return userState.equals(UserState.LOBBY);
    }
}