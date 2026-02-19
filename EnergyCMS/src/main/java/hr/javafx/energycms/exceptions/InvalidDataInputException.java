package hr.javafx.energycms.exceptions;

public class InvalidDataInputException extends Exception {
    public InvalidDataInputException(String message) {
        super(message);
    }

    public InvalidDataInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDataInputException(Throwable cause) {
        super(cause);
    }

    public InvalidDataInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public InvalidDataInputException() {}
}
