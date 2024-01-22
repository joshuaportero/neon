package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;

@Command(name = "neon", permission = "neon.cmd")
public class NeonCMD {

    @SubCommand(name = "test", permission = "neon.cmd.test", description = "Test command")
    public void testCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Test command executed!");
    }

    @SubCommand(name = "help", permission = "neon.cmd.help", description = "Help command")
    public void helpCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Help command executed!");
    }
}
