package one.test.parser;

import one.parser.LexContext;
import one.parser.OneParser;
import org.junit.jupiter.api.Test;

public class BasicLexerTest {

    @Test
    void test() {
        final String src = """
                "d"  7 "b" "a" 887755
                """;
        final OneParser parser = new OneParser();
        final LexContext context = new LexContext(parser, src, 0, "src");

        System.out.println(parser.lex(context).getTokens());
    }

}
