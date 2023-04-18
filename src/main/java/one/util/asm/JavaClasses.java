package one.util.asm;

/**
 * Utilities for working with Java classes.
 */
public class JavaClasses {

    public static String toInternal(String className) {
        return className.replace('.', '/');
    }

    public static String toNormal(String internal) {
        return internal.replace('/', '.');
    }

}
