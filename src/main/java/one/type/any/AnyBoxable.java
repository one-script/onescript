package one.type.any;

import one.type.Any;
import one.type.OneClass;

/**
 * Any primitive type that can be boxed.
 */
public abstract class AnyBoxable implements Any {

    /** The boxed value type. */
    private final OneClass<?> boxedType;

    protected AnyBoxable(OneClass<?> boxedType) {
        this.boxedType = boxedType;
    }

    /* TODO: remove this after implementing boxed types */ protected AnyBoxable() { this(null); }

    @Override
    public <T> T as(OneClass<T> type) {
        throw new UnsupportedOperationException("TODO: Boxing of primitives");
    }

    @Override
    public <T> T as(Class<T> type) {
        throw new UnsupportedOperationException("TODO: Boxing of primitives");
    }

}
