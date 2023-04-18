package one.type.any;

import one.type.Any;
import one.type.OneClassType;
import one.util.asm.JavaClass;
import one.util.asm.JavaMethod;

/**
 * Dynamic implementation of {@link Any} for class values.
 */
public class AnyClass implements Any {

    /** The type of the contained value. */
    private final OneClassType<?> type;
    /** The contained value. */
    private final Object value;

    public AnyClass(Object value,
                    OneClassType<?> type) {
        this.type = type;
        this.value = value;
    }

    /* Store the reflection instances for this class. */
    public static final JavaClass CLASS = JavaClass.of(AnyClass.class);
    public static final JavaMethod CONSTRUCTOR = JavaMethod.findConstructor(AnyClass.class, Object.class, OneClassType.class);
    public static final JavaMethod METHOD_AS = JavaMethod.find(AnyClass.class, "as");

    /*
        TODO
     */

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
        return 0;
    }

    @Override
    public char asChar() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public long asLong() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public <T> T as(OneClassType<T> type) {
        return null;
    }

    @Override
    public <T> T as(Class<T> type) {
        return null;
    }

}
