package cc.kiradev.practice.user;

import cc.kiradev.practice.Radium;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class User {

    private final UserManager userManager = Radium.getInstance().getUserManager();

    private final UUID uniqueId;

    private boolean loaded;

    public User(UUID uuid) {
        this.uniqueId = uuid;
        this.loaded = false;

        getUserManager().loadUser(this);
    }
}