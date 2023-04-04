package one.type;

import one.runtime.classes.OneClasses;
import org.objectweb.asm.Type;

/**
 * A type based on a specific class, this can either
 * be a OneScript defined (internal) class or a
 * native/generated class.
 *
 * This is the descriptor of a OneScript class type.
 *
 * It is a container for classes until and while they
 * are loaded. These descriptors are composed when scripts
 * are loaded to store information about the class for
 * compilation and early runtime purposes without having
 * to actually generate and load the class.
 *
 * @see OneClasses For a more detailed description of how classes work.
 */
public class OneClassType extends OneType {

    /** The name of the JVM class. */
    private final String jvmClassName;
    /** The OneScript class name. */
    private final String internalClassName;

    /**
     * The JVM class instance once loaded.
     */
    private Class<?> loadedJvmClass;

    private final Type asmType;

    public OneClassType(String internalClassName) {
        super(internalClassName);

        this.internalClassName = internalClassName;
        this.jvmClassName = OneClasses.getJVMClassName(internalClassName);

        this.asmType = Type.getType("L" + jvmClassName + ";");
    }

    /* Getters */

    public String getClassName() {
        return internalClassName;
    }

    @Override
    public Class<?> getLoadedJVMClass() {
        return loadedJvmClass;
    }

    @Override
    public String getJVMClassName() {
        return jvmClassName;
    }

    @Override
    public Type getASMType() {
        return asmType;
    }

}
