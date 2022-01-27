package fr.zelytra.histeria.managers.jobs.visual;

public enum JobPage {
    MENU("Menu"),
    PROGRESSION("Progression");

    public String pageName;

    JobPage(String name) {
        this.pageName = name;
    }
}
