package one.parser.rule;

import one.ast.NBinaryOp;
import one.ast.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.token.TokenType;
import one.parser.util.BiOpSpec;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class RExpression extends ParserRule<NExpression> {

    public RExpression() {
        super("expr", "exprExpr", 0);
    }

    /** Binary operator chain specification. */
    static final BiOpSpec BI_OP_SPEC = new BiOpSpec(Set.of(OneOperator.ADD, OneOperator.SUB),
            context -> context.tryParseNext("exprTerm"));

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NExpression parseNode(ParseContext context) {
        // parse binary op //
        return BI_OP_SPEC.parse(context);
    }

}
