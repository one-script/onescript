package one.util.asm;

import org.objectweb.asm.Type;

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

}
