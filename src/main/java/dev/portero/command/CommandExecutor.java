package dev.portero.command;

import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class CommandExecutor {

    private final Command command;
    private final Set<Method> methods;
    private final CommandSender sender;
    private final String[] args;

    public CommandExecutor(Command command, Set<Method> methods, CommandSender sender, String[] args) {
        this.command = command;
        this.methods = methods;
        this.sender = sender;
        this.args = args;
    }

    public boolean execute() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        if (checkPermissions(sender, command.permission())) return false;
        if (checkExecutorType(sender, command.executorType())) return false;

        if (args.length == 0) {
            Method method = methods.stream().filter(m -> m.getAnnotation(SubCommand.class).name().isEmpty()).findFirst().orElse(null);
            if (method == null) {
                sender.sendMessage("§c§l" + command.name().toUpperCase() + " §7- §f(" + command.permission() + ")");
                methods.forEach(m -> {
                    SubCommand subCommand = m.getAnnotation(SubCommand.class);
                    sender.sendMessage("§c/" + command.name() + " " + subCommand.name() + " §7- §f" + subCommand.description());
                });
                return false;
            }
            method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), new CommandContext(sender, args));
            return true;
        }

        Method method = methods.stream().filter(m -> m.getAnnotation(SubCommand.class).name().equalsIgnoreCase(args[0])).findFirst().orElse(null);
        if (method == null) {
            sender.sendMessage("§c/" + command.name() + " §f→ §c" + args[0] + " §7- §fWe couldn't find this subcommand!");
            return false;
        }
        SubCommand subCommand = method.getAnnotation(SubCommand.class);

        if (checkPermissions(sender, subCommand.permission())) return false;
        if (checkExecutorType(sender, subCommand.executorType())) return false;

        method.invoke(method.getDeclaringClass().getDeclaredConstructor().newInstance(), new CommandContext(sender, args));
        return true;
    }

    private boolean checkPermissions(CommandSender sender, String requiredPermission) {
        if (!requiredPermission.isEmpty() && !sender.hasPermission(requiredPermission)) {
            sender.sendMessage("§cYou don't have permission to execute this command!");
            return true;
        }
        return false;
    }

    private boolean checkExecutorType(CommandSender sender, ExecutorType requiredType) {
        if (requiredType == ExecutorType.PLAYER && !(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return true;
        }
        if (requiredType == ExecutorType.CONSOLE && sender instanceof Player) {
            sender.sendMessage("§cOnly console can execute this command!");
            return true;
        }
        return false;
    }
}
