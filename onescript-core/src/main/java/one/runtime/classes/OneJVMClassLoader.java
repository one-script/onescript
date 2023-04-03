package one.runtime.classes;

import one.runtime.OneRuntime;

/**
 * The class loader responsible for compiling, loading and
 * initializing the JVM class mirrors of
 */
public class OneJVMClassLoader extends ClassLoader {

    /** The runtime object. */
    private final OneRuntime runtime;

    public OneJVMClassLoader(ClassLoader parent, OneRuntime runtime) {
        super(parent);
        this.runtime = runtime;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // check if it is an OneScript class
        if (!name.startsWith(OneClasses.CLASSES_JVM_PACKAGE)) {
            // todo
        }

        // find the class in parent loader
        ClassLoader parent = getParent();
        if (parent != null) {
            return parent.loadClass(name);
        }

        throw new ClassNotFoundException(name);
    }

}
