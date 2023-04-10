package one.parser.util;

import one.ast.expr.NBinaryOp;
import one.ast.expr.NConstant;
import one.ast.expr.NExpression;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.token.TokenType;

import java.util.Set;
import java.util.function.Function;

/**
 * Utility for uniting all repeatable binary operator parsing.
 */
@SuppressWarnings("rawtypes")
public record BiOpSpec(Set<OneOperator> operators, Function<ParseContext, NExpression> operandSupplier) {

    /**
     * Parses a repeating binary operator expression
     * from the given context.
     *
     * Handles optimization automatically.
     *
     * @param context The context.
     * @return The bi-op node.
     */
    public NExpression parse(ParseContext context) {
        NExpression node = operandSupplier.apply(context);
        if (node != null) {
            OneOperator currentOp;
            while (context.currentType() == TokenType.OPERATOR &&
                    operators.contains(currentOp = context.current().getValueAs())) {
                context.next();
                NExpression right = operandSupplier.apply(context);
                NBinaryOp bNode = new NBinaryOp(currentOp, node, right);
                node = bNode;

                // perform constant optimization
                if (context.optimizeConstants() &&
                        bNode.getLeft() instanceof NConstant &&
                        right instanceof NConstant) {
                    try {
                        node = NConstant.of(node.evaluateSimple());
                    } catch (Exception ignored) { }
                }
            }
        }

        return node;
    }

}
