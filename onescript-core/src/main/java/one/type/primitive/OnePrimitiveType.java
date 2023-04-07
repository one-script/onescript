package one.type.primitive;

import one.type.Any;
import one.type.OneStrongType;
import one.type.OneType;
import one.util.asm.JavaMethod;
import one.util.asm.MethodBuilder;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * Denotes a primitive OneScript type.
 */
public abstract class OnePrimitiveType extends OneStrongType {

    /** The Java primitive class. */
    private final Class<?> klass;
    /** The ASM type. */
    private final Type asmType;

    private final JavaMethod anyConstructor;
    private final JavaMethod anyToThis;

    protected OnePrimitiveType(String name,
                               Class<?> klass,
                               JavaMethod anyConstructor,
                               JavaMethod anyToThis) {
        super(name);
        this.klass = klass;
        this.asmType = Type.getType(klass);
        this.anyConstructor = anyConstructor;
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
                                                                 // stack: value
        builder.newInstance(anyConstructor.getDeclaringClass()); // stack: value instance
        builder.dupX1();                                         // stack: instance value instance
        builder.swap();                                          // stack: instance instance value
        anyConstructor.putInvokeSpecial(builder);                // stack: instance
    }

    @Override
    public void compileToAny(MethodBuilder builder) {
                                             // stack: instance
        anyToThis.putInvokeVirtual(builder); // stack: value
    }

}
