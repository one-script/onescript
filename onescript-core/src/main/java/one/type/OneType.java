package one.type;

import org.objectweb.asm.Type;

/**
 * A compile and runtime description of a type
 * in OneScript.
 */
public abstract class OneType {

    /** The Java primitive class.*/
    private final Class<?> javaPrimitive;
    /** The OW2 ASM type for this value type. */
    private final Type asmType;

    protected OneType(Class<?> javaPrimitive,
                      Type asmType) {
        this.javaPrimitive = javaPrimitive;
        this.asmType = asmType;
    }

    public Class<?> getJavaPrimitive() {
        return javaPrimitive;
    }

    public Type getAsmType() {
        return asmType;
    }

}
