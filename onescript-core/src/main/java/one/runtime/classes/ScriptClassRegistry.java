package one.runtime.classes;

import one.type.OneClassType;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores all registered (unloaded and loaded) OneScript (internal) classes.
 * These are classes defined in OneScript scripts or other sources within
 * the OneScript runtime environment.
 */
public class ScriptClassRegistry {

    /**
     * All registered internal classes by their JVM name.
     */
    private final Map<String, OneClassType> byJVMName = new HashMap<>();

    /**
     * Registers a new class descriptor to the registry.
     *
     * @param descriptor The descriptor.
     */
    public void register(OneClassType descriptor) {
        byJVMName.put(descriptor.getJVMClassName(), descriptor);
    }

    /**
     * Get the class, if present, which would be loaded
     * at the given JVM class name.
     *
     * @param name The JVM class name.
     * @return The class descriptor if present, otherwise null.
     */
    public OneClassType forJVMName(String name) {
        return byJVMName.get(name);
    }

}
