package track.messenger.commands;

import track.messenger.messages.Message;
import track.messenger.net.ProtocolException;
import track.messenger.net.Session;

import java.io.IOException;

public interface Command {

    /**
     * Реализация паттерна Команда. Метод execute() вызывает соответствующую реализацию,
     * для запуска команды нужна сессия, чтобы можно было сгенерить ответ клиенту и провести валидацию
     * сессии.
     * @param session - текущая сессия
     * @param message - сообщение для обработки
     * @throws CommandException - все исключения перебрасываются как CommandException
     */
    void execute(Session session, Message message)
            throws CommandException, IOException, ProtocolException;
}