package one.type.any;

import one.type.Any;

public class AnyByte extends AnyBoxable {

    // byte instance cache to avoid creating
    // the same instances every time
    private static final AnyByte[] STATIC = new AnyByte[256];

    public static AnyByte of(byte b) {
        int idx = b - Byte.MIN_VALUE;
        AnyByte i = STATIC[idx];
        if (i == null) {
            STATIC[idx] = (i = new AnyByte(b));
        }

        return i;
    }

    /** The contained value. */
    private final byte value;

    public AnyByte(byte value) {
        this.value = value;
    }

    @Override
    public Any add(Any other) {
        return of((byte) (value + other.asByte()));
    }

    @Override
    public Any sub(Any other) {
        return of((byte) (value - other.asByte()));
    }

    @Override
    public Any mul(Any other) {
        return of((byte) (value * other.asByte()));
    }

    @Override
    public Any div(Any other) {
        return of((byte) (value / other.asByte()));
    }

    @Override
    public Any shl(Any other) {
        return of((byte) (value << other.asByte()));
    }

    @Override
    public Any shr(Any other) {
        return of((byte) (value >> other.asByte()));
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index byte");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index byte");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index byte");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index byte");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index byte");
    }

    @Override
    public boolean asBool() {
        return value == 1;
    }

    @Override
    public byte asByte() {
        return value;
    }

    @Override
    public char asChar() {
        return (char) value;
    }

    @Override
    public short asShort() {
        return value;
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
