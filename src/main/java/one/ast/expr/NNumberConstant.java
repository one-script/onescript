package one.ast.expr;

public class NNumberConstant extends NConstant<Double> {

    public NNumberConstant() { }

    public NNumberConstant(Double value) {
        super(value);
    }

    public NNumberConstant(double value) {
        this(Double.valueOf(value));
    }

    @Override
    public String getTypeName() {
        return "numberConstant";
    }

}
