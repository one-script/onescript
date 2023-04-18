package one.type.any;

import one.type.Any;

public class AnyInt extends AnyBoxable {

    public static AnyInt of(int value) {
        return new AnyInt(value);
    }

    public AnyInt(int value) {
        this.value = value;
    }

    /** The contained value. */
    private final int value;

    @Override
    public Any add(Any other) {
        return of(value + other.asInt());
    }

    @Override
    public Any sub(Any other) {
        return of(value - other.asInt());
    }

    @Override
    public Any mul(Any other) {
        return of(value * other.asInt());
    }

    @Override
    public Any div(Any other) {
        return of(value / other.asInt());
    }

    @Override
    public Any shl(Any other) {
        return of(value << other.asInt());
    }

    @Override
    public Any shr(Any other) {
        return of(value >> other.asInt());
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index int");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index int");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index int");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index int");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index int");
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
