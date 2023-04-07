package one.type.primitive;

import one.type.OneStrongType;
import one.type.OneType;
import org.objectweb.asm.Type;

/**
 * Denotes a primitive OneScript type.
 */
public abstract class OnePrimitiveType extends OneStrongType {

    /** The Java primitive class. */
    private final Class<?> klass;
    /** The ASM type. */
    private final Type asmType;

    protected OnePrimitiveType(String name,
                               Class<?> klass) {
        super(name);
        this.klass = klass;
        this.asmType = Type.getType(klass);
    }

    @Override
    public Class<?> getLoadedJVMClass() {
        return klass;
    }

    @Override
    public String getJVMClassName() {
        return klass.getName();
    }

    @Override
    public Type getAsmType() {
        return asmType;
    }

}
