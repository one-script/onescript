package one.parser.token;

import one.parser.LexContext;
import one.parser.util.Parsable;

/**
 * Represents a literal token
 */
public class StaticToken extends TokenType<Void> implements Parsable {

    /** The aliases for this token. */
    private final String[] aliases;

    public StaticToken(String name, String... aliases) {
        super(name);
        this.aliases = aliases;
    }

    @Override
    public Token<Void> parseToken(LexContext context) {
        return null;
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

}
