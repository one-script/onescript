package one.lang;

import one.parser.token.Token;
import one.parser.token.TokenType;

import java.util.Arrays;

public class Operator {

    // Arithmetic
    public static final Operator ADD = new Operator("+");
    public static final Operator SUB = new Operator("-");
    public static final Operator MUL = new Operator("*");
    public static final Operator DIV = new Operator("/");

    //////////////////////////////////////////////

    /**
     * The parsable aliases for this operator.
     * Used for matching and parsing it from source.
     */
    private final String[] aliases;

    private final String primaryAlias;

    public Operator(String... aliases) {
        if (aliases.length == 0)
            throw new IllegalArgumentException("Operators require at least one alias");
        this.aliases = aliases;
        this.primaryAlias = aliases[0];
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getPrimaryAlias() {
        return primaryAlias;
    }

    public Token<Operator> createToken() {
        return new Token<>(TokenType.OPERATOR, this);
    }

    @Override
    public String toString() {
        return primaryAlias;
    }

}
