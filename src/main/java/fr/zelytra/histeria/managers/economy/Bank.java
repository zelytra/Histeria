/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.economy;

import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.mysql.MySQL;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import fr.zelytra.histeria.managers.visual.chat.Emote;
import fr.zelytra.histeria.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class Bank {

    private CustomPlayer player;
    private int money;

    public Bank(CustomPlayer player) {
        this.player = player;
        this.money = 3000;

    }

    public void initNewAccount() {
        Bukkit.getScheduler().runTaskAsynchronously(Histeria.getInstance(), () -> {
            MySQL mySQL = Histeria.mySQL;
            mySQL.update("INSERT INTO `Bank` (id,uuid,name) VALUES (" + player.getID() + ",'" + player.getPlayer().getUniqueId() + "','" + player.getName() + "');");
        });
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public boolean hasEnoughFor(int money) {
        return this.money >= money;
    }

    public void takeMoney(int money) {
        this.money -= money;
        if (this.money < 0)
            this.money = 0;
    }

    public void save() {
        MySQL mySQL = Histeria.mySQL;
        mySQL.update("UPDATE `Bank` SET `money` = " + this.money + " WHERE `id` = " + player.getID() + ";");
    }

    public ItemStack getHead() {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) head.getItemMeta();

        skull.setDisplayName("§b" + player.getName());

        ArrayList<String> lore = new ArrayList<>();
        lore.add("§6" + Utils.formatBigNumber(money) + " §f" + Emote.GOLD);

        skull.setLore(lore);
        skull.setOwningPlayer(player.getPlayer());
        head.setItemMeta(skull);
        return head;
    }
}
