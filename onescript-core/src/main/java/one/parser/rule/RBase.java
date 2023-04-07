package one.parser.rule;

import one.ast.NConstant;
import one.ast.NExpression;
import one.ast.NUnaryOp;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.parser.token.Tokens;
import one.parser.token.TokenType;

@SuppressWarnings("rawtypes")
public class RBase extends ParserRule<NExpression> {

    public RBase() {
        super("base", "exprBase", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NExpression parseNode(ParseContext context) {
        // check for prefix unary operators
        if (context.currentType() == TokenType.OPERATOR) {
            OneOperator currentOp = context.current().getValueAs();
            OneOperator unaryOp = currentOp.toPrefixUnary();
            if (unaryOp == null)
                throw context.endOrHere(new OneParseException("operator " + currentOp + " does not apply as a prefix unary operator"));
            context.next();
            // TODO: maybe do this through rules by calling ParseContext#tryParseNext
            //  once ive optimized that, as this is unintuitive for developers
            NExpression factor =
                    /* note that this refers to RBase#parseNode,
                       so this will parse another factor, not any applicable rule */
                    this.parseNode(context);

            NExpression node = new NUnaryOp(unaryOp, factor);

            // perform constant optimization
            if (context.optimizeConstants() && factor instanceof NConstant) {
                node = NConstant.of(node.evaluateSimple());
            }

            return node;
        }

        /* collect initial value */
        NExpression value;

        // check for literal
        if ((value = context.tryParseNext("literal", NExpression.class)) != null) {

        } else if /* check for expression */ (context.currentType() == Tokens.LEFT_PAREN) {
            context.next();
            value = context.tryParseNext("exprExpr", NExpression.class);
            if (context.currentType() != Tokens.RIGHT_PAREN)
                throw context.endOrHere(new OneParseException("expected right parenthesis to close expression"));
            context.next();
        }

        // check for postfix unary operators
        if (context.currentType() == TokenType.OPERATOR) {
            OneOperator currentOp = context.current().getValueAs();
            OneOperator unaryOp = currentOp.toPostfixUnary();
            if (unaryOp != null) {
                context.next();
                NExpression node = new NUnaryOp(unaryOp, value);

                // perform constant optimization
                if (context.optimizeConstants() && value instanceof NConstant) {
                    node = NConstant.of(node.evaluateSimple());
                }

                return node;
            }
        }

        return value;
    }

}
