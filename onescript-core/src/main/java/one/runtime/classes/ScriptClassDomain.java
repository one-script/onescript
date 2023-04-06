package one.runtime.classes;

/**
 * Handles the organization of OneScript classes
 * on the JVM.
 */
public class ScriptClassDomain {

    /**
     * The top-level package name for OneScript classes.
     */
    public static final String CLASSES_JVM_PACKAGE = "oneclasses";

    /**
     * Check if the given JVM name is a script class.
     *
     * @param jvmName The JVM class name.
     * @return If it would be a script class.
     */
    public static boolean isScriptClass(String jvmName) {
        return jvmName.startsWith(CLASSES_JVM_PACKAGE + ".");
    }

    /** The general shared domain. */
    static ScriptClassDomain SHARED = shared("shared");

    public static ScriptClassDomain shared() {
        return SHARED;
    }

    public static ScriptClassDomain shared(final String name) {
        return new ScriptClassDomain(false, name);
    }

    public static ScriptClassDomain unique(final String name) {
        return new ScriptClassDomain(true, name);
    }

    ///////////////////////////////

    /** If this domain is unique. */
    private final boolean unique;
    /** The name. */
    private final String name;

    ScriptClassDomain(boolean unique, String name) {
        this.unique = unique;
        this.name = name;
    }

    /**
     * If this domain is local to one runtime.
     */
    public boolean isUnique() {
        return unique;
    }

    public String getName() {
        return name;
    }

    /**
     * Check if the given JVM class name falls
     * under this domain.
     *
     * @param jvmName The JVM class name.
     */
    public boolean hasClass(String jvmName) {
        return jvmName.startsWith(CLASSES_JVM_PACKAGE + "." + name + ".");
    }

    /**
     * Create the JVM class name for a class
     * with the name within scripts.
     *
     * @param scriptName The OneScript name.
     * @return The JVM name.
     */
    public String getJVMName(String scriptName) {
        return CLASSES_JVM_PACKAGE + "." + name + "." + scriptName;
    }

}
