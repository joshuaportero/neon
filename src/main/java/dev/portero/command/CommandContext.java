package dev.portero.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public record CommandContext(CommandSender sender, String[] args) {

    public Player getPlayer() {
        if (!(sender instanceof Player)) {
            throw new IllegalStateException("Only players can execute this command!");
        }
        return (Player) sender;
    }

}
