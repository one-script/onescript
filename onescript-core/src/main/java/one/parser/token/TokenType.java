package one.parser.token;

import one.parser.LexContext;
import one.parser.util.RegexValueFactory;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * Describes the type of a token and how to parse it.
 *
 * @param <T> The type of captured value.
 */
public abstract class TokenType<T> implements TokenParser<T> {

    public static <T> TokenType<T> fromParser(String name,
                                              TokenParser<T> parser) {
        return new TokenType<>(name) {
            @Override
            public Token<T> parseToken(LexContext context) {
                return parser.parseToken(context);
            }
        };
    }

    public static <T> TokenType<T> createWithParser(String name,
                                                    Function<TokenType<T>, TokenParser<T>> parserProvider) {
        return new TokenType<>(name) {
            TokenParser<T> parser = parserProvider.apply(this);

            @Override
            public Token<T> parseToken(LexContext context) {
                return parser.parseToken(context);
            }
        };
    }

    public static <T> TokenType<T> regexBased(String name,
                                              Pattern pattern,
                                              RegexValueFactory<T> valueFactory) {
        return createWithParser(name, type -> new RegexTokenParser<>(pattern, RegexTokenFactory.of(type, valueFactory)));
    }

    public static <T> TokenType<T> regexBased(String name,
                                              String pattern,
                                              RegexValueFactory<T> valueFactory) {
        return regexBased(name, Pattern.compile(pattern, RegexTokenParser.DEFAULT_PATTERN_FLAGS), valueFactory);
    }

    ////////////////////////////////////////////

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

}
