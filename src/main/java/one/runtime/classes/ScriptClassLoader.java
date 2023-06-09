package one.runtime.classes;

import one.runtime.OneRuntime;
import one.type.OneClass;

/**
 * Responsible for compiling, loading and initializing
 * internal (OneScript) classes.
 *
 * @see OneClassRegistry
 */
public class ScriptClassLoader {

    /** The host runtime. */
    private final OneRuntime runtime;

    public ScriptClassLoader(OneRuntime runtime) {
        this.runtime = runtime;
    }

    /**
     * Compiles, loads and initializes the script class
     * provided by descriptor.
     *
     * The loaded JVM class reference, an {@link Class} object,
     * is stored in back in the class descriptor.
     *
     * @param classType The script class type,
     * @return If it was successful.
     */
    public boolean loadScriptClass(OneJVMClassLoader classLoader,
                                   OneClass classType) {
        throw new UnsupportedOperationException("TODO"); // todo
    }

}
