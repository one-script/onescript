package one.type.any;

import one.type.Any;

public class AnyFloat extends AnyBoxable {

    public static AnyFloat of(float value) {
        return new AnyFloat(value);
    }

    public AnyFloat(float value) {
        this.value = value;
    }

    /** The contained value. */
    private final float value;

    @Override
    public Any add(Any other) {
        return of(value + other.asFloat());
    }

    @Override
    public Any sub(Any other) {
        return of(value - other.asFloat());
    }

    @Override
    public Any mul(Any other) {
        return of(value * other.asFloat());
    }

    @Override
    public Any div(Any other) {
        return of(value / other.asFloat());
    }

    @Override
    public Any shl(Any other) {
        throw new UnsupportedOperationException("Attempt to shift float");
    }

    @Override
    public Any shr(Any other) {
        throw new UnsupportedOperationException("Attempt to shift float");
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index float");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index float");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index float");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index float");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index float");
    }

    @Override
    public boolean asBool() {
        return value == 1f;
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
        return value;
    }

    @Override
    public double asDouble() {
        return value;
    }

}
