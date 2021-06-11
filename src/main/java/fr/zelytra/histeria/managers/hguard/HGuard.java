package fr.zelytra.histeria.managers.hguard;

import fr.zelytra.histeria.managers.items.CustomMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HGuard {
    private static List<HGuard> HGuardList = new ArrayList<>();

    private final String name;
    private BoundingBox area;
    private Location center;
    private int radius;
    private final Shape shape;
    private final World world;

    private int priorityLevel;
    private List<CustomMaterial> customItemWhiteList;
    private List<String> groupWhiteList;
    private List<Material> interactWhiteList;

    private boolean placeBlock;
    private boolean breakBlock;
    private boolean pvp;

    /**
     * @param name   Area name (must be unique)
     * @param center Center of the area
     * @param shape  The shape of the area
     * @param world  The world link to the area
     */

    public HGuard(String name, Location center, Shape shape, World world, int radius) {
        this.name = name;
        this.center = center;
        this.radius = radius;
        this.shape = shape;
        this.world = world;
        this.priorityLevel = 0;
        this.customItemWhiteList = new ArrayList<>();
        this.groupWhiteList = new ArrayList<>();
        this.interactWhiteList = new ArrayList<>();
        this.placeBlock = false;
        this.breakBlock = false;
        this.pvp = false;
        HGuardList.add(this);
    }

    public HGuard(String name, BoundingBox box, Shape shape, World world) {
        this.name = name;
        this.area = box;
        this.shape = shape;
        this.world = world;
        this.priorityLevel = 0;
        this.customItemWhiteList = new ArrayList<>();
        this.groupWhiteList = new ArrayList<>();
        this.interactWhiteList = new ArrayList<>();
        this.placeBlock = false;
        this.breakBlock = false;
        this.pvp = false;
        HGuardList.add(this);
    }

    public static List<HGuard> getList() {
        return HGuardList;
    }

    @Nullable
    public static HGuard getByName(String name) {
        for (HGuard area : HGuardList) {
            if (area.getName().equalsIgnoreCase(name))
                return area;
        }
        return null;
    }

    @Nullable
    public static HGuard getByLocation(Location location) {
        HGuard highestPriorityArea = null;
        for (HGuard hguard : HGuardList) {
            if (hguard.contains(location) && location.getWorld().getName().equalsIgnoreCase(hguard.getWorld().getName())) {
                if (highestPriorityArea != null) {

                    if (highestPriorityArea.getPriorityLevel() > hguard.getPriorityLevel())
                        continue;

                } else
                    highestPriorityArea = hguard;
            }

        }
        return highestPriorityArea;
    }

    private boolean contains(Location location) {
        switch (this.shape) {

            case CUBE:
                return location.getBlockX() >= area.getMinX() && location.getBlockX() <= area.getMaxX()
                        && location.getBlockY() >= area.getMinY() && location.getBlockY() <= area.getMaxY()
                        && location.getBlockZ() >= area.getMinZ() && location.getBlockZ() <= area.getMaxZ();

            case CYLINDER:
                double deltaX = center.getBlockX() - location.getBlockX();
                double deltaZ = center.getBlockZ() - location.getBlockZ();
                double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

                return distance <= radius;

            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "§8---------------§6[ HGuard ] §8---------------" + "\n" +
                "§8● §6Name: §f" + this.name + "\n" +
                "§8● §6Shape: §f" + this.shape + "\n" +
                "§8● §6World: §f" + this.world.getName() + "\n" +
                "§8● §6Priority level: §f" + this.priorityLevel + "\n" +
                "§8● §6CustomItem (WL): §f" + Arrays.toString(this.customItemWhiteList.toArray()) + "\n" +
                "§8● §6Groupe (WL): §f" + Arrays.toString(this.groupWhiteList.toArray()) + "\n" +
                "§8● §6Interact (WL): §f" + Arrays.toString(this.interactWhiteList.toArray()) + "\n" +
                "§8● §6PlaceBlock: §f" + (this.placeBlock ? "§aTRUE" : "§cFALSE") + "\n" +
                "§8● §6BreakBlock: §f" + (this.breakBlock ? "§aTRUE" : "§cFALSE") + "\n" +
                "§8● §6PVP: §f" + (this.pvp ? "§aTRUE" : "§cFALSE") + "\n" + "\n";
    }

    public void delete(){
        HGuardList.remove(this);
    }


    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public List<CustomMaterial> getCustomItemWhiteList() {
        return customItemWhiteList;
    }

    public List<String> getGroupWhiteList() {
        return groupWhiteList;
    }

    public List<Material> getInteractWhiteList() {
        return interactWhiteList;
    }

    public boolean canPlaceBlock() {
        return placeBlock;
    }

    public boolean canBreakBlock() {
        return breakBlock;
    }

    public boolean isPvp() {
        return pvp;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public void setCustomItemWhiteList(List<CustomMaterial> customItemWhiteList) {
        this.customItemWhiteList = customItemWhiteList;
    }

    public void setGroupWhiteList(List<String> groupWhiteList) {
        this.groupWhiteList = groupWhiteList;
    }

    public void setInteractWhiteList(List<Material> interactWhiteList) {
        this.interactWhiteList = interactWhiteList;
    }

    public void setPlaceBlock(boolean placeBlock) {
        this.placeBlock = placeBlock;
    }

    public void setBreakBlock(boolean breakBlock) {
        this.breakBlock = breakBlock;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }
}
