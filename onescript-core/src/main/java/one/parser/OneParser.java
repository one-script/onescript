package one.parser;

import one.lang.Operator;
import one.parser.error.OneParseException;
import one.parser.token.*;
import one.parser.util.OperatorRegistry;
import one.parser.util.StringLocation;
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

    private static boolean isValidIdentifierStart(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '$';
    }

    private static boolean isValidIdentifierChar(char c) {
        return isValidIdentifierStart(c) || (c >= '0' && c <= '9');
    }

    //////////////////////////////////////

    /**
     * All registered token types mapped by name.
     */
    final Map<String, TokenType<?>> tokenTypes = new HashMap<>();
    final List<TokenParser<?>> tokenParsers = new ArrayList<>();

    /**
     * All registered keyword types.
     */
    final Map<String, KeywordTokenType> keywordTypes = new HashMap<>();

    /**
     * The registered operators.
     */
    final OperatorRegistry operators = new OperatorRegistry();

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

    @SuppressWarnings("unchecked")
    public <T> TokenType<T> getTokenType(String name) {
        return (TokenType<T>) tokenTypes.get(name);
    }

    public OneParser addTokenParser(TokenParser<?> parser) {
        tokenParsers.add(parser);
        return this;
    }

    public OneParser removeTokenParser(TokenParser<?> parser) {
        tokenParsers.remove(parser);
        return this;
    }

    public OneParser addKeywordType(KeywordTokenType type) {
        keywordTypes.put(type.getName(), type);
        return this;
    }

    public OneParser removeKeywordType(KeywordTokenType type) {
        keywordTypes.remove(type.getName());
        return this;
    }

    public KeywordTokenType getKeywordType(String name) {
        return keywordTypes.get(name);
    }

    public OneParser addOperator(Operator operator) {
        operators.insert(operator);
        return this;
    }

    public OneParser removeOperator(Operator operator) {
        operators.remove(operator);
        return this;
    }

    /* Initialize Defaults */
    {
        operators.insert(Operator.ADD);
        operators.insert(Operator.SUB);
        operators.insert(Operator.MUL);
        operators.insert(Operator.DIV);
    }

    /**
     * Lexically analyzes a string given the context.
     *
     * @param context The context.
     * @return The context.
     */
    public LexContext lex(final LexContext context) {
        while (context.hasNext()) {
            context.consumeWhitespace();

            // match string literal
            if (context.curr() == '"') {
                context.addToken(BuiltInTokenTypes.STRING_LITERAL.parseToken(context));
                continue;
            }

            // match number literal
            // we can match in radix 10 because other bases
            // will start with 0cNUMBER, and 0 is a base-10 digit
            if (StringReader.isDigit(context.curr(), 10)) {
                context.addToken(BuiltInTokenTypes.NUMBER_LITERAL.parseToken(context));
                continue;
            }

            // match identifiers and keywords
            // keywords are just reserved identifiers
            if (isValidIdentifierStart(context.curr())) {
                int startIndex = context.index();
                String id = context.collect(OneParser::isValidIdentifierChar);
                int endIndex = context.index() - 1;
                Token<?> token;
                KeywordTokenType kw = getKeywordType(id);
                token = kw != null ? new Token<>(kw) : new Token<>(BuiltInTokenTypes.IDENTIFIER, id);
                context.addToken(token
                        .setLocation(new StringLocation(context.getFile(), context.str(), startIndex, endIndex)));
                continue;
            }

            // try and parse operators
            context.begin();
            int startIndex = context.index();
            Operator operator = context.matchForward(operators);
            if (operator == null) {
                context.reset();
            } else {
                int endIndex = context.index() - 1;
                context.addToken(operator.createToken()
                        .setLocation(new StringLocation(context.getFile(), context.str(), startIndex, endIndex)));
                continue;
            }

            /* trail-and-error all remaining non-builtin token parsers */
            Token<?> tk = null;
            for (TokenParser<?> parser : tokenParsers) {
                tk = parser.parseToken(context);
            }

            if (tk == null) {
                // throw lexer error
                throw context.endOrHere(new OneParseException("unexpected character: '" + context.curr() + "'"));
            } else {
                context.addToken(tk);
            }
        }

        return context;
    }

}
