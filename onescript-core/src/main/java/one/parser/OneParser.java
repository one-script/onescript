package one.parser;

import one.parser.token.BuiltInTokenTypes;
import one.parser.token.Token;
import one.parser.token.TokenParser;
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
    final List<TokenParser<?>> tokenParsers = new ArrayList<>();

    public OneParser addTokenType(TokenType<?> type) {
        tokenTypes.put(type.getName(), type);
        tokenParsers.add(type);
        return this;
    }

    public OneParser removeTokenType(TokenType<?> type) {
        tokenTypes.remove(type.getName());
        tokenParsers.remove(type);
        return this;
    }

    public OneParser addTokenParser(TokenParser<?> parser) {
        tokenParsers.add(parser);
        return this;
    }

    public OneParser removeTokenParser(TokenParser<?> parser) {
        tokenParsers.remove(parser);
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

            /* trail-and-error all remaining non-builtin token parsers */
            Token<?> tk = null;
            for (TokenParser<?> parser : tokenParsers) {
                tk = parser.parseToken(context);
            }

            if (tk == null) {
                // todo: throw error
                context.next();
                continue;
            } else {
                context.addToken(tk);
            }
        }

        return context;
    }

}
