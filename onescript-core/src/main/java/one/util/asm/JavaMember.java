package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Type;

/**
 * Common class for all Java member classes.
 * The Java member classes are for referencing attributes
 * before classes are loaded, so we can't use reflection.
 */
public abstract class JavaMember {

    /** The declaring class. */
    private final JavaClass declaringClass;

    /** The name of this member. */
    private final String name;

    /** If this member is static. */
    private final boolean isStatic;

    /**
     * The return/value type of this member.
     * This would be the fields type for fields
     * and the methods return type for methods.
     */
    private final Type asmValueType;

    protected JavaMember(JavaClass declaringClass, String name, boolean isStatic, Type asmValueType) {
        this.declaringClass = declaringClass;
        this.name = name;
        this.isStatic = isStatic;
        this.asmValueType = asmValueType;
    }

    public JavaClass getDeclaringClass() {
        return declaringClass;
    }

    public String getName() {
        return name;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public Type getAsmValueType() {
        return asmValueType;
    }

}
