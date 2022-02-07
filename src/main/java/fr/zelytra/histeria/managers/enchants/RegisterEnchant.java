package fr.zelytra.histeria.managers.enchants;

public abstract class RegisterEnchant {

    public static void register(){
        new CustomEnchant(CustomEnchantData.BLESS_OF_KEEPING);
    }
}
