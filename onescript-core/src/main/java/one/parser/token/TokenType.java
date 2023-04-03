package one.parser.token;

import one.parser.LexContext;

import java.util.Objects;

/**
 * Describes the type of a token and how to parse it.
 *
 * @param <T> The type of captured value.
 */
public abstract class TokenType<T> {

    /**
     * The internal identifier/name of this token type.
     */
    final String name;

    public TokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "tktype(" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenType<?> tokenType = (TokenType<?>) o;
        return Objects.equals(name, tokenType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Tries to parse a token in the given context.
     *
     * This is expected to consume the token if successful,
     * otherwise to return null and stay at the current position.
     *
     * @param context The context.
     * @return The token or null if it failed.
     */
    public abstract Token<T> parseToken(LexContext context);

}
