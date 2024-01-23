package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.ExecutorType;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import org.bukkit.entity.Player;

@Command(name = "neon", permission = "neon.cmd")
public class NeonCMD {

    @SubCommand(name = "test", permission = "neon.cmd.test", description = "Test the NeonCMD class", executorType = ExecutorType.PLAYER)
    public void testCommand(CommandContext ctx) {
        if (ctx.args().length == 1) {
            ctx.sender().sendMessage("Test command executed!");
        } else {
            Player player = ctx.getPlayer();
            player.teleport(player.getWorld().getSpawnLocation());
            ctx.sender().sendMessage("Teleported to spawn!");
        }
    }

    @SubCommand(name = "version", permission = "neon.cmd.version", description = "Get the version of the plugin", executorType = ExecutorType.PLAYER)
    public void versionCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Version command executed!");
    }

    @SubCommand(name = "discord", permission = "neon.cmd.discord", description = "Get the discord link of the community", executorType = ExecutorType.CONSOLE)
    public void discordCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Discord command executed!");
    }

    @SubCommand(name = "website", permission = "neon.cmd.website", description = "Get the website link of the community")
    public void websiteCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Website command executed!");
    }
}
