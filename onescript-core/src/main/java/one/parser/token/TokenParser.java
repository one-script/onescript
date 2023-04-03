package one.parser.token;

import one.parser.LexContext;

/**
 * Provides the ability to parse tokens.
 */
public interface TokenParser<T> {

    /**
     * Tries to parse a token in the given context.
     *
     * This is expected to consume the token if successful,
     * otherwise to return null and stay at the current position.
     *
     * @param context The context.
     * @return The token or null if it failed.
     */
    Token<T> parseToken(LexContext context);

}
