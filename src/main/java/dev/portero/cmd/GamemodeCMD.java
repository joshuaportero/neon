package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.ExecutorType;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Command(name = "gamemode", permission = "neon.cmd.gamemode")
public class GamemodeCMD {
    @SubCommand(name = "help", permission = "neon.cmd.gamemode.help", description = "Help command", executorType = ExecutorType.ALL)
    public void execute(CommandContext ctx) {
        ctx.sender().sendMessage("§c§lGAMEMODE §7- §f(/gamemode <mode>)");
        ctx.sender().sendMessage("§c/gamemode creative §7- §fChange your gamemode to creative");
        ctx.sender().sendMessage("§c/gamemode survival §7- §fChange your gamemode to survival");
        ctx.sender().sendMessage("§c/gamemode spectator §7- §fChange your gamemode to spectator");
        ctx.sender().sendMessage("§c/gamemode adventure §7- §fChange your gamemode to adventure");
    }

    @Command(name = "gmc", permission = "neon.cmd.gamemode.creative")
    public static class GamemodeCreativeCMD {

        @SubCommand(executorType = ExecutorType.PLAYER)
        public void execute(CommandContext ctx) {
            Player player = ctx.getPlayer();
            if (player.getGameMode() == GameMode.CREATIVE) {
                player.sendMessage("§cYou are already in creative mode!");
                return;
            }
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§aYour gamemode has been updated to creative!");
        }
    }

    @Command(name = "gms", permission = "neon.cmd.gamemode.survival")
    public static class GamemodeSurvivalCMD {

        @SubCommand(executorType = ExecutorType.PLAYER)
        public void execute(CommandContext ctx) {
            Player player = ctx.getPlayer();
            if (player.getGameMode() == GameMode.SURVIVAL) {
                player.sendMessage("§cYou are already in survival mode!");
                return;
            }
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("§aYour gamemode has been updated to survival!");
        }
    }

    @Command(name = "gmsp", permission = "neon.cmd.gamemode.spectator")
    public static class GamemodeSpectatorCMD {

        @SubCommand(executorType = ExecutorType.PLAYER)
        public void execute(CommandContext ctx) {
            Player player = ctx.getPlayer();
            if (player.getGameMode() == GameMode.SPECTATOR) {
                player.sendMessage("§cYou are already in spectator mode!");
                return;
            }
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("§aYour gamemode has been updated to spectator!");
        }
    }

    @Command(name = "gma", permission = "neon.cmd.gamemode.adventure")
    public static class GamemodeAdventureCMD {

        @SubCommand(executorType = ExecutorType.PLAYER)
        public void execute(CommandContext ctx) {
            Player player = ctx.getPlayer();
            if (player.getGameMode() == GameMode.ADVENTURE) {
                player.sendMessage("§cYou are already in adventure mode!");
                return;
            }
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage("§aYour gamemode has been updated to adventure!");
        }
    }
}
