package fr.zelytra.histeria.managers.visual.tab;

public enum Tab {
    HEADER("§6§m                                        \n", "§6§l<   §r§bHisteria   §6§l>\n"),
    FOOTER("\n", " §e[§642§e/§6100§e] | Server: §6Faction§e | §7v2.0 \n", "\n", "§bhisteria.fr\n", "§6§m                                        ");

    private String[] content;

    Tab(String... content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String line : content)
            stringBuilder.append(line);

        return stringBuilder.toString();
    }
}
