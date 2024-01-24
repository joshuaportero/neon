package dev.portero.locale;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

import static net.kyori.adventure.text.Component.translatable;
import static net.kyori.adventure.text.format.NamedTextColor.RED;

public interface Message {

    Args0 COMMANDS_NO_PERMISSION = () -> translatable()
            .key("commands.no-permission")
            .color(RED)
            .build();
    Args0 COMMANDS_ONLY_PLAYER = () -> translatable()
            .key("commands.only-player")
            .color(RED)
            .build();
    Args0 COMMANDS_ONLY_CONSOLE = () -> translatable()
            .key("commands.only-console")
            .color(RED)
            .build();

    interface Args0 {
        Component build();

        default void send(CommandSender sender) {
            sender.sendMessage(build());
        }
    }

    interface Args1<A0> {
        Component build(A0 arg0);

        default void send(CommandSender sender, A0 arg0) {
            sender.sendMessage(build(arg0));
        }
    }

    interface Args2<A0, A1> {
        Component build(A0 arg0, A1 arg1);

        default void send(CommandSender sender, A0 arg0, A1 arg1) {
            sender.sendMessage(build(arg0, arg1));
        }
    }
}