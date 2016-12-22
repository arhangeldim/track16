package track.messenger.commands;

/**
 * Created by asus on 30.11.16.
 */
public class CommandException extends Exception {
    public CommandException(Exception e) {
    }

    public CommandException(String message) {
        super(message);
    }

}
