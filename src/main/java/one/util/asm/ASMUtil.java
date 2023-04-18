package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;

import static org.objectweb.asm.Opcodes.*;
import static org.objectweb.asm.Type.*;

/**
 * Utilities for working with ObjectWeb ASM.
 */
public class ASMUtil {

    /**
     * Convert the array of classes into an array of types.
     *
     * @param classes The classes.
     * @return The types.
     */
    public static Type[] getTypes(Class<?>... classes) {
        final int l = classes.length;
        Type[] out = new Type[l];
        for (int i = 0; i < l; i++)
            out[i] = Type.getType(classes[i]);
        return out;
    }

    /**
     * Map the {@link Type} sort value to the primitive JVM
     * value type. (sourced from {@link org.objectweb.asm.Opcodes})
     *
     * @param sort The sort value.
     * @return The type value.
     */
    public static int getPrimitiveType(int sort) {
        return switch (sort) {
            case BOOLEAN     -> T_BOOLEAN;
            case BYTE        -> T_BYTE;
            case SHORT       -> T_SHORT;
            case INT         -> T_INT;
            case Type.LONG   -> T_LONG;
            case Type.FLOAT  -> T_FLOAT;
            case Type.DOUBLE -> T_DOUBLE;
            default -> throw new IllegalArgumentException("No primitive type for sort " + sort);
        };
    }

    /**
     * Get the JVM class for a given ASM type.
     *
     * @param type The type.
     * @return The class.
     */
    public static Class<?> getClass(Type type) {
        try {
            return switch (type.getSort()) {
                case BOOLEAN     -> Boolean.TYPE;
                case BYTE        -> Byte.TYPE;
                case SHORT       -> Short.TYPE;
                case INT         -> Integer.TYPE;
                case Type.LONG   -> Long.TYPE;
                case Type.FLOAT  -> Float.TYPE;
                case Type.DOUBLE -> Double.TYPE;
                case OBJECT -> Class.forName(type.getClassName());
                case ARRAY  -> Array.newInstance(getClass(type.getElementType()), 0).getClass();
                default -> throw new IllegalArgumentException(String.valueOf(type.getSort()));
            };
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    /**
     * Convert an array of types to an array of classes.
     *
     * @param types The types.
     * @return The classes.
     */
    public static Class<?>[] getClasses(Type[] types) {
        final int l = types.length;
        Class<?>[] classes = new Class[l];
        for (int i = 0; i < l; i++)
            classes[i] = getClass(types[i]);
        return classes;
    }

    public static MethodType getMethodType(Type methodType) {
        return MethodType.methodType(getClass(methodType.getReturnType()), getClasses(methodType.getArgumentTypes()));
    }

}
