package one.runtime.classes;

/**
 * OneScript class utility and standard methods.
 *
 * OneScript classes are always present as descriptors
 * at runtime. Once referenced, they are dynamically compiled
 * into Java bytecode and loaded as JVM classes under the
 * {@code oneclasses} package.
 *
 * An 'internal' (class) name refers to a symbol within OneScript.
 * A JVM (class) name refers to a mirror symbol for the JVM.
 */
public class OneClasses {

    public static final String CLASSES_JVM_PACKAGE = "oneclasses";

    /**
     * Get the standard JVM class name for the given
     * internal (OneScript) class path.
     *
     * @return The JVM class name.
     */
    public static String getJVMClassName(String internalName) {
        return CLASSES_JVM_PACKAGE + "." + internalName;
    }

    /**
     * Get the internal name of the script init class.
     *
     * @param pk The package.
     * @param scriptName The name of the script.
     * @return The internal package.
     */
    public static String getInternalScriptClassName(String pk, String scriptName) {
        return pk + ".Script$" + scriptName;
    }

}
