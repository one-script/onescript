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

    private final OneClassRegistry classRegistry;
    private final ScriptClassLoader scriptClassLoader;

    public OneJVMClassLoader(ClassLoader parent, OneRuntime runtime) {
        super(parent);
        this.runtime = runtime;
        this.classRegistry = runtime.getClassRegistry();
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
        final OneClassType classType = classRegistry.forJVMName(jvmName);
        if (classType == null) {
            return null;
        }

        Class<?> klass = classType.getLoadedJVMClass();
        if (klass != null) {
            return klass;
        }

        // TODO: write and here call some loader method
        //  in some other class OneClassLoader or something
        //  which will start the class loading chain
        if (!scriptClassLoader.loadScriptClass(this, classType)) {
            return null;
        }

        return classType.getLoadedJVMClass();
    }

    /**
     * Define a new class from the given bytes.
     *
     * @param name The name of the class.
     * @param bytes The bytes.
     * @return The class.
     */
    protected Class<?> defineFromBytes(String name, byte[] bytes) {
        return defineClass(name, bytes, 0, bytes.length);
    }

}
