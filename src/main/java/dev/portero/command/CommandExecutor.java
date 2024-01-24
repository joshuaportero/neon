package dev.portero.command;

import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import dev.portero.locale.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
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

    public boolean execute() {

        if (checkPermissions(sender, command.permission()) || checkExecutorType(sender, command.executorType()))
            return false;

        if (methods.size() == 1 && methods.stream().findFirst().get().getAnnotation(SubCommand.class).name().isEmpty()) {
            Optional<Method> method = methods.stream().findFirst();
            return this.invokeMethod(method, sender, args);
        }

        if (args.length == 0) {
            Optional<Method> method = methods.stream().filter(m -> m.getAnnotation(SubCommand.class).name().isEmpty()).findFirst();
            if (method.isEmpty()) {
                sender.sendMessage("§c§l" + command.name().toUpperCase() + " §7- §f(" + command.permission() + ")");
                methods.forEach(m -> {
                    SubCommand subCommand = m.getAnnotation(SubCommand.class);
                    sender.sendMessage("§c/" + command.name() + " " + subCommand.name() + " §7- §f" + subCommand.description());
                });
                return false;
            }

            return this.invokeMethod(method, sender, args);
        }

        Optional<Method> method = methods.stream().filter(m -> m.getAnnotation(SubCommand.class).name().equalsIgnoreCase(args[0])).findFirst();
        if (method.isEmpty()) {
            sender.sendMessage("§c/" + command.name() + " §f→ §c" + args[0] + " §7- §fWe couldn't find this subcommand!");
            return false;
        }
        SubCommand subCommand = method.get().getAnnotation(SubCommand.class);

        if (checkPermissions(sender, subCommand.permission()) || checkExecutorType(sender, subCommand.executorType()))
            return false;

        return this.invokeMethod(method, sender, args);
    }

    private boolean invokeMethod(Optional<Method> method, CommandSender sender, String[] args) {

        if(method.isEmpty()) return false;

        try {
            Constructor<?> constructor = method.get().getDeclaringClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            method.get().invoke(constructor.newInstance(), new CommandContext(sender, args));
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                 NoSuchMethodException e) {
            sender.sendMessage("§c" + new RuntimeException(e).getMessage());
            return false;
        }
        return true;
    }

    private boolean checkPermissions(CommandSender sender, String requiredPermission) {
        if (!requiredPermission.isEmpty() && !sender.hasPermission(requiredPermission)) {
            Message.COMMANDS_NO_PERMISSION.send(sender);
            return true;
        }
        return false;
    }

    private boolean checkExecutorType(CommandSender sender, ExecutorType requiredType) {
        if (requiredType == ExecutorType.PLAYER && !(sender instanceof Player)) {
            Message.COMMANDS_ONLY_PLAYER.send(sender);
            return true;
        }
        if (requiredType == ExecutorType.CONSOLE && sender instanceof Player) {
            Message.COMMANDS_ONLY_CONSOLE.send(sender);
            return true;
        }
        return false;
    }
}
