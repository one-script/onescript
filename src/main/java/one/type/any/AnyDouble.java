package one.type.any;

import one.type.Any;

public class AnyDouble extends AnyBoxable {

    public static AnyDouble of(double value) {
        return new AnyDouble(value);
    }

    public AnyDouble(double value) {
        this.value = value;
    }

    /** The contained value. */
    private final double value;

    @Override
    public Any add(Any other) {
        return of(value + other.asDouble());
    }

    @Override
    public Any sub(Any other) {
        return of(value - other.asDouble());
    }

    @Override
    public Any mul(Any other) {
        return of(value * other.asDouble());
    }

    @Override
    public Any div(Any other) {
        return of(value / other.asDouble());
    }

    @Override
    public Any shl(Any other) {
        throw new UnsupportedOperationException("Attempt to shift double");
    }

    @Override
    public Any shr(Any other) {
        throw new UnsupportedOperationException("Attempt to shift double");
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index double");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index double");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index double");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index double");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index double");
    }

    @Override
    public boolean asBool() {
        return value == 1.0;
    }

    @Override
    public byte asByte() {
        return (byte) value;
    }

    @Override
    public char asChar() {
        return (char) value;
    }

    @Override
    public short asShort() {
        return (short) value;
    }

    @Override
    public int asInt() {
        return (int) value;
    }

    @Override
    public long asLong() {
        return (long) value;
    }

    @Override
    public float asFloat() {
        return (float) value;
    }

    @Override
    public double asDouble() {
        return value;
    }

}
