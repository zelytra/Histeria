/*
 * Copyright (c) 2021.
 * Made by Zelytra :
 *  - Website : https://zelytra.fr
 *  - GitHub : http://github.zelytra.fr
 *
 * All right reserved
 */

package fr.zelytra.histeria.managers.loottable;


public class LootTableManager {
    private LootTable CCrusher;
    private LootTable voteKey;
    private LootTable diamondKey;
    private LootTable histeriteKey;
    private LootTable nocturiteKey;

    public LootTableManager() {
        lootTableInit();
    }

    public LootTable getByName(String name) {

        return null;
    }

    private void lootTableInit() {
        cobbleStoneCrusher();
        voteKey();
        diamondKey();
        histeriteKey();
        nocturiteKey();
    }

    private void cobbleStoneCrusher() {
        CCrusher = new LootTable("CCrusher");
        CCrusher.addLoot(LootsEnum.CC_DIAMOND);
        CCrusher.addLoot(LootsEnum.CC_OBSIDIAN);
        CCrusher.addLoot(LootsEnum.CC_IRON);
        CCrusher.addLoot(LootsEnum.CC_GOLD);
        CCrusher.addLoot(LootsEnum.CC_HISTERITE);

    }

    private void voteKey() {
        voteKey = new LootTable("VoteKey");
        voteKey.addLoot(LootsEnum.VOTEK_JUMP_STICK);
        voteKey.addLoot(LootsEnum.VOTEK_SPEED_STICK);
        voteKey.addLoot(LootsEnum.VOTEK_HEAL_STICK);
        voteKey.addLoot(LootsEnum.VOTEK_BUBBLE_WAND);
        voteKey.addLoot(LootsEnum.VOTEK_COMPRESS_COBBLESTONE);
        voteKey.addLoot(LootsEnum.VOTEK_IRON_INGOT);
        voteKey.addLoot(LootsEnum.VOTEK_DIAMOND);
        voteKey.addLoot(LootsEnum.VOTEK_OBSIDIAN);
        voteKey.addLoot(LootsEnum.VOTEK_HISTERITE_NUGGET);

    }

    private void diamondKey() {
        diamondKey = new LootTable("DiamondKey");
        diamondKey.addLoot(LootsEnum.DIAMONDK_JUMP_STICK);
        diamondKey.addLoot(LootsEnum.DIAMONDK_SPEED_STICK);
        diamondKey.addLoot(LootsEnum.DIAMONDK_HEAL_STICK);
        diamondKey.addLoot(LootsEnum.DIAMONDK_BUBBLE_WAND);
        diamondKey.addLoot(LootsEnum.DIAMONDK_COMPRESS_COBBLESTONE);
        diamondKey.addLoot(LootsEnum.DIAMONDK_IRON_INGOT);
        diamondKey.addLoot(LootsEnum.DIAMONDK_DIAMOND);
        diamondKey.addLoot(LootsEnum.DIAMONDK_OBSIDIAN);
        diamondKey.addLoot(LootsEnum.DIAMONDK_HISTERITE_INGOT);

    }

    private void histeriteKey() {
        histeriteKey = new LootTable("HisteriteKey");
        histeriteKey.addLoot(LootsEnum.HISTERITEK_JUMP_STICK);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_SPEED_STICK);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_HEAL_STICK);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_BUBBLE_WAND);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_COMPRESS_COBBLESTONE);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_IRON_INGOT);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_DIAMOND);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_OBSIDIAN);
        histeriteKey.addLoot(LootsEnum.HISTERITEK_HISTERITE_INGOT);

    }

    private void nocturiteKey() {
        nocturiteKey = new LootTable("NocturiteKey");
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_JUMP_STICK);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_SPEED_STICK);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_HEAL_STICK);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_BUBBLE_WAND);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_COMPRESS_COBBLESTONE);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_IRON_INGOT);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_DIAMOND);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_OBSIDIAN);
        nocturiteKey.addLoot(LootsEnum.NOCTURITEK_HISTERITE_INGOT);

    }

    public LootTable getCCrusher() {
        return CCrusher;
    }

    public LootTable getVoteKey() {
        return voteKey;
    }

    public LootTable getDiamondKey() {
        return diamondKey;
    }

    public LootTable getHisteriteKey() {
        return histeriteKey;
    }

    public LootTable getNocturiteKey() {
        return nocturiteKey;
    }
}
