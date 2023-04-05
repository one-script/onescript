package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Type;

/**
 * Common class for all Java member classes.
 * The Java member classes are for referencing attributes
 * before classes are loaded, so we can't use reflection.
 */
public abstract class JavaMember {

    /** The name of the containing class. */
    private final String className;
    private final String internalClassName;

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

    // cache for the loaded class
    private Class<?> loadedClass;

    protected JavaMember(String className, String name, boolean isStatic, Type asmValueType) {
        this.className = className;
        this.internalClassName = JavaClasses.toInternal(className);
        this.name = name;
        this.isStatic = isStatic;
        this.asmValueType = asmValueType;
    }

    public String getClassName() {
        return className;
    }

    public String getInternalClassName() {
        return internalClassName;
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

    public Class<?> getLoadedClass() {
        if (loadedClass == null) {
            try {
                loadedClass = Class.forName(className);
            } catch (Exception e) {
                Throwables.sneakyThrow(e);
            }
        }

        return loadedClass;
    }

}
