package track.messenger.store;

import track.messenger.messages.TextMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by geoolekom on 14.11.16.
 */

public class MessageStore extends AbstractStore<TextMessage> {

    @Override
    public String values(List<TextMessage> objects) throws SQLException {
        StringBuilder insert = new StringBuilder();
        for (TextMessage msg : objects) {
            insert.append(
                    "('" + msg.getTimestamp() + "', '" +
                            msg.getChatId().toString() + "', '" +
                            msg.getSenderId().toString() + "', '" +
                            msg.getText() + "'), "
            );
        }
        String value = insert.toString();
        return value.substring(0, value.length() - 2);
    }

    @Override
    public List<TextMessage> fill(ResultSet resultSet) throws SQLException {
        List<TextMessage> messages = new LinkedList<>();
        while (resultSet.next()) {
            TextMessage msg = new TextMessage();
            msg.setId(resultSet.getInt("id"));
            msg.setTimestamp(resultSet.getString("timestamp"));
            msg.setChatId(resultSet.getInt("chatId"));
            msg.setSenderId(resultSet.getInt("senderId"));
            msg.setText(resultSet.getString("text"));
            messages.add(msg);
        }
        return messages;
    }

    @Override
    public String columns() {
        return "(timestamp, chatId, senderId, text)";
    }

    @Override
    public Class getDataClass() {
        return TextMessage.class;
    }

    public void saveMessage(TextMessage msg) {
        save(Collections.nCopies(1, msg));
    }

    public List<TextMessage> getChatHistory(Integer chatId) {
        return get("chatId = '" + chatId.toString() + "' order by timestamp");
    }

    public List<TextMessage> getChatHistorySince(Integer chatId, Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy, z HH:mm:ss");
        String dateAsString = dateFormat.format(date);
        return get("chatId = '" + chatId.toString() + "' and timestamp > '" + dateAsString +  "' order by timestamp");
    }
}