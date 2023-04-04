package one.ast;

public class NNumberConstant extends NExpression {

    /** The value. */
    private double value;

    public NNumberConstant(double value) {
        this.value = value;
    }

    @Override
    public String getTypeName() {
        return "numberConstant";
    }

    @Override
    public String getDataString() {
        return Double.toString(value);
    }

    public double getValue() {
        return value;
    }

    public NNumberConstant setValue(double value) {
        this.value = value;
        return this;
    }

}
