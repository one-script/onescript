package one.parser.rule.expr;

import one.ast.expr.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.util.BiOpSpec;

import java.util.Set;

@SuppressWarnings("rawtypes")
public class RTerm extends ParserRule<NExpression> {

    public RTerm() {
        super("term", "exprTerm", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    /** Binary operator chain specification. */
    static final BiOpSpec BI_OP_SPEC = new BiOpSpec(Set.of(OneOperator.MUL, OneOperator.DIV, OneOperator.MOD),
            context -> context.tryParseNext("exprFactor"));

    @Override
    public NExpression parseNode(ParseContext context) {
        // parse binary op //
        return BI_OP_SPEC.parse(context);
    }

}
