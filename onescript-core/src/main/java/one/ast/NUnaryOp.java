package one.ast;

import one.lang.OneOperator;

public class NUnaryOp extends NExpression {

    public NUnaryOp() { }

    public NUnaryOp(OneOperator operator, NExpression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    /** The operator to apply. */
    private OneOperator operator;
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
        this.operator = operator;
        return this;
    }

    public NExpression getOperand() {
        return operand;
    }

    public NUnaryOp setOperand(NExpression operand) {
        this.operand = operand;
        return this;
    }

}
