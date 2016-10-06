package track.lections.l3oop;

/**
 * пример использования абстрактного класса, чье поведение определено не до конца
 */
public abstract class AServer {

    // как валидировать контент, определено в дочернем классе
    protected abstract boolean validate(String[] params);

    // обработка данных в дочернем классе
    protected abstract void processContent();

    protected String onError() {
        return "Failed to parse request";
    }

    // метод обработки входящего запроса
    public void doGet(String request) {
        String[] parameters = parseParameters(request);
        if (validate(parameters)) {
            processContent();
        } else {
            onError();
        }
    }

    private String[] parseParameters(String request) {
        // разбираем строку параметров
        return "id=1&req=100&imageId=100".split("&");
    }
}
