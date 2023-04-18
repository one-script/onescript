package one.type.primitive;

import one.type.OneStrongType;
import one.util.asm.JavaMethod;
import one.util.asm.MethodBuilder;
import org.objectweb.asm.Type;

/**
 * Denotes a primitive OneScript type.
 */
public abstract class OnePrimitiveType extends OneStrongType {

    /** The Java primitive class. */
    private final Class<?> klass;
    /** The ASM type. */
    private final Type asmType;

    private final JavaMethod anyFromThis;
    private final JavaMethod anyToThis;

    protected OnePrimitiveType(String name,
                               Class<?> klass,
                               JavaMethod anyFromThis,
                               JavaMethod anyToThis) {
        super(name);
        this.klass = klass;
        this.asmType = Type.getType(klass);
        this.anyFromThis = anyFromThis;
        this.anyToThis = anyToThis;
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

    /*
        Compilation
     */

    @Override
    public void compileFromAny(MethodBuilder builder) {
                                      // stack: instance
        anyToThis.putInvoke(builder); // stack: value
    }

    @Override
    public void compileToAny(MethodBuilder builder) {
                                        // stack: value
        anyFromThis.putInvoke(builder); // stack: instance
    }

}
