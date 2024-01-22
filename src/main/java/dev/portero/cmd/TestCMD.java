package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.ExecutorType;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;

@Command(name = "test", permission = "neon.cmd")
public class TestCMD {

    @SubCommand(permission = "test.cmd", description = "Test command", executorType = ExecutorType.ALL)
    public void execute(CommandContext ctx) {
        ctx.sender().sendMessage("Test command executed!");
    }

    @SubCommand(name = "test", permission = "neon.cmd.test", description = "Test command", executorType = ExecutorType.PLAYER)
    public void testCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Test2 command executed!");
    }

    @SubCommand(name = "help", permission = "neon.cmd.help", description = "Help command", executorType = ExecutorType.CONSOLE)
    public void helpCommand(CommandContext ctx) {
        ctx.sender().sendMessage("Help2 command executed!");
    }
}
