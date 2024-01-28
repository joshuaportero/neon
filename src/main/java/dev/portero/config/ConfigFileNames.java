package dev.portero.config;

public enum ConfigFileNames {

    CONFIG("config.yml"),
    HOLOGRAMS("holograms.yml");

    private final String fileName;

    ConfigFileNames(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}