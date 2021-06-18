package fr.zelytra.histeria.managers.chat;

public enum Emote {
    /**
     * Inside texture pack
     * Emote -> Ascent: 8
     * Banner -> Ascent: 7
     */

    ALIEN("\u9000", ":alien"),
    NON("\u9001", ":non"),
    OUI("\u9002", ":oui"),
    MICHEL("\u9003", ":michel"),
    SPIGOT("\u9004", ":spigot"),
    SELYTRA("\u9005", ":selytra"),
    DEMIGOD("\u9006", ":demigod"),
    MONARCH("\u9007", ":monarch"),
    LORD("\u9008", ":lord"),
    JAVA("\u9009", ":java"),
    BEDROCK("\u9010", ":bedrock");

    private final String UTF8;
    private final String tag;

    Emote(String utf8, String tag) {
        this.UTF8 = utf8;
        this.tag = tag;
    }

    public static String getByName(String word) {

        for (Emote emote : Emote.values())
            if (emote.getTag().equalsIgnoreCase(word))
                return emote.toString();

        return word;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return this.UTF8;
    }
}
