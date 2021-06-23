package fr.zelytra.histeria.managers.visual.chat;

public enum Emote {
    /**
     * Inside texture pack
     * Emote -> Ascent: 8
     * Banner -> Ascent: 7
     */

    ALIEN("\u9000", ":alien", EmoteType.EMOTE),
    NON("\u9001", ":non", EmoteType.EMOTE),
    OUI("\u9002", ":oui", EmoteType.EMOTE),
    MICHEL("\u9003", ":michel", EmoteType.EMOTE),
    SPIGOT("\u9004", ":spigot", EmoteType.EMOTE),
    SELYTRA("\u9005", ":selytra", EmoteType.EMOTE),
    DEMIGOD("\u9006", ":demigod", EmoteType.EMOTE),
    MONARCH("\u9007", ":monarch", EmoteType.EMOTE),
    LORD("\u9008", ":lord", EmoteType.EMOTE),
    JAVA("\u9009", ":java", EmoteType.EMOTE),
    BEDROCK("\u9010", ":bedrock", EmoteType.EMOTE),

    ALMIGHTY("\u9011", ":null", EmoteType.BADGE),
    ADMIN("\u9012", ":null", EmoteType.BADGE),
    MODO("\u9013", ":null", EmoteType.BADGE),
    GUIDE("\u9014", ":null", EmoteType.BADGE),
    DEMIGOD_BADGE("\u9015", ":null", EmoteType.BADGE),
    MONARCH_BADGE("\u9016", ":null", EmoteType.BADGE),
    LORD_BADGE("\u9017", ":null", EmoteType.BADGE),
    VOTE("\u9018", ":null", EmoteType.BADGE),

    WWW("\u9019", ":null", EmoteType.ICON),
    DISCORD("\u9020", ":null", EmoteType.ICON),
    SHOP("\u9021", ":null", EmoteType.ICON);


    private final String UTF8;
    private final String tag;
    private EmoteType type;

    Emote(String utf8, String tag, EmoteType type) {
        this.UTF8 = utf8;
        this.tag = tag;
        this.type = type;
    }

    public static String getByName(String word) {

        for (Emote emote : Emote.values())
            if (emote.getTag().equalsIgnoreCase(word) && emote.getType() == EmoteType.EMOTE)
                return emote.toString();

        return word;
    }

    public String getTag() {
        return tag;
    }

    public EmoteType getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.UTF8;
    }

}
