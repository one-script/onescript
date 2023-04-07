package one.parser.rule;

import one.ast.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.util.BiOpSpec;

import java.util.Set;

@SuppressWarnings("rawtypes")
public class RFactor extends ParserRule<NExpression> {

    public RFactor() {
        super("factor", "exprFactor", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    /** Binary operator chain specification. */
    static final BiOpSpec BI_OP_SPEC = new BiOpSpec(Set.of(OneOperator.POW),
            context -> context.tryParseNext("exprBase"));

    @Override
    public NExpression parseNode(ParseContext context) {
        return BI_OP_SPEC.parse(context);
    }

}
