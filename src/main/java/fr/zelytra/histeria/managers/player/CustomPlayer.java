/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.player;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.commands.moderation.Ban.Ban;
import fr.zelytra.histeria.commands.moderation.Mute.Mute;
import fr.zelytra.histeria.managers.afk.Afk;
import fr.zelytra.histeria.managers.economy.Bank;
import fr.zelytra.histeria.managers.home.Home;
import fr.zelytra.histeria.managers.languages.Lang;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.pvp.PvP;
import fr.zelytra.histeria.managers.serverSynchro.PacketSender;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
    private static Object syncObject = new Object();

    private final String name;
    private int kill;
    private int death;
    private int timePlayed = 0;
    private long lastConnection;
    private String ip;
    private String primConnection;
    private Lang lang;
    private final String uuid;
    private int id;
    private boolean playedBefore = false;

    private final Bank bank;
    private Mute mute;
    private Ban ban;
    private Afk afk;
    private PvP pvp;
    private List<Home> homes;

    public CustomPlayer(Player player) {
        this.name = player.getName();
        this.bank = new Bank(this);
        this.uuid = player.getUniqueId().toString();
        this.afk = new Afk(player);
        this.pvp = new PvP(this);
        this.homes = new ArrayList<>();
        this.playedBefore = playedBeforeTask();


        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            /* Init first time */
            if (!playedBefore) {
                initData();
                this.id = getBaseID();
                this.bank.initNewAccount();
                LangMessage.broadcast(Message.PLAYER_PREFIX.getMsg(), "server.newPlayer", name);

            } else {
                this.id = getBaseID();
                this.loadData();
            }
            customPlayerList.add(this);

        });

    }

    /**
     * OffLine customPlayer loading SYNC
     *
     * @param name OfflinePlayerName
     */
    public CustomPlayer(String name) {
        this.name = name;
        this.bank = new Bank(this);
        this.uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();

        if (this.uuid == null) {
            playedBefore = false;
            return;
        }

        this.playedBefore = playedBeforeTask();
        this.homes = new ArrayList<>();

        if (playedBefore) {

            this.id = getBaseID();
            this.loadData();

            customPlayerList.add(this);
        }


    }


    public static List<CustomPlayer> getList() {
        return customPlayerList;
    }


    private void initData() {
        synchronized (syncObject) {
            MySQL mySQL = Histeria.mySQL;
            this.lang = Lang.EN;
            lastConnection = System.currentTimeMillis();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();

            mySQL.update("INSERT INTO `Player` (`uuid`,`name`,`ip`,`primconnection`,`lang`) VALUES ('"
                    + this.uuid + "','"
                    + getPlayer().getName() + "','"
                    + Objects.requireNonNull(getPlayer().getAddress()).toString().replace("/", "").split(":")[0] + "','"
                    + formatter.format(date) + "','"
                    + lang.name() + "');");

        }

    }

    private int getBaseID() {
        synchronized (syncObject) {
            try {
                MySQL mySQL = Histeria.mySQL;
                ResultSet result = mySQL.query("SELECT `id` FROM `Player` WHERE `uuid` = '" + this.uuid + "';");
                result.next();
                int id = result.getInt("id");
                result.close();
                return id;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return 0;
    }

    private void loadData() {
        synchronized (syncObject) {
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
                resultSet = mySQL.query("SELECT * FROM `Mute` WHERE `uuid` = '" + this.uuid + "' ;");
                if (resultSet.next()) {
                    this.mute = new Mute(resultSet.getLong("startTime"),
                            resultSet.getLong("time"),
                            resultSet.getString("reason"),
                            resultSet.getString("staff"));
                }
                resultSet.close();

                //Get ban
                resultSet = mySQL.query("SELECT * FROM `Ban` WHERE `uuid` = '" + this.uuid + "' ;");
                if (resultSet.next()) {
                    this.ban = new Ban(resultSet.getLong("startTime"),
                            resultSet.getLong("time"),
                            resultSet.getString("reason"),
                            resultSet.getString("staff"));
                }
                resultSet.close();

                //Load homes
                resultSet = mySQL.query("SELECT * FROM `Home` WHERE `uuid` = '" + this.uuid + "' ;");
                while (resultSet.next()) {

                    Location location = new Location(Bukkit.getWorld(resultSet.getString("world")), resultSet.getInt("x"), resultSet.getInt("y"), resultSet.getInt("z"));
                    this.homes.add(new Home(this, location, resultSet.getString("server"), resultSet.getString("name"), resultSet.getString("world")));

                }
                resultSet.close();


            } catch (SQLException exception) {
                exception.printStackTrace();
            }
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

    public boolean isBan() {
        return this.ban != null && this.ban.isBan();
    }

    public Ban getBan() {
        return ban;
    }

    public void ban(int time, String reason, @NotNull Player staff) {
        this.ban = new Ban(System.currentTimeMillis(), time, reason, staff.getName());
    }

    public boolean isAFK() {
        return afk.isAFK();
    }

    public PvP getPvp() {
        return pvp;
    }

    @Nullable
    public Player getPlayer() {
        return Bukkit.getPlayer(this.name);
    }

    public boolean hasPlayedBefore() {
        return playedBefore;
    }

    private boolean playedBeforeTask() {
        synchronized (syncObject) {
            MySQL mySQL = Histeria.mySQL;
            ResultSet result = mySQL.query("SELECT `id` FROM `Player` WHERE `uuid` = '" + this.uuid + "';");
            try {
                final boolean finalResult = result.next();
                result.close();
                return finalResult;
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return false;

    }

    public int getID() {
        return id;
    }

    public void saveData() {
        synchronized (syncObject) {
            if (!Histeria.isReloading && !Bukkit.getServer().isStopping()) {

                Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
                    saveDataTask();
                    this.bank.save();
                });

            } else {

                saveDataTask();
                this.bank.save();

            }

        }
    }

    private void saveDataTask() {
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

        //Ban save data
        if (this.ban != null) {

            try {
                ResultSet resultSet = mySQL.query("SELECT * FROM `Ban` WHERE `uuid` = '" + this.uuid + "';");

                if (!resultSet.next()) {
                    mySQL.update("INSERT INTO `Ban` (`uuid`,`name`,`startTime`,`time`,`reason`,`staff`) VALUES ('"
                            + this.uuid + "','"
                            + this.name + "',"
                            + this.ban.getStartTime() + ","
                            + this.ban.getTime() + ",'"
                            + this.ban.getReason() + "','"
                            + this.ban.getStaffName() + "');");
                } else if (isBan()) {
                    mySQL.update("UPDATE `Ban` SET `uuid` = '" + this.uuid +
                            "' ,`name` = '" + this.name +
                            "' ,`startTime` = " + this.ban.getStartTime() +
                            " ,`time` = " + this.ban.getTime() +
                            " ,`reason` = '" + this.ban.getReason() +
                            "' ,`staff` = '" + this.ban.getStaffName() + "' WHERE `uuid` = '" + this.uuid + "' ;");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Home save data
        mySQL.update("DELETE FROM `Home` WHERE `uuid` = '" + this.uuid + "';");
        for (Home home : homes) {
            mySQL.update("INSERT IGNORE INTO `Home` SET `uuid` = '" + this.uuid +
                    "' ,`name` = '" + home.getName() +
                    "' ,`x` = " + home.getLocation().getX() +
                    " ,`y` = " + home.getLocation().getY() +
                    " ,`z` = " + home.getLocation().getZ() +
                    " ,`world` = '" + home.getLocation().getWorld().getName() +
                    "' ,`server` = '" + home.getServerName() + "' ;");
        }
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

    public List<Home> getHomes() {
        return homes;
    }

    public Afk getAfk() {
        return this.afk;
    }

    public boolean wasAFK() {
        if (this.getAfk() == null)
            this.afk = new Afk(getPlayer());

        return this.afk.wasAFK(getPlayer());
    }

    public static void forceSaveAll() {

        for (Player player : Bukkit.getOnlinePlayers()) {

            CustomPlayer.getCustomPlayer(player.getName()).saveData();
            if (Histeria.synchro)
                new PacketSender(player).save();
        }

    }

    public static void forceLoadAll() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            new CustomPlayer(player);
        }

    }


}
