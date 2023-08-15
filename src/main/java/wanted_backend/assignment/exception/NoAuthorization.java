package wanted_backend.assignment.exception;

public class NoAuthorization extends RuntimeException{
    public NoAuthorization() {
    }

    public NoAuthorization(String message) {
        super(message);
    }

    public NoAuthorization(String message, Throwable cause) {
        super(message, cause);
    }

    public NoAuthorization(Throwable cause) {
        super(cause);
    }

}
