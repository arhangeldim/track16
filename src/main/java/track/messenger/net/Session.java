package track.messenger.net;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import sun.rmi.runtime.Log;
import track.messenger.User;
import track.messenger.messages.*;

/**
 * Сессия связывает бизнес-логику и сетевую часть.
 * Бизнес логика представлена объектом юзера - владельца сессии.
 * Сетевая часть привязывает нас к определнному соединению по сети (от клиента)
 */
public class Session implements Runnable{

    /**
     * Пользователь сессии, пока не прошел логин, user == null
     * После логина устанавливается реальный пользователь
     */
    private static boolean interrupted = false;
    private Socket OurSocket;
    private final Long OurId = 1l;
    private User user;
    private Long LoginId;
    private Long SocketId;
    private boolean isLogged = false;

    // сокет на клиента
    private Socket socket;

    /**
     * С каждым сокетом связано 2 канала in/out
     */
    private InputStream in;
    ExecutorService executorService;
    private OutputStream out;
    SqliteManager sqliteManager;

    private Session()
    {
        sqliteManager = new SqliteManager();
        sqliteManager.connectToDb();
    }

    public Session(InputStream in, OutputStream out) throws SQLException, ProtocolException {
        this.in = in;
        this.out = out;
        try {
            if(executorService == null)
                executorService = Executors.newFixedThreadPool(5);
            sqliteManager = new SqliteManager();
            sqliteManager.connectToDb();
            System.out.println("starting getting inputstream");
            Message message = (Message) new ObjectInputStream(in).readObject();
            System.out.println("can get inputstream");
            this.onMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Session(Socket socket, SqliteManager sqliteManager) throws IOException {
        OurSocket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
        this.sqliteManager = sqliteManager;
    }

    public void send(Message msg) throws ProtocolException, IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(msg);
        objectOutputStream.flush();
    }

    public void onMessage(Message msg) throws SQLException, IOException, ProtocolException {
        System.out.println("type: = " + msg.getType().toString());
        switch(msg.getType())
        {
            case MSG_LOGIN:
            {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        LoginMessage loginMessage = (LoginMessage) msg;
                        try {
                            LogIn(loginMessage);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }
            case MSG_CHAT_LIST:
            {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        ChatInfMessage chatInfMessage = (ChatInfMessage) msg;
                        try {
                            GetChatLIst(chatInfMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }
            case MSG_CHAT_HIST:
            {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        ChatInfMessage chatHistMessage = (ChatInfMessage) msg;
                        try {
                            GetChatHist(chatHistMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }
            case MSG_SIGN_UP:
            {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        SignUpMessage signUpMessage = (SignUpMessage) msg;
                        try {
                            SignUp(signUpMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }
            case MSG_TEXT:
            {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        TextMessage textMessage = (TextMessage) msg;
                        try {
                            Messaging(textMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            }
            case MSG_CHAT_CREATE:
            {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        ChatCommandMessage chatCommandMessage = (ChatCommandMessage) msg;
                        CreateChat(chatCommandMessage);
                    }
                });
            }
            default:
            {
                send(new TextMessage(OurId, msg.getSenderId(), "Wrong Type!")); // 1 - my id
                break;
            }
        }

    }

    private void GetChatHist(ChatInfMessage chatHistMessage) throws IOException, ProtocolException {
        ChatInfMessage answer = new ChatInfMessage(OurId, chatHistMessage.getId(), Type.MSG_CHAT_LIST_RESULT);
        Long Chatid = Long.valueOf(chatHistMessage.getText());
        StringBuilder stringBuilder = new StringBuilder();
        if(isLogged)
        {
            ResultSet resultSet = sqliteManager.getChatHist(LoginId);
            try {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                if(resultSet == null)
                    answer.setText("Smth went wrong");
                else {
                    try {
                        resultSet.next();
                        while (!resultSet.isAfterLast()) {
                            for(int i = 2; i < resultSetMetaData.getColumnCount(); i++)
                            stringBuilder.append(resultSetMetaData.getColumnName(i) + ": " + resultSet.getString(i) + " \n");
                            resultSet.next();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    answer.setText(stringBuilder.toString());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else
            answer.setText("Please Log in!!");
        System.out.println(answer.getText());
        send(answer);
    }

    private void GetChatLIst(ChatInfMessage chatInfMessage) throws IOException, ProtocolException {

        ChatInfMessage answer = new ChatInfMessage(OurId, chatInfMessage.getId(), Type.MSG_CHAT_LIST_RESULT);
        StringBuilder stringBuilder = new StringBuilder();
        if(isLogged)
        {
            ResultSet resultSet = sqliteManager.getChatList(LoginId);
            if(resultSet == null)
                answer.setText("Smth went wrong");
            else {
                try {
                    resultSet.next();
                    while (!resultSet.isAfterLast()) {
                        System.out.println("WTF?");
                        stringBuilder.append(resultSet.getLong(1) + " ");
                        resultSet.next();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                answer.setText(stringBuilder.toString());
            }
        }
        else
            answer.setText("Please Log in!!");
        System.out.println(answer.getText());
        send(answer);

    }

    private void SignUp(SignUpMessage signUpMessage) throws IOException, ProtocolException, SQLException {
        TextMessage textMessage = new TextMessage(OurId, signUpMessage.getSenderId(), "Signing Upping)))");
        textMessage.setType(Type.MSG_STATUS);
        Long newIdOrNull = sqliteManager.contains(signUpMessage.getLogin());
        System.out.println(newIdOrNull);
        if(newIdOrNull == 0)
        {
            textMessage.setText("Sorry this account is busy(Direct translate)))) Is used already!!!");
        }
        else
        {
            sqliteManager.addNewUser(signUpMessage.getLogin(), signUpMessage.getPassword());
            textMessage.setText("Signed up!!");
        }
        send(textMessage);

    }

    private synchronized void CreateChat(ChatCommandMessage chatCommandMessage) {
        TextMessage textMessage = new TextMessage(OurId, chatCommandMessage.getSenderId(), "Creating Db");
        textMessage.setType(Type.MSG_STATUS);

        if((isLogged) || 1 == 1)
        {
            sqliteManager.CreateTableFor(LoginId, chatCommandMessage.getIdToDo());
            textMessage.setText("Sucesfully created!!!");
        }
        else
        {
            textMessage.setText("Please, log in!!!");
        }
        try {
            send(textMessage);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void Messaging(TextMessage textMessage) throws IOException, ProtocolException {

        TextMessage answer_to_our_person = new TextMessage(textMessage.getSenderId());
        System.out.println((isLogged && LoginId == textMessage.getSenderId()));
        // !!!!!!!!!!!!! Write to others about msg !!!!!!!!!!!!!!!
        try {
            if((isLogged) || 1==1)
            {
                System.out.println("sent to " + textMessage.getSendToId() + "   senderId " + textMessage.getSenderId());
                sqliteManager.postMessageToFrom(textMessage.getSendToId(), LoginId, textMessage.getText());
                answer_to_our_person.setText("successfully sended");
            }
            else
                answer_to_our_person.setText("You Haven't logged in yet");

        } finally {
            this.send(answer_to_our_person);
        }

    }

    public static void main(String[] args) throws SQLException, IOException, ProtocolException {
        Session session = new Session();
        session.executorService = new ScheduledThreadPoolExecutor(5);
        ChatInfMessage chatCommandMessage = new ChatInfMessage(1l, 1l, Type.MSG_CHAT_LIST);
        session.isLogged = true;
        session.LoginId = 10l;
        session.onMessage(chatCommandMessage);
    }

    private synchronized void LogIn(LoginMessage loginMessage) throws SQLException, IOException, ProtocolException {

        TextMessage textMessage = new TextMessage();
        textMessage.setId(loginMessage.getId());
        textMessage.setType(Type.MSG_STATUS);
        textMessage.setSendToId(loginMessage.getSenderId());
        textMessage.setSenderId(OurId); // My ID


        String Login = loginMessage.getLogin();
        String Password = loginMessage.getPassword();
        // Check if SenderId with Login and Password ind Db Log Him in
        ResultSet resultSet = null;
        String text;
        try {
            resultSet = sqliteManager.getItemWithSameLogin(Login);
        } catch (SQLException e) {
            text = "user doesn't exsist";
            textMessage.setText(text);
            this.send(textMessage);
            return ;
        }
        if(resultSet == null || resultSet.isAfterLast()){
            // user doesn't exsist call
            text = "user doesn't exsist";
            textMessage.setText(text);
            this.send(textMessage);
            return ;
        }

        if(!resultSet.getString("password").equals(Password))
        {
            // user password wrong call
            text = "user typed wrong password";
            textMessage.setText(text);
            this.send(textMessage);
            return ;
        }

        LoginId = resultSet.getLong("id");
        isLogged = true;
        text = "Successfully logged in";
        textMessage.setText(text);
        this.send(textMessage);
    }

    public void close() throws IOException {
        interrupted = true;
        in.close();
        out.close();
        // TODO: закрыть in/out каналы и сокет. Освободить другие ресурсы, если необходимо
    }

    @Override
    public void run() {
        if(executorService == null)
            executorService = Executors.newFixedThreadPool(5);
        while(!interrupted)
        {
            System.out.println("starting getting inputstream");
            Message message = null;
            try {
                if(!OurSocket.isConnected() || OurSocket.isInputShutdown() || OurSocket.isClosed()){
                    interrupted = true;
                    break;
                }
                message = (Message) new ObjectInputStream(in).readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("can get inputstream");
            try {
                this.onMessage(message);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
        }
    }
}