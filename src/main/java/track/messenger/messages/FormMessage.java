package track.messenger.messages;

import track.messenger.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 30.11.16.
 */
public class FormMessage extends Message{
    public FormMessage(User sender) {
        super(sender);
    }
    private Map<String, String> fields = new HashMap<>();

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getField(String name) {
        return fields.get(name);
    }

    public void setField(String name, String value) {
        fields.put(name, value);
    }
}
