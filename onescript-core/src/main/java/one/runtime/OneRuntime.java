package one.runtime;

import one.runtime.classes.OneJVMClassLoader;

/**
 * The central class for the OneScript runtime.
 */
public class OneRuntime {

    /**
     * The JVM OneScript class loader.
     */
    private OneJVMClassLoader jvmClassLoader;

    public OneJVMClassLoader getJVMClassLoader() {
        return jvmClassLoader;
    }

}
