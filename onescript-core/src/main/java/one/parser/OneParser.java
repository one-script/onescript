package one.parser;

import one.parser.token.BuiltInTokenTypes;
import one.parser.token.TokenType;
import one.parser.util.StringReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The primary OneScript parser.
 *
 *
 */
public class OneParser {

    /**
     * All registered token types mapped by name.
     */
    final Map<String, TokenType<?>> tokenTypes = new HashMap<>();
    final List<TokenType<?>> tokenTypeList = new ArrayList<>();

    public OneParser addTokenType(TokenType<?> type) {
        tokenTypes.put(type.getName(), type);
        tokenTypeList.add(type);
        return this;
    }

    public OneParser removeTokenType(TokenType<?> type) {
        tokenTypes.remove(type.getName());
        tokenTypeList.remove(type);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> TokenType<T> getTokenType(String name) {
        return (TokenType<T>) tokenTypes.get(name);
    }

    /**
     * Lexically analyzes a string given the context.
     *
     * @param context The context.
     * @return The context.
     */
    public LexContext lex(final LexContext context) {
        while (context.curr() != StringReader.EOF) {
            context.consumeWhitespace();

            // match string literal
            if (context.curr() == '"') {
                context.addToken(BuiltInTokenTypes.STRING_LITERAL.parseToken(context));
            }

            // match number literal
            // we can match in radix 10 because other bases
            // will start with 0cNUMBER, and 0 is a base-10 digit
            if (StringReader.isDigit(context.curr(), 10)) {
                context.addToken(BuiltInTokenTypes.NUMBER_LITERAL.parseToken(context));
            }

            // todo: throw exception in this case
            context.next();
        }

        return context;
    }

}
