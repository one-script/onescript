package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Wrapper for reflection of Java fields.
 */
public class JavaField extends JavaMember {

    public static JavaField of(Field field) {
        return new JavaField(
                field.getDeclaringClass().getName(),
                field.getName(),
                Modifier.isStatic(field.getModifiers()),
                Type.getType(field.getType())
        );
    }

    public static JavaField ofAccessible(Field field) {
        field.setAccessible(true);
        return of(field);
    }

    public static JavaField find(Class<?> klass, String name) {
        try {
            return of(klass.getDeclaredField(name));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    public static JavaField findAccessible(Class<?> klass, String name) {
        try {
            return ofAccessible(klass.getDeclaredField(name));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    ////////////////////////////////////////

    public JavaField(String className, String name, boolean isStatic, Type type) {
        super(className, name, isStatic, type);
    }

    public Type getASMType() {
        return getAsmValueType();
    }

}
