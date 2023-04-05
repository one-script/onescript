package one.util.asm;

import org.objectweb.asm.Type;

/**
 * Utilities for working with ObjectWeb ASM.
 */
public class ASMUtil {

    public static Type[] getTypes(Class<?>... classes) {
        final int l = classes.length;
        Type[] out = new Type[l];
        for (int i = 0; i < l; i++)
            out[i] = Type.getType(classes[i]);
        return out;
    }

}
