package one.runtime;

import one.runtime.classes.ScriptClassLoader;
import one.runtime.classes.ScriptClassRegistry;
import one.runtime.classes.OneJVMClassLoader;

/**
 * The central class for the OneScript runtime.
 */
public class OneRuntime {

    /**
     * The JVM OneScript class loader.
     */
    private OneJVMClassLoader jvmClassLoader;

    /**
     * The class pool of loaded and unloaded script classes.
     */
    private ScriptClassRegistry scriptClassRegistry;

    /**
     * The system/main script class loader.
     */
    private ScriptClassLoader scriptClassLoader;

    /* Getters */

    public OneJVMClassLoader getJVMClassLoader() {
        return jvmClassLoader;
    }

    public ScriptClassRegistry getScriptClassRegistry() {
        return scriptClassRegistry;
    }

    public ScriptClassLoader getScriptClassLoader() {
        return scriptClassLoader;
    }

}
