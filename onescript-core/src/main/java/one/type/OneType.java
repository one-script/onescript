package one.type;

import org.objectweb.asm.Type;

/**
 * A compile and runtime description of a type
 * in OneScript.
 */
public abstract class OneType {

    /**
     * The internal name of this type.
     */
    private final String name;

    protected OneType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the loaded JVM class mirroring this value
     * type if present, otherwise return null.
     *
     * @return The JVM class.
     */
    public abstract Class<?> getLoadedJVMClass();

    /**
     * Get the name of the JVM class, this should never
     * return null as this does not require anything
     * besides the descriptor to be loaded.
     *
     * @return The JVM class name.
     */
    public abstract String getJVMClassName();

    /**
     * Get the ObjectWeb ASM type mirror for
     * this value type.
     *
     * @return The ASM type.
     */
    public abstract Type getASMType();

    @Override
    public String toString() {
        return "OneType(" + getName() + ")";
    }

}
