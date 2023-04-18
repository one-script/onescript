package one.test.parser;

import one.ast.ASTVisualizer;
import one.parser.LexContext;
import one.parser.OneParser;
import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.util.Sequence;
import org.junit.jupiter.api.Test;

public class BasicExpressionTest {

    @Test
    void test_ParseBasicExpr() {
        try {
            final OneParser parser = new OneParser();
            final String src = """
                    @MyAnnotation
                    pub static class Int : Number {
                        pub int myInt = 69
                        pub int myFunc() {
                            return 2f + 6D * 9s - 8b;
                        }
                    }
                    """;

            LexContext lexContext = parser.lex(new LexContext(parser, src, 0, "src"));
            System.out.println(lexContext.getTokens());
            ParseContext parseContext = parser.parse(
                    new ParseContext(parser,
                            Sequence.ofList(lexContext.getTokens()),
                            "classBody")

                            // TODO: fix issue when handling string and double constants:
                            //  it tries to evaluate but cant because cast errors
                            .setOptimizeConstants(false)
            );

            System.out.println(ASTVisualizer.newLineToString(parseContext.getRootNode(), true));
        } catch (OneParseException e) {
            e.printFancy(System.err, true, false);
        }
    }

}
