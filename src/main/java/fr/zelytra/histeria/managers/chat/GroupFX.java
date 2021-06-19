package fr.zelytra.histeria.managers.chat;


import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public enum GroupFX {

    FONDATEUR("fondateur", Emote.ALMIGHTY, TextColor.color(0x9dc2e7), TextColor.color(NamedTextColor.WHITE)," > ", TextColor.color(NamedTextColor.AQUA)),
    ADMIN("administrator", Emote.ADMIN, TextColor.color(0xda0800), TextColor.color(NamedTextColor.GOLD)," > ", TextColor.color(0xff3030)),
    MODO("moderator", Emote.MODO, TextColor.color(0x09a917), TextColor.color(NamedTextColor.GOLD)," > ", TextColor.color(NamedTextColor.GREEN)),
    GUIDE("guide", Emote.GUIDE, TextColor.color(0x16ceee), TextColor.color(0x00768a) , " > ", TextColor.color(NamedTextColor.WHITE)),

    YOUTUBER("youtuber", Emote.ALIEN, TextColor.color(0xfd6c50), TextColor.color(0x990300) , " > ", TextColor.color(NamedTextColor.WHITE)),

    DEMIGOD("demigod", Emote.DEMIGOD_BADGE, TextColor.color(0xffc428), TextColor.color(NamedTextColor.WHITE) , " > ", TextColor.color(NamedTextColor.WHITE)),
    MONARCH("monarch", Emote.MONARCH_BADGE, TextColor.color(0x2b71e6), TextColor.color(NamedTextColor.WHITE) , " > ", TextColor.color(NamedTextColor.WHITE)),
    LORD("lord", Emote.LORD_BADGE, TextColor.color(0x00cea3), TextColor.color(NamedTextColor.WHITE) , " > ", TextColor.color(NamedTextColor.WHITE)),
    VOTE("vote", Emote.VOTE, TextColor.color(NamedTextColor.GRAY), TextColor.color(NamedTextColor.GRAY) , " > ", TextColor.color(NamedTextColor.GRAY)),

    DEFAULT("default", Emote.ALIEN, TextColor.color(NamedTextColor.GRAY), TextColor.color(NamedTextColor.GRAY) , " > ", TextColor.color(NamedTextColor.GRAY));

    private final String group;
    private final TextColor nameColor;
    private final String splitter;
    private final TextColor splittercolor;
    private final TextColor messageColor;
    private final Emote badge;


    GroupFX(String group, Emote badge, TextColor nameColor,TextColor splittercolor, String splitter, TextColor messageColor) {
        this.group = group;
        this.nameColor = nameColor;
        this.splitter = splitter;
        this.messageColor = messageColor;
        this.badge = badge;
        this.splittercolor=splittercolor;
    }

    public static GroupFX getByName(String name) {
        for (GroupFX groupFX : GroupFX.values()) {
            if (groupFX.getGroup().equalsIgnoreCase(name))
                return groupFX;
        }
        return DEFAULT;
    }

    public String getGroup() {
        return group;
    }

    public TextColor getNameColor() {
        return nameColor;
    }

    public String getSplitter() {
        return splitter;
    }

    public TextColor getMessageColor() {
        return messageColor;
    }

    public Emote getBadge() {
        return badge;
    }

    public TextColor getSplittercolor() {
        return splittercolor;
    }
}
