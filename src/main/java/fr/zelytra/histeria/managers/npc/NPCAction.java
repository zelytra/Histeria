package fr.zelytra.histeria.managers.npc;

public enum NPCAction {

    DEFAULT,
    SERVER,
    TELEPORT,
    SHOP,
    HMARKET;

    public static NPCAction getByName(String name) {

        for (NPCAction action : NPCAction.values())
            if (action.name().equalsIgnoreCase(name))
                return action;

        return null;
    }

}
