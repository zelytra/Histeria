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
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.content.Enchanter;
import fr.zelytra.histeria.managers.jobs.content.Farmer;
import fr.zelytra.histeria.managers.jobs.content.Fighter;
import fr.zelytra.histeria.managers.jobs.content.Miner;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.listener.JobUtils;
import fr.zelytra.histeria.managers.languages.Lang;
import fr.zelytra.histeria.managers.languages.LangMessage;
import fr.zelytra.histeria.managers.logs.LogType;
import fr.zelytra.histeria.managers.logs.discord.DiscordLog;
import fr.zelytra.histeria.managers.logs.discord.WebHookType;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.pvp.PvP;
import fr.zelytra.histeria.managers.serverSynchro.server.Request;
import fr.zelytra.histeria.managers.serverSynchro.server.SyncServer;
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
    private static final Object syncObject = new Object();

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
    private final boolean playedBefore;

    private Bank bank;
    private Mute mute;
    private Ban ban;
    private Afk afk;
    private PvP pvp;
    private List<Home> homes;
    private List<JobInterface> jobs;

    public CustomPlayer(Player player) {
        this.name = player.getName();
        this.bank = new Bank(this);
        this.uuid = player.getUniqueId().toString();
        this.ip = player.getAddress().toString().replace("/", "").split(":")[0];
        this.afk = new Afk(player);
        this.pvp = new PvP(this);
        this.homes = new ArrayList<>();
        this.jobs = new ArrayList<>();
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
            dcChecker();
            customPlayerList.add(this);

        });

    }

    private void dcChecker() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            try {
                ResultSet result = Histeria.mySQL.query("SELECT `ip`,`name` FROM `Player` WHERE `ip` = '" + this.ip + "';");
                int count = 0;
                StringBuilder linkedAccount = new StringBuilder();

                while (result.next()) {
                    linkedAccount.append(result.getString("name") + " ");
                    count++;
                }

                if (count > 1) {
                    Histeria.log("DC detected on player " + name + " | IP: " + ip + " | Linked account: " + linkedAccount, LogType.WARN);
                    new DiscordLog(WebHookType.CHEATER, "DC detected on player **" + name + "** | IP: **" + ip + "** | Linked account: " + linkedAccount);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * OffLine customPlayer loading SYNC
     *
     * @param name OfflinePlayerName
     */
    public CustomPlayer(String name) {
        this.name = name;
        this.uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
        this.playedBefore = playedBeforeTask();

        if (!playedBefore)
            return;

        this.bank = new Bank(this);
        this.homes = new ArrayList<>();
        this.id = getBaseID();
        this.loadData();

        customPlayerList.add(this);


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

            jobs.add(new Miner(0, 0));
            jobs.add(new Farmer(0, 0));
            jobs.add(new Fighter(0, 0));
            jobs.add(new Enchanter(0, 0));

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
                    Histeria.log("Failed to load bank account of " + this.name, LogType.ERROR);
                    Objects.requireNonNull(getPlayer()).sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cFailed to load bank account please try to reconnect to the server or contact an admin if the problem persist.");
                }

                resultSet = mySQL.query("SELECT * FROM `Player` WHERE `id` =" + this.id + ";");
                if (resultSet.next()) {

                    this.kill = resultSet.getInt("kill");
                    this.death = resultSet.getInt("death");
                    this.timePlayed = resultSet.getInt("playTime");
                    this.primConnection = resultSet.getString("primconnection");
                    this.lang = Lang.valueOf(resultSet.getString("lang"));

                    String ip = resultSet.getString("ip");
                    if (this.ip != null && !ip.equalsIgnoreCase(this.ip)) {
                        Histeria.log(".IP change detected for " + name + " | Previous IP: " + ip + " | new IP: " + this.ip, LogType.WARN);
                        new DiscordLog(WebHookType.CHEATER, ".IP change detected for **" + name + "** | Previous IP: **" + ip + "** | new IP: **" + this.ip + "**");
                    }

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

                //Load jobInterfaces
                resultSet = mySQL.query("SELECT * FROM `Jobs` WHERE `uuid` = '" + this.uuid + "' ;");
                while (resultSet.next()) {

                    switch (resultSet.getString("type")) {

                        case "MINER":
                            jobs.add(new Miner(resultSet.getInt("level"), resultSet.getDouble("experience")));
                            break;
                        case "FARMER":
                            jobs.add(new Farmer(resultSet.getInt("level"), resultSet.getDouble("experience")));
                            break;
                        case "ENCHANTER":
                            jobs.add(new Enchanter(resultSet.getInt("level"), resultSet.getDouble("experience")));
                            break;
                        case "FIGHTER":
                            jobs.add(new Fighter(resultSet.getInt("level"), resultSet.getDouble("experience")));
                            break;

                    }

                }
                //Completing job init if already not
                jobs = JobUtils.completeJobList(jobs);


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

    public String getUuid() {
        return uuid;
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
        mySQL.update("UPDATE `Player` SET `name` = '" + this.name
                + "',`kill` = " + this.kill
                + ",`death` = " + this.death
                + ", `lang` = '" + lang.name()
                + "', `ip` = '" + this.ip
                + "' WHERE `id` = " + this.id + " ;");

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
                ResultSet resultSet = mySQL.query("SELECT `uuid` FROM `Ban` WHERE `uuid` = '" + this.uuid + "';");

                if (!resultSet.next() && isBan()) {

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

        //Jobs save data
        mySQL.update("DELETE FROM `Jobs` WHERE `uuid` = '" + this.uuid + "';");
        for (JobInterface jobInterface : jobs) {
            mySQL.update("INSERT IGNORE INTO `Jobs` SET " +
                    "`uuid` = '" + this.uuid +
                    "' ,`type` = '" + jobInterface.getJob().name() +
                    "' ,`level` = " + jobInterface.getLevel() +
                    " ,`experience` = " + jobInterface.getXP() + ";");
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

    public JobInterface getJob(JobType jobType) {
        System.out.println(jobs.size());
        for (JobInterface job : jobs) {
            System.out.println(job.getJob());
            if (job.getJob() == jobType)
                return job;
        }
        return null;
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
                new SyncServer(player, Request.SEND).execute();

        }

    }

    public static void forceLoadAll() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            new CustomPlayer(player);
        }

    }


}
