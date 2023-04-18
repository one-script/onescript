package one.type.any;

import one.type.Any;

public class AnyLong extends AnyBoxable {

    public static AnyLong of(long value) {
        return new AnyLong(value);
    }

    public AnyLong(long value) {
        this.value = value;
    }

    /** The contained value. */
    private final long value;

    @Override
    public Any add(Any other) {
        return of(value + other.asLong());
    }

    @Override
    public Any sub(Any other) {
        return of(value - other.asLong());
    }

    @Override
    public Any mul(Any other) {
        return of(value * other.asLong());
    }

    @Override
    public Any div(Any other) {
        return of(value / other.asLong());
    }

    @Override
    public Any shl(Any other) {
        return of(value << other.asLong());
    }

    @Override
    public Any shr(Any other) {
        return of(value >> other.asLong());
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index long");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index long");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index long");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index long");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index long");
    }

    @Override
    public boolean asBool() {
        return value == 1;
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
        return value;
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
