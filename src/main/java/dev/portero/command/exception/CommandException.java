package dev.portero.command.exception;

public class CommandException extends RuntimeException {
    public CommandException(String s) {
        super(s);
    }

    public CommandException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
