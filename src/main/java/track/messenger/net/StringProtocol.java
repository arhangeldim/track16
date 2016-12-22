package track.messenger.net;
//убрать строковый протокол и добавить сериализацию
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import track.messenger.User;
import track.messenger.messages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Простейший протокол передачи данных
 */
public class StringProtocol implements Protocol {

    static Logger log = LoggerFactory.getLogger(StringProtocol.class);

    public static final String DELIMITER = ";";
    private User user;

    @Override
    public Message decode(byte[] bytes) throws ProtocolException {
        String str = new String(bytes);
        log.info("decoded: {}", str);
        String[] tokens = str.split(DELIMITER);
        Type type = Type.valueOf(tokens[0]);
        switch (type) {
            case MSG_STATUS:
                StatusMessage statusMessage = new StatusMessage();
                statusMessage.setStatus(tokens[1]);
                statusMessage.setType(type);
                return statusMessage;
            case MSG_TEXT:
                TextMessage textMsg = new TextMessage();
                textMsg.setSenderId(parseLong(tokens[1]));
                textMsg.setText(tokens[2]);
                textMsg.setType(type);
                return textMsg;
            case MSG_LOGIN:
                LoginMessage logMsg = new LoginMessage();
                logMsg.setUsername(tokens[1]);
                logMsg.setPassword(tokens[2]);
                logMsg.setType(type);
                return logMsg;
            case MSG_REGISTER:
                Registration registration = new Registration();
                registration.setUsername(tokens[1]);
                registration.setPassword(tokens[2]);
                registration.setType(type);
                return registration;
            case MSG_QUIT:
                LoginMessage quitMsg = new LoginMessage();
                quitMsg.setType(type);
                return quitMsg;
            case MSG_CHAT_LIST:
                ChatList listChatMessage = new ChatList();
                listChatMessage.setSenderId(parseLong(tokens[1]));
                listChatMessage.setType(type);
                return listChatMessage;
            case MSG_CHAT_CREATE:
                ChatCreate chatCreateMessage = new ChatCreate();
                String[] userIdsStr = tokens[2].split(",");
                List<Long> userIds = new ArrayList<Long>();
                for (int i = 0; i < userIdsStr.length; ++i) {
                    userIds.add(Long.parseLong(userIdsStr[i]));
                }
                //chatCreateMessage.setUsersIds(userIds);
                chatCreateMessage.setType(type);
                return chatCreateMessage;
            case MSG_CHAT_LIST_RESULT:
                ChatListResultMessage chatListResultMessage = new ChatListResultMessage();
                chatListResultMessage.setSenderId(parseLong(tokens[1]));
                if (tokens.length >= 3) {
                    chatListResultMessage.setChatIds(Arrays.stream(tokens[2].split(","))
                            .map(this::parseLong)
                            .collect(Collectors.toList()));
                }
                return chatListResultMessage;
            case MSG_CHAT_HIST_RESULT:
                ChatHistResultMessage chatHistResultMessage = new ChatHistResultMessage();
                chatHistResultMessage.setSenderId(parseLong(tokens[1]));
                //if (tokens.length >= 3) {
                //    chatHistResultMessage.setHistory(tokens[2]);
                //}
                return chatHistResultMessage;
            default:
                throw new ProtocolException("Invalid type: " + type);
        }
    }

    @Override
    public byte[] encode(Message msg) throws ProtocolException {
        StringBuilder builder = new StringBuilder();
        Type type = msg.getType();
        builder.append(type).append(DELIMITER);
        switch (type) {
            case MSG_STATUS:
                StatusMessage statusMessage = (StatusMessage) msg;
                builder.append(statusMessage.getStatus()).append(DELIMITER);
                break;
            case MSG_TEXT:
                TextMessage sendMessage = (TextMessage) msg;
                builder.append(String.valueOf(sendMessage.getSenderId())).append(DELIMITER);
                builder.append(sendMessage.getText()).append(DELIMITER);
                break;
            case MSG_LOGIN:
                LoginMessage loginMessage = (LoginMessage) msg;
                builder.append(loginMessage.getUsername()).append(DELIMITER);
                builder.append(loginMessage.getPassword()).append(DELIMITER);
                break;
            case MSG_REGISTER:
                Registration registration = (Registration) msg;
                builder.append(registration.getUsername()).append(DELIMITER);
                builder.append(registration.getPassword()).append(DELIMITER);
                break;
            case MSG_CHAT_LIST:
                ChatList chatList = (ChatList) msg;
                builder.append(String.valueOf(chatList.getSenderId())).append(DELIMITER);
                builder.append(chatList.getChatsList()).append(DELIMITER);
                break;
            case MSG_CHAT_CREATE:
                ChatCreate chatCreate = (ChatCreate) msg;
                //List<Long> userIds = chatCreate.getUsersIds();
                //List<String> userIdsStr = new ArrayList<String>();
                //for (int i = 0; i < userIds.size(); ++i) {
                //    userIdsStr.add(String.valueOf(userIds.get(i)));
                //}
                //builder.append(String.valueOf(chatCreate.getSenderId())).append(DELIMITER);
                //builder.append(String.join(",",
                 //       userIdsStr)).append(DELIMITER);
                break;

            case MSG_QUIT:
                TextMessage quitMessage = (TextMessage) msg;
                builder.append(String.valueOf(quitMessage.getSenderId())).append(DELIMITER);
                break;
            default:
                throw new ProtocolException("Invalid type: " + type);


        }
        log.info("encoded: {}", builder.toString());
        return builder.toString().getBytes();
    }

    private Long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            // who care
        }
        return null;
    }
}