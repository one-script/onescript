package one.parser.token;

import one.parser.LexContext;
import one.parser.util.StringLocation;

/**
 * These are token types which are built into
 * the lexer for performance reasons. For that reason
 * the parsing methods do not have as many checks to
 * confirm the validity of a token so use these manually
 * with caution.
 */
public class BuiltInTokenTypes {

    /**
     * A double-quoted string literal.
     *
     * Example: {@code "hello"} -> {@code stringLiteral(hello)}
     */
    public static TokenType<String> STRING_LITERAL = new TokenType<>("literalString") {
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
    public static TokenType<Double> NUMBER_LITERAL = new TokenType<>("literalNumber") {
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
    public static TokenType<String> IDENTIFIER = TokenType.noParser("identifier");

    public static TokenType<Void> LEFT_PAREN = TokenType.oneChar("leftParen", '(');
    public static TokenType<Void> RIGHT_PAREN = TokenType.oneChar("rightParen", ')');

}
