package one.ast.expr;

import one.lang.OneOperator;

@SuppressWarnings("rawtypes")
public class NUnaryOp extends NExpression {

    public NUnaryOp() { }

    public NUnaryOp(OneOperator operator, NExpression operand) {
        this.operator = (OneOperator.Unary) operator;
        this.operand = operand;
    }

    /** The operator to apply. */
    private OneOperator.Unary operator;
    /** The operand to operate on. */
    private NExpression operand;

    @Override
    public String getTypeName() {
        return "unaryOp";
    }

    public OneOperator getOperator() {
        return operator;
    }

    public NUnaryOp setOperator(OneOperator operator) {
        this.operator = (OneOperator.Unary) operator;
        return this;
    }

    public NExpression getOperand() {
        return operand;
    }

    public NUnaryOp setOperand(NExpression operand) {
        this.operand = operand;
        return this;
    }

    @Override
    public String getDataString() {
        return operator + ": " + operand;
    }

    @Override
    public Object evaluateSimple() {
        return operator.evaluateSimple(operand.evaluateSimple());
    }

}
