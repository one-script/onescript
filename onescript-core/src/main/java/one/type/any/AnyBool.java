package one.type.any;

import one.type.Any;
import one.type.OneClassType;
import one.util.asm.JavaField;

public class AnyBool implements Any {

    public static final AnyBool TRUE  = new AnyBool(true);
    public static final AnyBool FALSE = new AnyBool(false);

    public static final JavaField TRUE_FIELD  = JavaField.find(AnyBool.class, "TRUE");
    public static final JavaField FALSE_FIELD = JavaField.find(AnyBool.class, "FALSE");

    /** The value. */
    private final boolean value;

    AnyBool(boolean value) {
        this.value = value;
    }

    @Override
    public Any add(Any other) {
        return null;
    }

    @Override
    public Any sub(Any other) {
        return null;
    }

    @Override
    public Any mul(Any other) {
        return null;
    }

    @Override
    public Any div(Any other) {
        return null;
    }

    @Override
    public Any shl(Any other) {
        return null;
    }

    @Override
    public Any shr(Any other) {
        return null;
    }

    @Override
    public Any indexGet(Any other) {
        return null;
    }

    @Override
    public Any indexSet(Any other) {
        return null;
    }

    @Override
    public Any memberGet(String name) {
        return null;
    }

    @Override
    public Any memberSet(String name) {
        return null;
    }

    @Override
    public Any memberCall(String name) {
        return null;
    }

    @Override
    public boolean asBool() {
        return false;
    }

    @Override
    public byte asByte() {
        return (byte) (value ? 1 : 0);
    }

    @Override
    public char asChar() {
        return value ? '1' : '0';
    }

    @Override
    public short asShort() {
        return (short) (value ? 1 : 0);
    }

    @Override
    public int asInt() {
        return value ? 1 : 0;
    }

    @Override
    public long asLong() {
        return value ? 1 : 0;
    }

    @Override
    public float asFloat() {
        return value ? 1 : 0;
    }

    @Override
    public double asDouble() {
        return value ? 1 : 0;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T as(OneClassType<T> type) {
        if (type.getLoadedJVMClass() == Boolean.class)
            return (T) Boolean.valueOf(value);
        throw new ClassCastException("Bool to " + type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T as(Class<T> type) {
        if (type == Boolean.class)
            return (T) Boolean.valueOf(value);
        throw new ClassCastException("Bool to " + type);
    }

}
