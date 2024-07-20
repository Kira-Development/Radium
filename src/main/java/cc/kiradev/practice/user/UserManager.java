package cc.kiradev.practice.user;

import cc.kiradev.practice.Radium;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import lombok.Getter;
import org.bson.Document;

import java.util.*;

public class UserManager {

    @Getter private final Map<UUID, User> users = new HashMap<>();
    private final Radium plugin = Radium.getInstance();

    public User getOrCreate(UUID uuid) {
        return users.computeIfAbsent(uuid, User::new);
    }

    public User getByUuid(UUID uuid) {
        return users.get(uuid);
    }

    public Collection<User> getAllUsers() {
        return this.users.values();
    }

    public void loadUser(User user) {
        Document document = this.plugin.getMongoManager().getUsers().find(Filters.eq("uniqueId", user.getUniqueId().toString())).first();
        if (document != null) {

        }

        user.setLoaded(true);
    }

    public void saveUser(User user) {
        Document document = new Document();
        document.put("uniqueId", user.getUniqueId().toString());

        this.plugin.getMongoManager().getUsers().replaceOne(Filters.eq("uniqueId", user.getUniqueId().toString()), document, new UpdateOptions().upsert(true));
    }

    public void deleteUser(UUID uniqueId) {
        this.saveUser(this.getByUuid(uniqueId));
        this.getUsers().remove(uniqueId);
    }

}
