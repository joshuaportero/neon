package dev.portero.command;

import dev.portero.command.annotation.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CommandCompletions {

    private final Set<Method> subCommandsMethods;
    private final List<Method> tabCompleteMethods;
    private final String[] args;

    public CommandCompletions(Set<Method> subCommandsMethods, List<Method> tabCompleteMethods, String[] args) {
        this.subCommandsMethods = subCommandsMethods;
        this.tabCompleteMethods = tabCompleteMethods;
        this.args = args;
    }

    public List<String> getCompletions() {

        for (Method method : tabCompleteMethods) {
            if (method.getParameterCount() == 1) {
                return invokeMethod(method, args);
            }
        }
        return List.of();
    }

    @SuppressWarnings("unchecked")
    private List<String> invokeMethod(Method method, String[] args) {

        if (method == null) return Collections.emptyList();

        List<String> list;

        try {
            Constructor<?> constructor = method.getDeclaringClass().getDeclaredConstructor();
            constructor.setAccessible(true);
            list = (List<String>) method.invoke(constructor.newInstance(), (Object) args);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                 NoSuchMethodException e) {
            return Collections.emptyList();
        }
        return list;
    }
}
