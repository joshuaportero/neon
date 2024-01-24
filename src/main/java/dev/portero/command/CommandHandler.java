package dev.portero.command;

import dev.portero.NeonPlugin;
import dev.portero.command.annotation.Command;
import dev.portero.command.annotation.SubCommand;
import dev.portero.command.exception.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.reflections.scanners.Scanners.*;

public class CommandHandler {

    private static final String COMMAND_PACKAGE = "dev.portero.cmd";
    private final Map<String, Set<Method>> subCommandsMap = new HashMap<>();
    private final NeonPlugin plugin;

    public CommandHandler(NeonPlugin plugin) {
        this.plugin = plugin;
        this.registerCommands();
        this.registerSubCommands();
    }

    private Set<Class<?>> getCommands() {
        Reflections reflections = new Reflections(COMMAND_PACKAGE);
        return reflections.get(SubTypes.of(TypesAnnotated.with(Command.class)).asClass());
    }

    private Set<Method> getSubCommands() {
        Reflections reflections = new Reflections(COMMAND_PACKAGE, MethodsAnnotated);
        return reflections.getMethodsAnnotatedWith(SubCommand.class);
    }

    private void registerCommands() {
        Iterable<Class<?>> commandClasses = this.getCommands();

        for (Class<?> commandClass : commandClasses) {
            Command command = commandClass.getAnnotation(Command.class);
            PluginCommand pluginCommand = plugin.getCommand(command.name());
            if (pluginCommand == null) {
                plugin.getLogger().warning("The command " + command.name() + " couldn't be registered! Please check your plugin.yml file!");
                continue;
            }
            pluginCommand.setExecutor((sender, cmd, label, args) -> executeCommand(commandClass, sender, args));
        }
    }

    private void registerSubCommands() {
        Iterable<Method> subCommands = this.getSubCommands();
        for (Method method : subCommands) {
            String className = method.getDeclaringClass().getSimpleName();
            subCommandsMap.computeIfAbsent(className, k -> new HashSet<>()).add(method);
        }
    }

    private boolean executeCommand(Class<?> commandClass, CommandSender sender, String[] args) {
        Command command = commandClass.getAnnotation(Command.class);
        Set<Method> methods = this.subCommandsMap.get(commandClass.getSimpleName());

        try {
            CommandExecutor commandExecutor = new CommandExecutor(command, methods, sender, args);
            return commandExecutor.execute();
        } catch (Exception e) {
            throw new CommandException("An error occurred while executing command " + command.name() + "!", e);
        }
    }
}