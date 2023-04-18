package one.parser.error;

public class OneSyntaxException extends OneParseException {

    public OneSyntaxException() { }

    public OneSyntaxException(String message) {
        super(message);
    }

    public OneSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public OneSyntaxException(Throwable cause) {
        super(cause);
    }

    public OneSyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
