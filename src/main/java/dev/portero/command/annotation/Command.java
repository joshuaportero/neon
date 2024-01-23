package dev.portero.command.annotation;

import dev.portero.command.ExecutorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking a class as a command handler within a command framework.
 * This annotation is used on classes to designate them as handlers for specific commands,
 * providing metadata about the command such as its name, permission, and executor type.
 * The 'name' attribute defines the base command name used for invocation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    /**
     * The name of the command. This is the primary identifier used to invoke the command.
     * For example, if the command name is "command", it would typically be invoked
     * with "/command".
     *
     * @return The name of the command.
     */
    String name();

    /**
     * The permission string required to execute this command.
     * This restricts who can execute the command. If left empty, the command
     * is available to all users without any specific permissions.
     *
     * @return The permission string for the command.
     */
    String permission() default "";

    /**
     * Specifies the type of executor that is allowed to run this command.
     * It can be set to players, console, or both, depending on the command's requirements.
     * The default is {@code ExecutorType.ALL}, allowing any executor to run the command.
     * See {@link ExecutorType} for possible values.
     *
     * @return The executor type for the command.
     */
    ExecutorType executorType() default ExecutorType.ALL;
}
