package one.runtime.classes;

import one.runtime.OneRuntime;
import one.type.OneClassType;

/**
 * The class loader responsible for compiling, loading and
 * initializing the JVM class mirrors of
 */
public class OneJVMClassLoader extends ClassLoader {

    /** The runtime object. */
    private final OneRuntime runtime;

    private final ScriptClassRegistry scriptClassRegistry;
    private final ScriptClassLoader scriptClassLoader;

    public OneJVMClassLoader(ClassLoader parent, OneRuntime runtime) {
        super(parent);
        this.runtime = runtime;
        this.scriptClassRegistry = runtime.getScriptClassRegistry();
        this.scriptClassLoader = runtime.getScriptClassLoader();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // check if it is an OneScript class
        if (ScriptClassDomain.isScriptClass(name)) {
            Class<?> cl = loadScriptClass(name);
            if (cl != null)
                return cl;
        }

        // find the class in parent loader
        ClassLoader parent = getParent();
        if (parent != null) {
            return parent.loadClass(name);
        }

        throw new ClassNotFoundException(name);
    }

    /**
     * Defines/loads an internal class found by JVM name.
     *
     * @param jvmName The JVM class name.
     * @return The class.
     */
    private Class<?> loadScriptClass(String jvmName) throws ClassNotFoundException {
        final OneClassType classType = scriptClassRegistry.forJVMName(jvmName);
        if (classType == null) {
            return null;
        }

        // TODO: write and here call some loader method
        //  in some other class OneClassLoader or something
        //  which will start the class loading chain
        if (!scriptClassLoader.loadScriptClass(classType)) {
            return null;
        }

        return classType.getLoadedJVMClass();
    }

}
