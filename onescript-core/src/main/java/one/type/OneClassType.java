package one.type;

import one.runtime.classes.ScriptClassDomain;
import one.type.any.AnyClass;
import one.util.asm.MethodBuilder;
import org.objectweb.asm.Type;

/**
 * A type based on a specific class, this can either
 * be a OneScript defined (internal) class or a
 * native/generated class.
 *
 * This is the descriptor of a OneScript class type.
 *
 * It is a container for classes until and while they
 * are loaded. These descriptors are composed when scripts
 * are loaded to store information about the class for
 * compilation and early runtime purposes without having
 * to actually generate and load the class.
 *
 * @param <V> The value type.
 */
public class OneClassType<V> extends OneStrongType {

    /** The name of the JVM class. */
    private final String jvmClassName;
    /** The OneScript class name. */
    private final String internalClassName;

    /**
     * The class domain if this is a script class
     */
    private ScriptClassDomain scriptClassDomain;

    /**
     * The JVM class instance once loaded.
     */
    private Class<?> loadedJvmClass;

    /**
     * The ASM class type representing this class.
     */
    private final Type asmType;

    /**
     * If the JVM class for this class type implements
     * {@link Any}. This allows faster back and forth
     * casting as no work is required.
     */
    private final boolean implementsAny;

    public OneClassType(String internalClassName,
                        String jvmClassName,
                        ScriptClassDomain scriptClassDomain,
                        boolean implementsAny) {
        super(internalClassName);

        this.internalClassName = internalClassName;
        this.jvmClassName = jvmClassName;
        this.scriptClassDomain = scriptClassDomain;
        this.implementsAny = implementsAny;

        this.asmType = Type.getType("L" + jvmClassName + ";");
    }

    /* Getters */

    public String getClassName() {
        return internalClassName;
    }

    @Override
    public Class<?> getLoadedJVMClass() {
        return loadedJvmClass;
    }

    @Override
    public String getJVMClassName() {
        return jvmClassName;
    }

    @Override
    public Type getAsmType() {
        return asmType;
    }

    public ScriptClassDomain getScriptClassDomain() {
        return scriptClassDomain;
    }

    public boolean isScriptClass() {
        return scriptClassDomain != null;
    }

    public boolean isNativeClass() {
        return scriptClassDomain == null;
    }

    /**
     * Get if the JVM class for this class type implements
     * {@link Any}. This allows faster back and forth
     * casting as no work is required.
     */
    public boolean implementsAny() {
        return implementsAny;
    }

    /*
        Compilation
     */

    @Override
    public void compileToAny(MethodBuilder builder) {
        if (!implementsAny) {
                                                            // stack: value
            builder.newInstance(AnyClass.CLASS);            // stack: value instance
            builder.dupX1();                                // stack: instance value instance
            builder.swap();                                 // stack: instance instance value
            /* todo: obtain instance of this class type */  // stack: instance instance value type
            builder.constNull();
            AnyClass.CONSTRUCTOR.putInvokeSpecial(builder); // stack: instance
        }

        // if the class implements the Any interface
        // the value on top of the stack would already
        // inherit from Any which means no work has to
        // be done and no new instances have to be created
        // to cast it
    }

    @Override
    public void compileFromAny(MethodBuilder builder) {
        if (!implementsAny) {
                                                          // stack: instance
            AnyClass.METHOD_AS.putInvokeVirtual(builder); // stack: value
        }

        // if the class implements the Any interface
        // the value on top of the stack would already
        // inherit from Any which means no work has to
        // be done and no new instances have to be created
        // to cast it
    }

}
