package one.ast.expr;

import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.parser.token.Tokens;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Grouped type for any type of method call.
 */
public abstract class NCall extends NExpression<Object> {

    /**
     * Parses the parameters and other properties for
     * the call from the given context.
     */
    public static <C extends NCall> C parseCall(ParseContext context,
                                                C node) {
        while (context.currentType() != Tokens.RIGHT_PAREN) {
            // check for named
            if (context.currentType() == Tokens.IDENTIFIER &&
                    context.peek(1).getType() == Tokens.ASSIGN) {
                String name = context.current().getValueAs();
                context.next(); // skip =

                // parse parameter value
                NExpression<?> expr = context.tryParseNext("exprExpr");

                node.addNamedParam(name, expr);
            } else {
                node.addOrderedParam(context.tryParseNext("exprExpr"));
            }

            if (context.currentType() == Tokens.RIGHT_PAREN)
                break;
            if (context.currentType() != Tokens.COMMA)
                throw context.endOrHere(new OneParseException("Expected right paren or comma"));
        }

        context.next(); // skip )

        return node;
    }

    /** The specified ordered parameters. */
    private List<NExpression<?>> orderedParams = new ArrayList<>();

    /** The specified named parameters. */
    private Map<String, NExpression<?>> namedParams = new HashMap<>();

    public List<NExpression<?>> getOrderedParams() {
        return orderedParams;
    }

    public NCall setOrderedParams(List<NExpression<?>> orderedParams) {
        this.orderedParams = orderedParams;
        return this;
    }

    public NCall addOrderedParam(NExpression<?> expression) {
        orderedParams.add(expression);
        return this;
    }

    public Map<String, NExpression<?>> getNamedParams() {
        return namedParams;
    }

    public NCall setNamedParams(Map<String, NExpression<?>> namedParams) {
        this.namedParams = namedParams;
        return this;
    }

    public NCall addNamedParam(String name, NExpression<?> expression) {
        namedParams.put(name, expression);
        return this;
    }

    @Override
    public String getDataString() {
        return "ordered: " + orderedParams + ", named: " + namedParams;
    }

}
