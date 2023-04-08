package one.ast.expr;

import java.util.Objects;

public abstract class NConstant<V> extends NExpression<V> {

    public static NConstant<?> of(Object value) {
        if (value instanceof Number number)
            return new NNumberConstant(number.doubleValue());
        if (value instanceof String string)
            return new NStringConstant(string);

        throw new IllegalArgumentException("Unsupported type for constant " + (value == null ? "null" : value.getClass()));
    }

    ///////////////////////////////////////////

    /** The value. */
    private V value;

    public NConstant() { }

    public NConstant(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public NConstant<V> setValue(V value) {
        this.value = value;
        return this;
    }

    @Override
    public String getDataString() {
        return Objects.toString(value);
    }

    @Override
    public V evaluateSimple() {
        return value;
    }

}
