package one.type.any;

import one.type.Any;
import one.type.OneClassType;
import one.util.asm.JavaField;
import one.util.asm.JavaMethod;

public class AnyBool extends AnyBoxable {

    public static final AnyBool TRUE  = new AnyBool(true);
    public static final AnyBool FALSE = new AnyBool(false);

    public static AnyBool of(boolean b) {
        return b ? TRUE : FALSE;
    }

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
    public Any memberCall(String name, Any... args) {
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

}
