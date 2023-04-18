package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Unloaded reflection of any JVM class.
 */
public class JavaClass {

    private static final Map<String, JavaClass> cache =
            new HashMap<>();

    public static JavaClass of(Class<?> klass) {
        String name = klass.getName();
        JavaClass cl = cache.get(name);
        if (cl != null) {
            return cl;
        }

        return new JavaClass(
                name,
                JavaClasses.toInternal(name),
                klass.isInterface(),
                Modifier.isAbstract(klass.getModifiers()),
                klass
        );
    }

    public static JavaClass forName(String name) {
        JavaClass cl = cache.get(name);
        if (cl != null) {
            return cl;
        }

        try {
            return of(Class.forName(name));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            throw new AssertionError();
        }
    }

    /** The name of the class. */
    private final String name;
    /** The internal name of the class. */
    private final String internalName;

    /** If this class is an interface. */
    private final boolean isInterface;
    /** If this class is abstract. */
    private final boolean isAbstract;

    private final Type asmType;

    // the loaded jvm class
    private Class<?> loadedClass;

    public JavaClass(String name, String internalName, boolean isInterface, boolean isAbstract, Class<?> loadedClass) {
        this.name = (name == null ? JavaClasses.toNormal(internalName) : name);
        this.internalName = (internalName == null ? JavaClasses.toInternal(name) : internalName);
        this.isInterface = isInterface;
        this.isAbstract = isInterface || isAbstract;

        this.asmType = Type.getType("L" + this.internalName + ";");
        this.loadedClass = loadedClass;

        cache.put(this.name, this);
    }

    public JavaClass(String name, String internalName, boolean isInterface, boolean isAbstract) {
        this(name, internalName, isInterface, isAbstract, null);
    }

    public JavaClass(String name, boolean isInterface) {
        this(name, null, isInterface, false);
    }

    public String getName() {
        return name;
    }

    public String getInternalName() {
        return internalName;
    }

    public Type getAsmType() {
        return asmType;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public Class<?> getLoadedClassOrNull() {
        return loadedClass;
    }

    public Class<?> getLoadedClass() {
        if (loadedClass == null) {
            try {
                loadedClass = Class.forName(name);
            } catch (Exception e) {
                Throwables.sneakyThrow(e);
            }
        }

        return loadedClass;
    }

}
