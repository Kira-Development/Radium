package cc.kiradev.practice.managers;

import cc.kiradev.practice.Radium;
import cc.kiradev.practice.utils.CC;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Collections;

@Getter
public class MongoManager {

    private final MongoManager instance;
    private final Radium plugin = Radium.getInstance();

    private MongoClient client;
    private MongoDatabase mongoDatabase;

    private final String host = this.plugin.getConfig().getString("MONGO.HOST");
    private final int port = this.plugin.getConfig().getInt("MONGO.PORT");
    private final String database = this.plugin.getConfig().getString("MONGO.DATABASE");
    private final boolean authentication = this.plugin.getConfig().getBoolean("MONGO.AUTH.ENABLED");

    private final String user = this.plugin.getConfig().getString("MONGO.AUTH.USERNAME");
    private final String password = this.plugin.getConfig().getString("MONGO.AUTH.PASSWORD");
    private final String authDatabase = this.plugin.getConfig().getString("MONGO.AUTH.AUTH-DATABASE");

    private boolean connected;

    private MongoCollection<Document> users;

    public MongoManager() {
        instance = this;

        try {
            if (authentication) {
                final MongoCredential credential = MongoCredential.createCredential(user, authDatabase, password.toCharArray());
                client = new MongoClient(new ServerAddress(host, port), Collections.singletonList(credential));
            } else {
                client = new MongoClient(host, port);
            }
            connected = true;
            mongoDatabase = client.getDatabase(database);
            this.users = this.mongoDatabase.getCollection("users");
        } catch (Exception e) {
            connected = false;
            Bukkit.getConsoleSender().sendMessage(CC.translate("&c[Radium] &cFailed to connect to MongoDB"));
            e.printStackTrace();

            Bukkit.getServer().getPluginManager().disablePlugin(this.plugin);
            Bukkit.getConsoleSender().sendMessage(CC.translate("&c[Radium] &cDisabling plugin..."));
        }
    }

    public void disconnect() {
        if (this.client != null) {
            this.plugin.getLogger().info("[Radium] Disconnecting MongoDB...");
            this.client.close();
            this.connected = false;
            this.plugin.getLogger().info("[Radium] MongoDB has been successfully disconnected.");
        }
    }

    public MongoDatabase getDatabase() {
        return this.mongoDatabase;
    }
}