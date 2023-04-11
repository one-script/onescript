package one.ast.statement;

import one.ast.expr.NExpression;

public class NReturnStatement extends NStatement {

    public NReturnStatement() { }

    public NReturnStatement(NExpression<?> expression) {
        this.expression = expression;
    }

    /** The expression to return. */
    private NExpression<?> expression;

    public NExpression<?> getExpression() {
        return expression;
    }

    public NReturnStatement setExpression(NExpression<?> expression) {
        this.expression = expression;
        return this;
    }

    @Override
    public String getTypeName() {
        return "return";
    }

    @Override
    public String getDataString() {
        return "expr: " + expression.toString();
    }

}
