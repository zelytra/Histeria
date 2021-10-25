package fr.zelytra.histeria.commands.vote;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.Duration;

public class Vote {

    private String secretKey;
    private String url;

    private int timer = 15; //time in seconds


    public Vote() {

        try {

            YamlConfiguration configuration = new YamlConfiguration();
            InputStream is = Histeria.getInstance().getResource("server.yml");
            Reader reader = new InputStreamReader(is);
            configuration.load(reader);

            this.secretKey = configuration.getString("voteAPI.key");
            this.url = configuration.getString("voteAPI.url");


        } catch (InvalidConfigurationException | IOException e) {

            Bukkit.getConsoleSender().sendMessage("§cYou are not running the plugin on the official server. Shutting down");
            Bukkit.shutdown();

        }

        startWebListener();
    }

    private void startWebListener() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Histeria.getInstance(), () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {

                URLConnection connection;

                try {

                    String ip = player.getAddress().toString().split(":")[0];
                    connection = new URL(this.url + this.secretKey + ip).openConnection();
                    connection.connect();

                    BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

                    StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                    }

                    //Player has vote
                    if (sb.toString().equalsIgnoreCase("1"))
                        vote(player);


                } catch (IOException e) {
                    Histeria.log("Failed to connect to website", LogType.WARN);
                }

            }
        }, timer * 20, timer * 20);
    }

    public void vote(Player player) {
        User user = Histeria.getLuckPerms().getPlayerAdapter(Player.class).getUser(player);
        user.data().add(Node.builder("group.vote").expiry(Duration.ofHours(24)).build());
        Histeria.getLuckPerms().getUserManager().saveUser(user);
        LangMessage.sendMessage(player, "vote.voteSuccess");
    }
}
