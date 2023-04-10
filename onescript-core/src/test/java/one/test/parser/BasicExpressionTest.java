package one.test.parser;

import one.ast.expr.NExpression;
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
                @MyAnnotation
                class Int : Number {
                    
                }
                """;

        LexContext lexContext = parser.lex(new LexContext(parser, src, 0, "src"));
        ParseContext parseContext = parser.parse(
                new ParseContext(parser,
                        Sequence.ofList(lexContext.getTokens()),
                        "classBody")
                .setOptimizeConstants(true)
        );

        System.out.println(lexContext.getTokens());
        System.out.println(parseContext.getRootNode());
    }

}
