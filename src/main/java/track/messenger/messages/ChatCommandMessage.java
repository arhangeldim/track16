package track.messenger.messages;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by user on 24.11.16.
 */
public class ChatCommandMessage extends Message {

    public ArrayList<Long> getIdToDo() {
        return IdToDo;
    }

    public void setIdToDo(ArrayList<Long> idToDo) {
        IdToDo = idToDo;
    }

    ArrayList<Long> IdToDo;

}
