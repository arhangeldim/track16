package track.messenger.commands;

/**
 * Created by geoolekom on 14.11.16.
 */

public class CommandException extends Exception {

    public CommandException(String msg) {
        super(msg);
    }

    public CommandException(Throwable ex) {
        super(ex);
    }
}
