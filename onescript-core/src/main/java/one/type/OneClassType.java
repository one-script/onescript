package one.type;

import org.objectweb.asm.Type;

/**
 * A type based on a specific class, this can either
 * be a OneScript defined (internal) class or a
 * native/generated class.
 */
public class OneClassType extends OneType {

    protected OneClassType(Class<?> javaValueClass, Type asmType) {
        super(null, null);
        throw new UnsupportedOperationException("TODO"); // TODO
    }

}
