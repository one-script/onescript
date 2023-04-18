package one.runtime.classes;

public interface NativeClassResolver {

    /**
     * Get the packages this resolver
     * applies to. Any empty string means
     * all packages.
     *
     * @return The packages to cover.
     */
    String[] getPackages();

    /**
     * The priority of this resolver.
     *
     * @return The priority.
     */
    int getPriority();

    /**
     * Resolve the script name into the name
     * of the JVM class for the native class.
     *
     * Return null if this resolver can't
     * resolve it.
     *
     * @param scriptName The script name.
     * @return The JVM name or null if not applicable.
     */
    String qualifyJVMName(String scriptName);

}
