package one.parser.rule;

import one.ast.NBinaryOp;
import one.ast.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.token.Token;
import one.parser.token.TokenType;

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
        // parse binary op //
        NExpression node = context.tryParseNext("exprTerm", NExpression.class);
        if (node != null) {
            OneOperator currentOp = null;
            while (context.currentType() == TokenType.OPERATOR &&
                    (currentOp = context.current().getValueAs()) == OneOperator.ADD ||
                    currentOp == OneOperator.SUB) {
                context.next();
                NExpression right = context.tryParseNext("exprTerm", NExpression.class);
                node = new NBinaryOp(currentOp, node, right);
            }
        }

        return node;
    }

}
