package fr.zelytra.histeria.managers.player;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.commands.staffModeration.Mute.Mute;
import fr.zelytra.histeria.managers.economy.Bank;
import fr.zelytra.histeria.managers.languages.Lang;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CustomPlayer {

    private static final List<CustomPlayer> customPlayerList = new ArrayList<>();

    private final String name;
    private int kill;
    private int death;
    private int timePlayed = 0;
    private long lastConnection;
    private String ip;
    private String primConnection;
    private Lang lang;
    private Mute mute;
    private final String uuid;

    private final Bank bank;
    private int id;
    //TODO Home

    public CustomPlayer(Player player) {
        this.name = player.getName();
        this.bank = new Bank(this);
        this.uuid = player.getUniqueId().toString();


        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            /* Init first time */
            if (!hasPlayedBefore()) {
                initData();
                this.id = getBaseID();
                this.bank.initNewAccount();

            } else {
                this.id = getBaseID();
                this.loadData();
            }
            customPlayerList.add(this);

        });

    }

    private void initData() {
        MySQL mySQL = Histeria.mySQL;
        this.lang = Lang.EN;
        lastConnection = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        mySQL.update("INSERT INTO `Player` (`uuid`,`name`,`ip`,`primconnection`,`lang`) VALUES ('"
                + Objects.requireNonNull(getPlayer()).getUniqueId() + "','"
                + getPlayer().getName() + "','"
                + Objects.requireNonNull(getPlayer().getAddress()).toString().replace("/", "").split(":")[0] + "','"
                + formatter.format(date) + "','"
                + lang.name() + "');");

    }

    private int getBaseID() {
        try {
            MySQL mySQL = Histeria.mySQL;
            ResultSet result = mySQL.query("SELECT `id` FROM `Player` WHERE `uuid` = '" + Objects.requireNonNull(getPlayer()).getUniqueId() + "';");
            result.next();
            int id = result.getInt("id");
            result.close();
            return id;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    private void loadData() {

        try {
            MySQL mySQL = Histeria.mySQL;
            lastConnection = System.currentTimeMillis();

            ResultSet resultSet = mySQL.query("SELECT `money` FROM `Bank` WHERE `id` =" + this.id + "; ");
            if (resultSet.next()) {
                this.bank.setMoney(resultSet.getInt("money"));
            } else {
                Histeria.log("§cFailed to load bank account of " + this.name, LogType.ERROR);
                Objects.requireNonNull(getPlayer()).sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cFailed to load bank account please try to reconnect to the server or contact an admin if the problem persist.");
            }

            resultSet = mySQL.query("SELECT * FROM `Player` WHERE `id` =" + this.id + "; ");
            if (resultSet.next()) {

                this.kill = resultSet.getInt("kill");
                this.death = resultSet.getInt("death");
                this.timePlayed = resultSet.getInt("playTime");
                this.ip = resultSet.getString("ip");
                this.primConnection = resultSet.getString("primconnection");
                this.lang = Lang.valueOf(resultSet.getString("lang"));

            }

            resultSet.close();

            //Get mute
            resultSet = mySQL.query("SELECT * FROM `Mute` WHERE `uuid` = '" + getPlayer().getUniqueId() + "' ;");
            if (resultSet.next()) {
                this.mute = new Mute(resultSet.getLong("startTime"),
                        resultSet.getLong("time"),
                        resultSet.getString("reason"),
                        resultSet.getString("staff"));
            }
            resultSet.close();


            //TODO Load home

        } catch (SQLException exception) {
            exception.printStackTrace();
        }


    }

    @Nullable
    public static CustomPlayer getCustomPlayer(String playerName) {
        for (CustomPlayer customPlayer : customPlayerList) {
            if (customPlayer.getName().equalsIgnoreCase(playerName))
                return customPlayer;
        }
        return null;
    }

    public void destroy() {
        customPlayerList.remove(this);
    }

    public String getName() {
        return name;
    }

    public Bank getBankAccount() {
        return bank;
    }

    public boolean isMute() {
        return this.mute != null && this.mute.isMute();
    }

    public void mute(int time, String reason, @NotNull Player staff) {
        this.mute = new Mute(System.currentTimeMillis(), time, reason, staff.getName());
    }

    public Mute getMute() {
        return mute;
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(this.name);
    }

    private boolean hasPlayedBefore() {

        MySQL mySQL = Histeria.mySQL;
        ResultSet result = mySQL.query("SELECT `id` FROM `Player` WHERE `uuid` = '" + Objects.requireNonNull(this.getPlayer()).getUniqueId() + "';");
        try {
            final boolean finalResult = result.next();
            result.close();
            return finalResult;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;

    }

    public int getID() {
        return id;
    }

    public void saveData() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {

            MySQL mySQL = Histeria.mySQL;
            mySQL.update("UPDATE `Player` SET `name` = '" + this.name + "',`kill` = " + this.kill + ",`death` = " + this.death + ", `lang` = '" + lang.name() + "' WHERE `id` = " + this.id + " ;");
            timePlayed += (int) ((System.currentTimeMillis() - lastConnection) / 1000.0);
            mySQL.update("UPDATE `Player` SET `playTime` = " + timePlayed + " WHERE `id` = " + this.id + ";");

            //Mute save data
            if (this.mute != null) {

                try {
                    ResultSet resultSet = mySQL.query("SELECT * FROM `Mute` WHERE `uuid` = '" + this.uuid + "';");

                    if (!resultSet.next()) {
                        mySQL.update("INSERT INTO `Mute` (`uuid`,`name`,`startTime`,`time`,`reason`,`staff`) VALUES ('"
                                + this.uuid + "','"
                                + this.name + "',"
                                + this.mute.getStartTime() + ","
                                + this.mute.getTime() + ",'"
                                + this.mute.getReason() + "','"
                                + this.mute.getStaffName() + "');");
                    } else if (isMute()) {
                        mySQL.update("UPDATE `Mute` SET `uuid` = '" + this.uuid +
                                "' ,`name` = '" + this.name +
                                "' ,`startTime` = " + this.mute.getStartTime() +
                                " ,`time` = " + this.mute.getTime() +
                                " ,`reason` = '" + this.mute.getReason() +
                                "' ,`staff` = '" + this.mute.getStaffName() + "' WHERE `uuid` = '" + this.uuid + "' ;");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });
        this.bank.save();
    }

    public int getKill() {
        return kill;
    }

    public int getDeath() {
        return death;
    }

    public void addDeath() {
        death += 1;
    }

    public void addKill() {
        kill += 1;
    }

    public int getTimePlayed() {
        return timePlayed;
    }

    public long getLastConnection() {
        return lastConnection;
    }

    public String getIp() {
        return ip;
    }

    public String getPrimConnection() {
        return primConnection;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }
}
