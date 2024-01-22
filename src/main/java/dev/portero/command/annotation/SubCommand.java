package dev.portero.command.annotation;

import dev.portero.command.ExecutorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SubCommand {

    String name() default "";

    String permission() default "";

    String description() default "";

    ExecutorType executorType() default ExecutorType.ALL;

}
