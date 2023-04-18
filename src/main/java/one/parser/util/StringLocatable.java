package one.parser.util;

/**
 * An object which can have a location in a string.
 */
public interface StringLocatable {

    StringLocation getLocation();

    StringLocatable setLocation(StringLocation location);

}
