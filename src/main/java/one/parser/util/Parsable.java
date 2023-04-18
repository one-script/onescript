package one.parser.util;

public interface Parsable {

    /**
     * Get all parsable aliases associated
     * with this parsable.
     *
     * @return The aliases.
     */
    String[] getAliases();

    String getName();

}
