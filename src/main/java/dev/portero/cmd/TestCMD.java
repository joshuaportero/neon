package dev.portero.cmd;

import dev.portero.command.CommandContext;
import dev.portero.command.ExecutorType;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import dev.portero.command.annotation.TabComplete;

import java.util.List;

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

    @TabComplete
    public List<String> tabComplete(String[] args) {
        return List.of("test", "help", "test2", "help2", "test3", "help3");
    }
}
