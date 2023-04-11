package one.parser.token;

import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.util.StringLocation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.Key;

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
            int startLine = context.currentLine();
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
                    .setLocation(new StringLocation(context.getFile(), context.aStr(), startIndex, endIndex, startLine));
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
            int startLine = context.currentLine();
            int startIndex = context.index();
            double d = context.collectDouble();
            int endIndex = context.index();

            return new Token<>(NUMBER_LITERAL, d)
                    .setLocation(new StringLocation(context.getFile(), context.aStr(), startIndex, endIndex, startLine));
        }
    };

    /**
     * An identifier.
     *
     * Can not be parsed directly because keywords are
     * parsed in the same function.
     */
    public static final TokenType<String> IDENTIFIER = TokenType.noParser("identifier");

    public static void registerAllStatics(OneParser parser) {
        try {
            for (Field f : Tokens.class.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers()) || f.getType() != StaticToken.class) continue;
                parser.addStaticToken((StaticToken) f.get(null));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static final StaticToken LEFT_PAREN = TokenType.literal("leftParen", "(");
    public static final StaticToken RIGHT_PAREN = TokenType.literal("rightParen", ")");
    public static final StaticToken LEFT_BRACE = TokenType.literal("leftBrace", "{");
    public static final StaticToken RIGHT_BRACE = TokenType.literal("rightBrace", "}");
    public static final StaticToken LEFT_ANGLE = TokenType.literal("leftAngle", "<");
    public static final StaticToken RIGHT_ANGLE = TokenType.literal("rightAngle", ">");

    public static final StaticToken SEMICOLON = TokenType.literal("semicolon", ";");
    public static final StaticToken COLON = TokenType.literal("colon", ":");

    public static final StaticToken ASSIGN = TokenType.literal("assign", "=");
    public static final StaticToken DOT = TokenType.literal("dot", ".");
    public static final StaticToken ARROW = TokenType.literal("arrow", "->");
    public static final StaticToken AT = TokenType.literal("at", "@");
    public static final StaticToken COMMA = TokenType.literal("comma", ",");

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

    // Control Flow
    public static final Keyword IF       = TokenType.staticKeyword("if");
    public static final Keyword ELSE     = TokenType.staticKeyword("else");
    public static final Keyword WHILE    = TokenType.staticKeyword("while");
    public static final Keyword FOR      = TokenType.staticKeyword("for");
    public static final Keyword BREAK    = TokenType.staticKeyword("break");
    public static final Keyword CONTINUE = TokenType.staticKeyword("continue");
    public static final Keyword RETURN   = TokenType.staticKeyword("return");

    // Classes
    public static final Keyword CLASS     = TokenType.staticKeyword("class");
    public static final Keyword THIS      = TokenType.staticKeyword("this");
    public static final Keyword INTERFACE = TokenType.staticKeyword("interface");
    public static final Keyword RECORD    = TokenType.staticKeyword("record");

    // Script
    public static final Keyword PACKAGE = TokenType.staticKeyword("package");
    public static final Keyword IMPORT = TokenType.staticKeyword("import");

    // Other
    public static final Keyword AS = TokenType.staticKeyword("as");

}
