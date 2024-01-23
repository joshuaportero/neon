package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.ExecutorType;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;

@Command(name = "neon", permission = "neon.cmd")
public class NeonCMD {

    @SubCommand(name = "test", permission = "neon.cmd.test", description = "Test the NeonCMD class")
    public void testCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Test command executed!");
    }

    @SubCommand(name = "help", permission = "neon.cmd.help", description = "Display the help menu")
    public void helpCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Help command executed!");
    }

    @SubCommand(name = "reload", permission = "neon.cmd.reload", description = "Reload all the config files")
    public void reloadCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Reload command executed!");
    }

    @SubCommand(name = "info", permission = "neon.cmd.info", description = "Get info about the plugin")
    public void infoCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Info command executed!");
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
