package one.parser.token;

import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.util.StringLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Built-in token types.
 *
 * These token types are usually built into
 * the lexer for performance reasons. For that reason
 * most parsing methods do not have as many checks to
 * confirm the validity of a token so use these manually
 * with caution.
 */
public class Tokens {

    /**
     * A double-quoted string literal.
     *
     * Example: {@code "hello"} -> {@code stringLiteral(hello)}
     */
    public static final TokenType<String> STRING_LITERAL = new TokenType<>("literalString") {
        @Override
        public Token<String> parseToken(LexContext context) {
            int startIndex = context.index();

            final StringBuilder b = new StringBuilder();
            context.next();
            char c;
            while (!context.ended() && (c = context.curr()) != '"') {
                if (c == '\\') {
                    char c1 = context.next();
                    b.append((switch (c1) {
                        case 't' -> '\t';
                        case 'n' -> '\n';
                        case 'r' -> '\r';
                        default -> c1;
                    }));
                    // skip the appended character
                    context.next();
                    continue;
                }

                b.append(c);
                context.next();
            }

            // skip "
            int endIndex = context.index();
            context.next();

            return new Token<>(STRING_LITERAL, b.toString())
                    .setLocation(new StringLocation(context.getFile(), context.str(), startIndex, endIndex));
        }
    };

    /**
     * A number literal, can be decimal or integer.
     * At the parsing stage numbers are always stored as
     * doubles.
     *
     * Example: {@code 123} -> {@code numberLiteral(123.0)}
     */
    public static final TokenType<Double> NUMBER_LITERAL = new TokenType<>("literalNumber") {
        @Override
        public Token<Double> parseToken(LexContext context) {
            int startIndex = context.index();
            double d = context.collectDouble();
            int endIndex = context.index();

            return new Token<>(NUMBER_LITERAL, d)
                    .setLocation(new StringLocation(context.getFile(), context.str(), startIndex, endIndex));
        }
    };

    /**
     * An identifier.
     *
     * Can not be parsed directly because keywords are
     * parsed in the same function.
     */
    public static final TokenType<String> IDENTIFIER = TokenType.noParser("identifier");

    public static final TokenType<Void> LEFT_PAREN = TokenType.oneChar("leftParen", '(');
    public static final TokenType<Void> RIGHT_PAREN = TokenType.oneChar("rightParen", ')');

    public static final TokenType<Void> SEMICOLON = TokenType.oneChar("semicolon", ';');
    public static final TokenType<Void> COLON = TokenType.oneChar("colon", ';');

    /* Keywords */

    public static void registerAllKeywords(OneParser parser) {
        try {
            for (Field f : Tokens.class.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) || f.getType() != Keyword.class) continue;
                parser.addKeywordType((Keyword) f.get(null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Variables
    public static final Keyword LET = TokenType.staticKeyword("let");

    // Fields
    public static final Keyword VAL = TokenType.staticKeyword("val");

    // Methods
    public static final Keyword FUNC = TokenType.staticKeyword("func");


}
