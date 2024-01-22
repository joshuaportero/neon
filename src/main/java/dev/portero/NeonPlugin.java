package dev.portero;

import dev.portero.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class NeonPlugin extends JavaPlugin {

    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        commandHandler = new CommandHandler(this);
    }

    @Override
    public void onDisable() {

    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}