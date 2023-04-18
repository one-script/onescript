package one.runtime.classes;

import one.type.OneClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all registered (unloaded and loaded) OneScript (internal) classes.
 * These are classes defined in OneScript scripts or other sources within
 * the OneScript runtime environment.
 */
public class OneClassRegistry {

    /**
     * All registered internal classes by their JVM name.
     */
    private final Map<String, OneClass> byJVMName = new HashMap<>();

    /**
     * All registered script classes by their script name.
     */
    private final Map<String, OneClass> byScriptName = new HashMap<>();

    /**
     * Registers a new class descriptor to the registry.
     *
     * @param descriptor The descriptor.
     */
    public void register(OneClass descriptor) {
        byJVMName.put(descriptor.getJVMClassName(), descriptor);
        byScriptName.put(descriptor.getClassName(), descriptor);
    }

    /**
     * Get the class, if present, which would be loaded
     * at the given JVM class name.
     *
     * @param name The JVM class name.
     * @return The class descriptor if present, otherwise null.
     */
    public OneClass forJVMName(String name) {
        return byJVMName.get(name);
    }

    public OneClass forScriptName(String name) {
        return byScriptName.get(name);
    }

}
