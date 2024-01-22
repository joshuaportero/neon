package dev.portero.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record CommandContext(CommandSender sender, String[] args) {

    public Player getPlayer() {
        return (Player) sender;
    }
}
