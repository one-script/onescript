package one.parser.rule;

import one.ast.NBinaryOp;
import one.ast.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.token.Token;
import one.parser.token.TokenType;

public class RTerm extends ParserRule<NExpression> {

    public RTerm() {
        super("term", "exprTerm", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NExpression parseNode(ParseContext context) {
        // parse binary op //
        NExpression node = context.tryParseNext("exprFactor", NExpression.class);
        if (node != null) {
            Token<?> currentToken;
            OneOperator currentOp = null;
            while ((currentToken = context.current()).getType() == TokenType.OPERATOR &&
                    (currentOp = currentToken.getValueAs()) == OneOperator.MUL ||
                    currentOp == OneOperator.DIV) {
                context.next();
                NExpression right = context.tryParseNext("exprFactor", NExpression.class);
                node = new NBinaryOp(currentOp, node, right);
            }
        }

        return node;
    }

}
