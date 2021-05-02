package fr.zelytra.histeria.events.blocks;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import fr.zelytra.histeria.Histeria;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.items.CustomMaterial;
import fr.zelytra.histeria.utils.Message;
import net.minecraft.server.v1_16_R3.EntityPufferFish;
import net.minecraft.server.v1_16_R3.EntitySpider;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombie;
import org.bukkit.*;
import org.bukkit.block.Barrel;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.io.InputStream;

public class LuckyBlock implements Listener {
    static private int count;

    @EventHandler
    public void placeLucky(BlockPlaceEvent e){
        if(e.getBlock().getType()==CustomMaterial.LUCKY_BLOCK.getVanillaMaterial()){
            if(e.getBlock().getLocation().getY()<=15){
                e.getPlayer().sendMessage(Message.getPlayerPrefixe()+"§cYou cannot place LuckyBlock under layer 15");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void luckyBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() != CustomMaterial.LUCKY_BLOCK.getVanillaMaterial() /*|| e.getPlayer().getGameMode() != GameMode.SURVIVAL*/) {
            return;
        }

        if ((!e.getPlayer().getInventory().getItemInMainHand().getEnchantments().isEmpty())) {
            if (e.getPlayer().getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH))
                return;
        }

        e.setCancelled(true);
        e.getBlock().setType(Material.AIR);
        int rand = (int) (Math.random() * (1000 - 1) + 1);

        // Nocturite Core
        if (rand <= 1) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new CustomItemStack(CustomMaterial.NOCTURITE_CORE, 1).getItem());

        } // 50/50
        else if (rand == 2) {
            ItemStack[] playerInv = e.getPlayer().getInventory().getContents();
            for (ItemStack itemStack : playerInv) {
                if (itemStack == null) {
                    continue;
                }
                int randInv = (int) (Math.random() * (100 - 1) + 1);
                if (randInv <= 50) {
                    itemStack.setAmount(0);
                }
            }
            e.getPlayer().sendMessage("§9[§bJean-Pierre Foucault§9]§b You choose the 50/50 !");
        }
        // Norch apple
        else if (rand <= 14) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.WITHER_SKELETON_SKULL));
        } // Instant explosion/kill
        else if (rand <= 36) {
            e.getPlayer().sendMessage("§cKABOOOOOOM");
            e.getPlayer().getWorld().createExplosion(e.getBlock().getLocation(), 20);
        } // What a raid !
        else if (rand <= 58) {
            for (int x = 0; x <= 10; x++) {
                e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.VINDICATOR);
            }
            for (int x = 0; x <= 4; x++) {
                e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.RAVAGER);
            }
            for (int x = 0; x <= 5; x++) {
                e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.PILLAGER);
            }
            for (int x = 0; x <= 2; x++) {
                e.getPlayer().getWorld().spawnEntity(e.getBlock().getLocation(), EntityType.EVOKER);
            }
            e.getPlayer().sendMessage("§dGLHF boy !");
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(e.getBlock().getLocation(), Sound.EVENT_RAID_HORN, 1000, 1);
            }
        } // XP Shower
        else if (rand <= 90) {
            e.getPlayer().playSound(e.getBlock().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            e.getPlayer().setLevel(e.getPlayer().getLevel() + 50);
            e.getPlayer().sendMessage("§aYou feel lucky today !");

            // Bedrock
        } else if (rand <= 122) {
            ItemStack item = new ItemStack(Material.COBBLESTONE);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName("§fBedrock");
            }
            meta.setCustomModelData(17);
            item.setItemMeta(meta);
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), item);

        } // All bad potion effect
        else if (rand <= 164) {
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SPLASH_POTION_BREAK, 10, 1);
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 30, 0, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 600, 1, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 600000, 3, true, true, true));
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 700, 1, true, true, true));

        } // Drop Histerite Ingot
        else if (rand <= 206) {
            ItemStack drop = new CustomItemStack(CustomMaterial.HISTERITE_INGOT, 1).getItem();
            drop.setAmount((int) (Math.random() * (9) + 1));
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), drop);

        } // Heroe of the village
        else if (rand <= 258) {
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 999999, (int) (Math.random() * (4) + 1), false, false, true));
            e.getPlayer().sendMessage("§aYou are a hero today ! (c fo)");

        } // Glados gato
        else if (rand <= 310) {
            e.getBlock().setType(Material.CAKE);
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 2, false, false, true));
            Location fLocation = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX() + 0.5, e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ() + 0.5);
            Firework fw = (Firework) e.getBlock().getWorld().spawnEntity(fLocation, EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
            fwm.setPower(20);
            fwm.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).flicker(true).build());
            fw.setFireworkMeta(fwm);
            fw.detonate();
            fw = (Firework) e.getBlock().getWorld().spawnEntity(fLocation, EntityType.FIREWORK);
            fwm = fw.getFireworkMeta();

            fwm.setPower(20);
            fwm.addEffect(FireworkEffect.builder().withColor(Color.BLUE).flicker(true).build());

            fw.setFireworkMeta(fwm);
            fw.detonate();
            Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> e.getPlayer().sendMessage("§9[§6GLaDOS§9] §fOh... it's not a deer."), 25);

        } // Foir § la zaucisse
        else if (rand <= 362) {
            Player player = e.getPlayer();
            count = 0;
            for (int x = 0; x <= 150; x++) {
                Bukkit.getScheduler().runTaskLater(Histeria.getInstance(), () -> {

                    double randomx = (Math.random() * (10.0 + 10.0 + 1)) - 10.0;
                    double randomy = (Math.random() * (10.0));
                    double randomz = (Math.random() * (10.0 + 10.0 + 1)) - 10.0;

                    Location Location1 = new Location(e.getBlock().getWorld(),
                            e.getBlock().getLocation().getX() + randomx,
                            e.getBlock().getLocation().getY() + randomy,
                            e.getBlock().getLocation().getZ() + randomz);

                    Firework fw = (Firework) e.getBlock().getWorld().spawnEntity(Location1, EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();

                    fwm.setPower(20);
                    fwm.addEffect(FireworkEffect.builder().withColor(Color.fromRGB((int) (Math.random() * (16000000) + 1))).flicker(true).build());
                    fw.setFireworkMeta(fwm);
                    fw.detonate();

                    if (count == 1) {
                        player.sendMessage("§dWhat a Ramdam ! Like the French like to say");
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    if (count == 10) {
                        player.sendMessage("§9§kH§r§bI wish you a very merry Unbirthday !§9§kH");
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    if (count == 25) {
                        player.sendTitle("§cH§6a§2p§dp§by §cN§6e§2w §dY§9e§ca§6r §9§2!", null, 1, 18, 1);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    if (count == 45) {
                        player.sendTitle("§a...", null, 1, 18, 1);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    if (count == 65) {
                        player.sendTitle("§aWait...", null, 1, 18, 1);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    if (count == 85) {
                        player.sendTitle("§aNo...", null, 1, 18, 1);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    if (count == 105) {
                        player.sendTitle("§aOr maybe yes,", "§anever know when you open this block ^^", 1, 50, 1);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 5, 1);
                    }
                    count++;

                }, x + 10);

            }

        } // Attack on Dado
        else if (rand <= 414) {
            CraftWorld world = (CraftWorld) e.getBlock().getWorld();
            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setOwningPlayer(Bukkit.getOfflinePlayer("Dadodasyra"));
            item.setItemMeta(skull);

            EntityZombie customDado = new EntityZombie(EntityTypes.ZOMBIE, world.getHandle());
            customDado.setPosition(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(),
                    e.getBlock().getLocation().getZ());
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setBoots(new CustomItemStack(CustomMaterial.HISTERITE_BOOTS, 1).getItem());
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setLeggings(new CustomItemStack(CustomMaterial.HISTERITE_LEGGINGS, 1).getItem());
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setChestplate(new CustomItemStack(CustomMaterial.HISTERITE_CHESTPLATE, 1).getItem());
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setHelmet(item);
            ((LivingEntity) customDado.getBukkitEntity()).setCustomName("§cdadodasyra");
            ((LivingEntity) customDado.getBukkitEntity()).setCustomNameVisible(true);
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setItemInMainHand(new CustomItemStack(CustomMaterial.HISTERITE_SWORD, 1).getItem());
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setBootsDropChance(0);
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setLeggingsDropChance(0);
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setChestplateDropChance(0);
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setHelmetDropChance((float) 0.01);
            ((LivingEntity) customDado.getBukkitEntity()).getEquipment().setItemInMainHandDropChance(0);

            world.getHandle().addEntity(customDado);

        } // Lucky BlockCeption
        else if (rand <= 474) {
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.HONEYCOMB_BLOCK));
            e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.HONEYCOMB_BLOCK));
            e.getPlayer().sendMessage("§6Cut off one head, two more will grow back in its place...");
            e.getPlayer().playSound(e.getBlock().getLocation(), Sound.ENTITY_VILLAGER_HURT, 5, 1);

        } // Chest bon toutou
        else if (rand <= 536) {
            e.getBlock().setType(Material.BARREL);
            Barrel barrel = (Barrel) e.getBlock().getState();
            ItemStack[] contents = barrel.getInventory().getContents();

            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta skull = (SkullMeta) item.getItemMeta();
            skull.setDisplayName("§eMdr je suis fonda (c fo)");
            skull.setOwningPlayer(Bukkit.getOfflinePlayer("dadodasyra"));
            item.setItemMeta(skull);

            ItemStack bone = new ItemStack(Material.BONE, 1);
            ItemMeta meta = bone.getItemMeta();
            meta.setDisplayName("Bon toutou");
            bone.setItemMeta(meta);

            contents[13] = new ItemStack(item);
            contents[11] = new ItemStack(bone);
            contents[15] = new ItemStack(bone);

            barrel.getInventory().setContents(contents);

        } // BBQ
        else if (rand <= 598) {
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "BBC_layer1", e.getBlock().getX() - 6, e.getBlock().getY(), e.getBlock().getZ() - 6);
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "BBC_layer2", e.getBlock().getX() - 6, e.getBlock().getY(), e.getBlock().getZ() - 6);
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "BBC_layer3", e.getBlock().getX() - 6, e.getBlock().getY(), e.getBlock().getZ() - 6);
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "BBC_layer4", e.getBlock().getX() - 6, e.getBlock().getY(), e.getBlock().getZ() - 6);
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "BBC_layer5", e.getBlock().getX() - 6, e.getBlock().getY(), e.getBlock().getZ() - 6);

            Location location = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX() + 1, e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());
            e.getPlayer().sendMessage("§cWant a sausage ?");
            e.getPlayer().teleport(location);
        } // ObsiTrap
        else if (rand <= 660) {
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "obsiTrap", e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());
            e.getPlayer().sendMessage("§9blblblbl blbl blblblbllbl");
        } // CobwebTrap
        else if (rand <= 722) {
            CraftWorld world = (CraftWorld) e.getBlock().getWorld();
            for (int x = 0; x < 3; x++) {
                EntitySpider spider = new EntitySpider(EntityTypes.CAVE_SPIDER, world.getHandle());
                spider.setPosition(e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());
                world.getHandle().addEntity(spider);
            }
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "cobweb", e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());

        } // Block spreading
        else if (rand <= 784) {
            Material[] table = new Material[]{Material.ACACIA_PLANKS, Material.WARPED_PLANKS, Material.GRAVEL,
                    Material.DIRT, Material.DIORITE, Material.DARK_OAK_LEAVES, Material.COBWEB, Material.PURPLE_WOOL,
                    Material.BOOKSHELF, Material.SNOW_BLOCK, Material.CRACKED_STONE_BRICKS, Material.WHITE_TERRACOTTA,
                    Material.CUT_RED_SANDSTONE};
            Location BLocation = new Location(e.getBlock().getWorld(), e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY() - 1, e.getBlock().getLocation().getZ());
            int range = 9;
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    final int fx = x;
                    final int fz = z;
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Histeria.getInstance(), () -> {
                        BLocation.setX(e.getBlock().getLocation().getX() + fx);
                        BLocation.setZ(e.getBlock().getLocation().getZ() + fz);
                        int random = (int) (Math.random() * (100 - 1) + 1);
                        int block = (int) (Math.random() * (table.length - 1) + 1);
                        if (random <= 40) {
                            BLocation.getBlock().setType(table[block]);
                        }
                    }, (x + z + 50));
                }
            }
        } // Anvil rain
        else if (rand <= 856) {
            Location BLocation = new Location(e.getBlock().getWorld(), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ());
            WEgenerate(BukkitAdapter.adapt(e.getBlock().getWorld()), "AnvilTrap", BLocation.getX(), BLocation.getY(), BLocation.getZ());
            int range = 33;
            for (int y = 30; y < range; y++) {
                BLocation.setY(e.getPlayer().getLocation().getY() + y);
                BLocation.getBlock().setType(Material.ANVIL);
            }

        } // PufferFish
        else if (rand <= 1000) {
            CraftWorld world = (CraftWorld) e.getBlock().getWorld();
            EntityPufferFish fish = new EntityPufferFish(EntityTypes.PUFFERFISH, world.getHandle());
            ((LivingEntity) fish.getBukkitEntity()).setCustomName("Give me a hug");
            fish.setPosition(e.getBlock().getLocation().getX(), e.getBlock().getLocation().getY(), e.getBlock().getLocation().getZ());
            world.getHandle().addEntity(fish);

            e.getPlayer().sendMessage("§dHoooooo so cute... §c§lWAIT WHAT ?!");
        }

    }

    private void WEgenerate(World world, String fileName, double locX, double locY, double locZ) {

        // Load the file selected
        InputStream is = Histeria.getInstance().getResource("luckyBlock/" + fileName + ".schem");
        BuiltInClipboardFormat format = BuiltInClipboardFormat.SPONGE_SCHEMATIC;
        Clipboard clipboard = null;
        try (ClipboardReader reader = format.getReader(is)) {
            clipboard = reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(world, -1)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(locX, locY, locZ))
                    .ignoreAirBlocks(true)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }
}
