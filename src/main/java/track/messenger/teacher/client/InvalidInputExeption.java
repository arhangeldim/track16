package track.messenger.teacher.client;


public class InvalidInputExeption extends Exception {
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
