package one.runtime.classes;

import one.runtime.OneRuntime;
import one.type.OneType;

/**
 * The descriptor of a OneScript class type.
 *
 * It is a container for classes until and while they
 * are loaded. These descriptors are composed when scripts
 * are loaded to store information about the class for
 * compilation and early runtime purposes without having
 * to actually generate and load the class.
 *
 * @see RuntimeClasses For a more detailed description of how classes work.
 */
public class OneClassDescriptor {

    /**
     * The runtime object.
     */
    private final OneRuntime runtime;

    /** The name of the JVM class. */
    private final String jvmClassName;
    /** The OneScript class name. */
    private final String internalClassName;

    /**
     * The JVM class instance once loaded.
     */
    private Class<?> loadedJvmClass;

    /**
     * The OneScript type.
     */
    private OneType internalType;

    public OneClassDescriptor(OneRuntime runtime, String internalClassName) {
        this.runtime = runtime;

        this.internalClassName = internalClassName;
        this.jvmClassName = RuntimeClasses.getJVMClassName(internalClassName);
    }

    /* Getters */

    public OneRuntime getRuntime() {
        return runtime;
    }

    public String getJVMClassName() {
        return jvmClassName;
    }

    public String getClassName() {
        return internalClassName;
    }

    public Class<?> getLoadedJVMClass() {
        return loadedJvmClass;
    }

    public OneType getType() {
        return internalType;
    }

}
