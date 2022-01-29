package fr.zelytra.histeria.managers.jobs.visual;

import fr.zelytra.histeria.builder.guiBuilder.CustomGUI;
import fr.zelytra.histeria.builder.guiBuilder.InterfaceBuilder;
import fr.zelytra.histeria.builder.guiBuilder.VisualItemStack;
import fr.zelytra.histeria.builder.guiBuilder.VisualType;
import fr.zelytra.histeria.managers.items.CustomItemStack;
import fr.zelytra.histeria.managers.jobs.builder.JobInterface;
import fr.zelytra.histeria.managers.jobs.builder.JobType;
import fr.zelytra.histeria.managers.jobs.command.JobMenuCommand;
import fr.zelytra.histeria.managers.player.CustomPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class JobVisualListener implements Listener {

    @EventHandler
    public void onInterfaceClick(InventoryClickEvent e) {

        if (!(e.getInventory().getHolder() instanceof CustomGUI)) return;

        if (e.getView().getTitle().equals(JobMenuCommand.menuName + JobPage.MENU.pageName)) {
            if (e.getCurrentItem() == null) return;

            e.setCancelled(true);

            CustomPlayer player = CustomPlayer.getCustomPlayer(e.getWhoClicked().getName());
            if (player == null) return;

            InterfaceBuilder builder = new InterfaceBuilder(54, JobMenuCommand.menuName + JobPage.PROGRESSION.pageName);
            builder.setContent(getProgressContent(player.getJob(JobType.get(CustomItemStack.getCustomMaterial(e.getCurrentItem())))));
            builder.open(player.getPlayer());

        } else if (e.getView().getTitle().equals(JobMenuCommand.menuName + JobPage.PROGRESSION.pageName)) {

            if (e.getCurrentItem() == null) return;
            e.setCancelled(true);

            CustomPlayer player = CustomPlayer.getCustomPlayer(e.getWhoClicked().getName());
            if (player == null) return;

            if (e.getCurrentItem().getType() == Material.BARRIER) {

                InterfaceBuilder builder = new InterfaceBuilder(9, JobMenuCommand.menuName + JobPage.MENU.pageName);
                builder.setContent(JobMenuCommand.getMenuContent(player));
                builder.open(player.getPlayer());

            }

        }


    }

    private int[] interfaceOrder = new int[]{9, 10, 19, 28, 37, 38, 39, 30, 21, 12, 13, 14, 23, 32, 41, 42, 43, 34, 25, 16};

    private ItemStack[] getProgressContent(JobInterface job) {
        ItemStack[] content = new ItemStack[54];

        for (int x = 0; x < content.length; x++)
            content[x] = VisualType.BLANK_BLACK_GLASSE.getItem();

        content[4] = job.getItemMenu().getItem();
        content[49] = new VisualItemStack(Material.BARRIER, "§6Back", false).getItem();

        // Generating progress glass display
        int countLevel = 1;
        int levelSpace = 5;

        for (int x : interfaceOrder) {

            Material glass;

            if (job.getLevel() >= levelSpace * countLevel && job.getLevel() < levelSpace * (countLevel + 1))
                glass = Material.ORANGE_STAINED_GLASS_PANE;

            else if (job.getLevel() >= levelSpace * countLevel)
                glass = Material.LIME_STAINED_GLASS_PANE;

            else glass = Material.RED_STAINED_GLASS_PANE;

            content[x] = new VisualItemStack(glass,
                    "§6Level §e" + levelSpace * countLevel,
                    false,
                    "§6Reward",
                    "§e" + job.getReward(levelSpace * countLevel)).getItem();
            countLevel++;
        }


        return content;
    }

}
