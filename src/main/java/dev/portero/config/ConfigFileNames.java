package dev.portero.config;

public enum ConfigFileNames {

    ITEMS("items.yml"),
    SKILLS("skills.yml"),
    QUESTS("quests.yml"),
    NPCS("npcs.yml"),
    HOLOGRAMS("holograms.yml"),
    CHESTS("chests.yml");

    private final String fileName;

    ConfigFileNames(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}