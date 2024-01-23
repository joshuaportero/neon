package dev.portero.command.annotation;

import dev.portero.command.ExecutorType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for marking a method as a subcommand within a command framework.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    /**
     * The name of the subcommand. This is used to invoke the subcommand.
     * For example, if the command is "command" and the subcommand name is "sub",
     * it would typically be invoked with "/command sub".
     * If this attribute is not set (i.e., left as an empty string), the method
     * will be executed when the base command ("/command") is run without any
     * additional arguments.
     *
     * @return The name of the subcommand.
     */
    String name() default "";

    /**
     * The permission string required to execute this subcommand.
     * Permissions are used to restrict who can execute this subcommand.
     * If no permission is set, then the subcommand is available to everyone.
     *
     * @return The permission string for the subcommand.
     */
    String permission() default "";

    /**
     * A brief description of what the subcommand does.
     * This is used for generating help messages or command documentation.
     *
     * @return The description of the subcommand.
     */
    String description() default "";

    /**
     * Specifies the type of executor that is allowed to run this subcommand.
     * It can be set to players, console, or both, depending on the command's requirements.
     * The default is {@code ExecutorType.ALL}, allowing any executor to run the subcommand.
     * See {@link ExecutorType} for possible values.
     *
     * @return The executor type for the subcommand.
     */
    ExecutorType executorType() default ExecutorType.ALL;
}
