package one.parser.error;

import one.parser.util.StringLocatable;
import one.parser.util.StringLocation;

public class OneParseException extends RuntimeException implements StringLocatable {

    // the string location where the error occurred
    StringLocation location;

    public OneParseException() { }

    public OneParseException(String message) {
        super(message);
    }

    public OneParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public OneParseException(Throwable cause) {
        super(cause);
    }

    public OneParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public OneParseException setLocation(StringLocation location) {
        this.location = location;
        return this;
    }

    @Override
    public StringLocation getLocation() {
        return location;
    }

    public boolean hasLocation() {
        return location != null;
    }

}
