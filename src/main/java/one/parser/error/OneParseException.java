package one.parser.error;

import one.parser.util.StringLocatable;
import one.parser.util.StringLocation;

import java.io.PrintStream;

public class OneParseException extends RuntimeException
        implements StringLocatable {

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

    // creates an ansi code from the given
    // format and then returns it escaped if the
    // boolean is true
    private static String ac(String c, boolean fmt) {
        if (!fmt)
            return "";
        return "\u001B[" + c + "m";
    }

    public String getMessageFancy(boolean fmt) {
        return ac("31", fmt) + ac("1", fmt) + getClass().getSimpleName() + ": " + ac("0", fmt) +
                super.getMessage() + "\n" +
                (location == null ? "  from an unknown source" : location.toStringFancy(fmt, 2));
    }

    public void printFancy(PrintStream stream,
                           boolean fmt,
                           boolean stackTrace) {
        stream.println(getMessageFancy(fmt));

        if (stackTrace) {
            for (StackTraceElement element : getStackTrace()) {
                stream.println("    at " + element);
            }
        }
    }

}
