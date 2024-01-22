package dev.portero.command;

import dev.portero.NeonPlugin;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import dev.portero.command.exception.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static org.reflections.scanners.Scanners.*;

public class CommandHandler {

    private static final String COMMAND_PACKAGE = "dev.portero.cmd";
    Map<String, Set<Method>> subCommandsMap = new HashMap<>();
    private final NeonPlugin plugin;

    public CommandHandler(NeonPlugin plugin) {
        this.plugin = plugin;
        this.registerCommands();
        this.registerSubCommands();
    }

    private void registerCommands() {
        Iterable<Class<?>> commandClasses = this.getCommands();

        for (Class<?> commandClass : commandClasses) {
            Command command = commandClass.getAnnotation(Command.class);
            if (command == null) {
                throw new CommandException("Command class " + commandClass.getName() + " is missing @Command annotation!");
            }
            Objects.requireNonNull(plugin.getCommand(command.name())).setExecutor((sender, cmd, label, args) -> {
                try {
                    return execute(commandClass, sender, args);
                } catch (InvocationTargetException | IllegalAccessException | InstantiationException |
                         NoSuchMethodException e) {
                    throw new CommandException("An error occurred while executing command " + command.name() + "!", e);
                }
            });
        }
    }

    private void registerSubCommands() {
        Iterable<Method> subCommands = this.getSubCommands();
        for (Method method : subCommands) {
            String className = method.getDeclaringClass().getSimpleName();
            subCommandsMap.computeIfAbsent(className, k -> new HashSet<>()).add(method);
        }
    }

    private Set<Class<?>> getCommands() {
        Reflections reflections = new Reflections(COMMAND_PACKAGE);
        return reflections.get(SubTypes.of(TypesAnnotated.with(Command.class)).asClass());
    }

    private Set<Method> getSubCommands() {
        Reflections reflections = new Reflections(COMMAND_PACKAGE, MethodsAnnotated);
        return reflections.getMethodsAnnotatedWith(SubCommand.class);
    }

    private boolean execute(Class<?> commandClass, CommandSender sender, String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Command command = commandClass.getAnnotation(Command.class);
        if (!command.permission().isEmpty() && !sender.hasPermission(command.permission())) {
            sender.sendMessage("§cYou don't have permission to execute this command!");
            return false;
        }
        if (command.executorType() == ExecutorType.PLAYER && !(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can execute this command!");
            return false;
        }

        Set<Method> methods = this.subCommandsMap.get(commandClass.getSimpleName());

        if (methods == null) {
            sender.sendMessage("§cThis command is being developed!");
            return false;
        }

        if (args.length == 0) {
            Method method = methods.stream().filter(m -> m.getAnnotation(SubCommand.class).name().isEmpty()).findFirst().orElse(null);
            if (method == null) {
                sender.sendMessage("§cInvalid usage! Try: /" + command.name() + " help");
                return false;
            }
            method.invoke(commandClass.getDeclaredConstructor().newInstance(), new CommandContext(sender, args));
            return true;
        }

        return false;
    }
}