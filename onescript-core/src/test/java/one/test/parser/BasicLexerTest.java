package one.test.parser;

import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.token.Token;
import one.parser.token.TokenType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

public class BasicLexerTest {

    private List<Token<?>> doLex(String src, Consumer<OneParser> configFunc) {
        final OneParser parser = new OneParser();
        if (configFunc != null)
            configFunc.accept(parser);
        final LexContext context = new LexContext(parser, src, 0, "src");
        return parser.lex(context).getTokens();
    }

    /* Tests */

    record Email(String user, String host) { }

    @Test
    void test_BuiltInTokens() {
        String src = """
                "a"    "b" 4 +  - "c" b  b_t b$rrr b    69E-1 420e3   6 "hhhhh ello      /"
                """;
        System.out.println(doLex(src, null));
    }

    @Test
    void test_RegexTokenTypes() {
        TokenType<Email> emailToken = TokenType.regexBased("email", "(.+)@(.+\\..+)",
                match -> new Email(match.group(1), match.group(2)));
        String src = """
                bing@chilling.com
                """;

        var tokens = doLex(src, p -> p.addTokenType(emailToken));
        System.out.println(tokens);
    }

}
