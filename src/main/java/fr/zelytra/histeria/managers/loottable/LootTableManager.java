package fr.zelytra.histeria.managers.loottable;


public class LootTableManager {
    private LootTable CCrusher;

    public LootTableManager() {
        lootTableInit();
    }

    public LootTable getByName(String name) {

        return null;
    }

    private void lootTableInit() {
        cobbleStoneCrusher();
    }

    private void cobbleStoneCrusher() {
        CCrusher = new LootTable("CCrusher");
        CCrusher.addLoot(LootsEnum.CC_DIAMOND);
        CCrusher.addLoot(LootsEnum.CC_OBSIDIAN);
        CCrusher.addLoot(LootsEnum.CC_IRON);
        CCrusher.addLoot(LootsEnum.CC_GOLD);
        CCrusher.addLoot(LootsEnum.CC_HISTERITE);

    }

    public LootTable getCCrusher() {
        return CCrusher;
    }
}
