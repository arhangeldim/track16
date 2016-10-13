package track.container.config;

/**
 * Собственное исключение, на случай, если произошла ошибка при чтении конфига
 */
public class InvalidConfigurationException extends Exception {
    public InvalidConfigurationException(String message) {
        super(message);
    }
}
