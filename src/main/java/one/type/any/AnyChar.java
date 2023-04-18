package one.type.any;

import one.type.Any;

public class AnyChar extends AnyBoxable {

    public static AnyChar of(char value) {
        return new AnyChar(value);
    }

    public AnyChar(char value) {
        this.value = value;
    }

    /** The contained value. */
    private final char value;

    @Override
    public Any add(Any other) {
        return of((char) (value + other.asChar()));
    }

    @Override
    public Any sub(Any other) {
        return of((char) (value - other.asChar()));
    }

    @Override
    public Any mul(Any other) {
        return of((char) (value * other.asChar()));
    }

    @Override
    public Any div(Any other) {
        return of((char) (value / other.asChar()));
    }

    @Override
    public Any shl(Any other) {
        return of((char) (value << other.asChar()));
    }

    @Override
    public Any shr(Any other) {
        return of((char) (value >> other.asChar()));
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index char");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index char");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index char");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index char");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index char");
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
        return value;
    }

    @Override
    public short asShort() {
        return (short) value;
    }

    @Override
    public int asInt() {
        return value;
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
