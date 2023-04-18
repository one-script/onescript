package one.parser.rule.expr;

import one.ast.expr.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.util.SortedList;

@SuppressWarnings("rawtypes")
public class RExpression extends ParserRule<NExpression> {

    public RExpression() {
        super("expr", "exprExpr", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NExpression parseNode(ParseContext context) {
        // parse operators //
        SortedList<OneOperator.PrioritySet> list = context.getParser().getOperatorSets();
        return list.get(list.size() - 1).parse(context);
    }

}
