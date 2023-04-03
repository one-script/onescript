package one.parser;

import one.parser.token.Token;
import one.parser.token.TokenType;
import one.parser.util.StringReader;

import java.util.ArrayList;
import java.util.List;

/**
 * A context for lexical analysis.
 *
 * Extends {@link StringReader} for convenience in usage.
 */
public class LexContext extends StringReader {

    /**
     * The parser which issued this context.
     */
    private final OneParser parser;

    /**
     * The list of resulting tokens.
     */
    private final List<Token<?>> tokens = new ArrayList<>();

    public LexContext(OneParser parser,
                      String string,
                      int index,
                      String file) {
        super(string, index);
        this.parser = parser;
        inFile(file);
    }

    public OneParser getParser() {
        return parser;
    }

    public List<Token<?>> getTokens() {
        return tokens;
    }

    public <T> Token<T> addToken(TokenType<T> type) {
        Token<T> tk = newToken(type);
        tokens.add(tk);
        return tk;
    }

    public <T> Token<T> addToken(TokenType<T> type, T value) {
        Token<T> tk = newToken(type, value);
        tokens.add(tk);
        return tk;
    }

    public <T> Token<T> addToken(Token<T> token) {
        tokens.add(token);
        return token;
    }

}
