package Refact.CalvinRefact.exception;

public class InvalidMemberDataException extends RuntimeException{
    public InvalidMemberDataException() {
    }

    public InvalidMemberDataException(String message) {
        super(message);
    }

    public InvalidMemberDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMemberDataException(Throwable cause) {
        super(cause);
    }

    public InvalidMemberDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
