package fr.zelytra.histeria.managers.enchants.builder;

/**
 * @param startWindow    Minimal value of the draw window at lvl 1
 * @param windowSize     Max size of the window
 * @param weight         Weight of draw between all other custom enchants (higher is the weight higher is the luck to draw)
 * @param drawSaturation Maximum value at the draw windows at the maximum level
 */
public record EnchantRarity(int startWindow, int windowSize, int weight, int drawSaturation) {

    public int getMaxWindows() {
        return startWindow + windowSize;
    }

}
