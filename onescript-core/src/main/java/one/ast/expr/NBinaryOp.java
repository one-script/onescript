package one.ast.expr;

import one.lang.OneOperator;

@SuppressWarnings("rawtypes")
public class NBinaryOp extends NExpression {

    public NBinaryOp() { }
    public NBinaryOp(OneOperator operator, NExpression left, NExpression right) {
        this.operator = (OneOperator.Binary) operator;
        this.left = left;
        this.right = right;
    }

    /** The operator to apply. */
    private OneOperator.Binary operator;
    /** The left operand. */
    private NExpression left;
    /** The right operand. */
    private NExpression right;

    @Override
    public String getTypeName() {
        return "binaryOp";
    }

    @Override
    public String getDataString() {
        return operator + " " + left + ", " + right;
    }

    public OneOperator getOperator() {
        return operator;
    }

    public NBinaryOp setOperator(OneOperator operator) {
        this.operator = (OneOperator.Binary) operator;
        return this;
    }

    public NExpression getLeft() {
        return left;
    }

    public NBinaryOp setLeft(NExpression left) {
        this.left = left;
        return this;
    }

    public NExpression getRight() {
        return right;
    }

    public NBinaryOp setRight(NExpression right) {
        this.right = right;
        return this;
    }

    @Override
    public Object evaluateSimple() {
        return operator.evaluateSimple(left.evaluateSimple(), right.evaluateSimple());
    }

}
