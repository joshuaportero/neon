package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.ExecutorType;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import org.bukkit.entity.Player;

@Command(name = "fly", permission = "neon.cmd.fly")
public class FlyCMD {

    @SubCommand(executorType = ExecutorType.PLAYER)
    public void execute(CommandContext ctx) {
        Player player = ctx.getPlayer();
        String[] args = ctx.args();

        if (args.length == 0) {
            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.sendMessage("§cYour fly mode has been disabled!");
            } else {
                player.setAllowFlight(true);
                player.sendMessage("§aYour fly mode has been enabled!");
            }
        } else {
            Player target = ctx.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cWe couldn't find this player!");
                return;
            }
            if (target.getAllowFlight()) {
                target.setAllowFlight(false);
                target.sendMessage("§cYour fly mode has been disabled!");
                player.sendMessage("§cYou disabled the fly mode of §f" + target.getName());
            } else {
                target.setAllowFlight(true);
                target.sendMessage("§aYour fly mode has been enabled!");
                player.sendMessage("§aYou enabled the fly mode of §f" + target.getName());
            }
        }
    }
}
