/*
 * Copyright (c) 2022.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.events.blocks.luckyBlock.listener;

import fr.zelytra.histeria.events.blocks.luckyBlock.builder.LuckyEvent;
import fr.zelytra.histeria.events.blocks.luckyBlock.event.*;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyPlace implements Listener {

    private final static Material luckyBlockType = CustomMaterial.LUCKY_BLOCK.getVanillaMaterial();
    private final static Random random = new Random();

    private final static List<LuckyEvent> luckyEventList = new ArrayList<>();
    private final static int outOf = 1000, origin = 1;

    {
        luckyEventList.add(new AnvilRain(42));
        luckyEventList.add(new AttackOnDado(60));
        luckyEventList.add(new BadPotions(42));
        luckyEventList.add(new BBQ(42));
        luckyEventList.add(new BlockSpreading(62));
        luckyEventList.add(new CakeLie(52));
        luckyEventList.add(new Foucault(1));
        luckyEventList.add(new FakeBedrock(32));
        luckyEventList.add(new GoodDogo(42));
        luckyEventList.add(new HisteriteIngot(32));
        luckyEventList.add(new Kaboom(22));
        luckyEventList.add(new LuckyBlockCeption(62));
        luckyEventList.add(new NocturiteCore(1));
        luckyEventList.add(new NotchWither(12));
        luckyEventList.add(new ObsidianTrap(62));
        luckyEventList.add(new Raid(22));
        luckyEventList.add(new SpiderTrap(62));
        luckyEventList.add(new VillagerHero(52));
        luckyEventList.add(new XPShower(32));
        luckyEventList.add(new Zaucisse(52));
        luckyEventList.add(new CuteFish(43));
        luckyEventList.add(new TnTShower(22));
        luckyEventList.add(new MLG(40));
        luckyEventList.add(new Shuffler(50));
        luckyEventList.add(new Nubert(1));
        luckyEventList.add(new OreShower(16));
    }

    @EventHandler
    public void onPlace(BlockBreakEvent e) {

        if (e.getBlock().getType() != luckyBlockType) return;

        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;

        //Checking silk touch enchant
        if (e.getPlayer().getItemInUse() != null) {
            if (e.getPlayer().getItemInUse().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
                return;
        }

        e.setDropItems(false);

        int draw = random.nextInt(origin, outOf);

        //Executing draw and event
        for (LuckyEvent event : luckyEventList) {
            if (draw <= event.getLuck()) {
                event.run(e);
                break;
            }

            draw -= event.getLuck();
        }
    }

    @EventHandler
    public void placeLucky(BlockPlaceEvent e) {
        if (e.getBlock().getType() == luckyBlockType) {
            if (e.getBlock().getLocation().getY() <= 15) {
                e.getPlayer().sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou cannot place LuckyBlock under layer 15");
                e.setCancelled(true);
            } else if (!e.getPlayer().getWorld().getName().equalsIgnoreCase("world")) {
                e.getPlayer().sendMessage(Message.PLAYER_PREFIX.getMsg() + "§cYou cannot place LuckyBlock in this world");
                e.setCancelled(true);
            }
        }
    }
}
