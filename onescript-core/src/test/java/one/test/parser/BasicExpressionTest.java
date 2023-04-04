package one.test.parser;

import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.ParseContext;
import one.util.Sequence;
import org.junit.jupiter.api.Test;

public class BasicExpressionTest {

    @Test
    void test_ParseBasicExpr() {
        final OneParser parser = new OneParser();
        final String src = """
                6 + 8 * 2
                """;

        LexContext lexContext = parser.lex(new LexContext(parser, src, 0, "src"));
        ParseContext parseContext = parser.parse(new ParseContext(parser,
                Sequence.ofList(lexContext.getTokens()),
                "expr"));

        System.out.println(parseContext.getRootNode());
    }

}