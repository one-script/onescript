package one.type.any;

import one.type.Any;

public class AnyShort extends AnyBoxable {

    public static AnyShort of(short value) {
        return new AnyShort(value);
    }

    /** The value as a short. */
    private final short value;

    public AnyShort(short value) {
        this.value = value;
    }

    @Override
    public Any add(Any other) {
        short sv = other.asShort();
        if (sv == 0) return this;
        return of((short) (value + sv));
    }

    @Override
    public Any sub(Any other) {
        short sv = other.asShort();
        if (sv == 0) return this;
        return of((short) (value - sv));
    }

    @Override
    public Any mul(Any other) {
        short sv = other.asShort();
        if (sv == 1) return this;
        return of((short) (value * sv));
    }

    @Override
    public Any div(Any other) {
        short sv = other.asShort();
        if (sv == 1) return this;
        return of((short) (value / sv));
    }

    @Override
    public Any shl(Any other) {
        short sv = other.asShort();
        if (sv == 0) return this;
        return of((short) (value << sv));
    }

    @Override
    public Any shr(Any other) {
        short sv = other.asShort();
        if (sv == 0) return this;
        return of((short) (value >> sv));
    }

    @Override
    public Any indexGet(Any other) {
        throw new UnsupportedOperationException("Attempt to index short");
    }

    @Override
    public Any indexSet(Any other) {
        throw new UnsupportedOperationException("Attempt to index short");
    }

    @Override
    public Any memberGet(String name) {
        throw new UnsupportedOperationException("Attempt to index short");
    }

    @Override
    public Any memberSet(String name) {
        throw new UnsupportedOperationException("Attempt to index short");
    }

    @Override
    public Any memberCall(String name, Any... args) {
        throw new UnsupportedOperationException("Attempt to index short");
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
