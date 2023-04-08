package one.test.parser;

import one.ast.NExpression;
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
                class Int : BoxedNumber {
                    val: int value;
                    
                    pub this(int: v) {
                        this.value = v;
                    }
                    
                    pub static this(String: str) int {
                        return Integer.parseInt(str);
                    }
                }
                
                int myInt = Int("69")
                """;

        LexContext lexContext = parser.lex(new LexContext(parser, src, 0, "src"));
        ParseContext parseContext = parser.parse(
                new ParseContext(parser,
                        Sequence.ofList(lexContext.getTokens()),
                        "expr")
                .setOptimizeConstants(true)
        );

        System.out.println(lexContext.getTokens());
        System.out.println(parseContext.getRootNode());
        System.out.println(parseContext.getRootNode().<NExpression>as().evaluateSimple());
    }

}
