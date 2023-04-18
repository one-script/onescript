package one.parser.token;

import one.parser.util.RegexValueFactory;

import java.util.regex.MatchResult;

/**
 * A function which creates a token from the
 * given match result.
 *
 * @param <T> The token value type.
 */
@FunctionalInterface
public interface RegexTokenFactory<T> {

    static <T> RegexTokenFactory<T> of(TokenType<T> type, RegexValueFactory<T> factory) {
        return m -> new Token<>(type, factory.parse(m));
    }

    ////////////////////////////////////////////

    Token<T> create(MatchResult result);

}
