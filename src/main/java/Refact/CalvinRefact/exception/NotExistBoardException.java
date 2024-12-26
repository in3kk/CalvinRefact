package Refact.CalvinRefact.exception;

public class NotExistBoardException extends RuntimeException{
    public NotExistBoardException() {
    }

    public NotExistBoardException(String message) {
        super(message);
    }

    public NotExistBoardException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistBoardException(Throwable cause) {
        super(cause);
    }

    public NotExistBoardException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
