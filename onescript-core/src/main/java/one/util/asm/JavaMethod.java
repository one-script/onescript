package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Wrapper for reflection of Java methods.
 */
public class JavaMethod extends JavaMember {

    public static JavaMethod of(Method method) {
        Class<?> dc = method.getDeclaringClass();
        return new JavaMethod(
                dc.getName(),
                method.getName(),
                Modifier.isStatic(method.getModifiers()),
                dc.isInterface(),
                Type.getType(method)
        );
    }

    public static JavaMethod of(Constructor<?> constructor) {
        Class<?> dc = constructor.getDeclaringClass();
        return new JavaMethod(
                dc.getName(),
                "<init>",
                false,
                dc.isInterface(),
                Type.getType(constructor)
        );
    }

    public static JavaMethod ofAccessible(Method method) {
        method.setAccessible(true);
        return of(method);
    }

    public static JavaMethod ofAccessible(Constructor<?> constructor) {
        constructor.setAccessible(true);
        return of(constructor);
    }

    public static JavaMethod find(Class<?> klass, String name, Class<?>... paramTypes) {
        try {
            return of(klass.getDeclaredMethod(name, paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    public static JavaMethod findAccessible(Class<?> klass, String name, Class<?>... paramTypes) {
        try {
            return ofAccessible(klass.getDeclaredMethod(name, paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    public static JavaMethod findConstructor(Class<?> klass, Class<?>... paramTypes) {
        try {
            return of(klass.getDeclaredConstructor(paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    public static JavaMethod findAccessibleConstructor(Class<?> klass, Class<?>... paramTypes) {
        try {
            return ofAccessible(klass.getDeclaredConstructor(paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    //////////////////////////////////////

    /** The ASM method type. */
    private final Type asmType;

    /** If it is an interface method. */
    private final boolean isItf;

    public JavaMethod(String className, String name, boolean isStatic, boolean isItf, Type methodType) {
        super(className, name, isStatic, methodType.getReturnType());
        this.isItf = isItf;
        this.asmType = methodType;
    }

    public boolean isInterfaceMethod() {
        return isItf;
    }

    public Type getAsmType() {
        return asmType;
    }

    public Type getAsmReturnType() {
        return getAsmValueType();
    }

    /*
        Codegen
     */

    public void putInvokeVirtual(MethodBuilder builder) {
        int opcode = isInterfaceMethod() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL;
        builder.getVisitor().visitMethodInsn(
                opcode, getInternalClassName(), getName(),
                asmType.getDescriptor(), isInterfaceMethod()
        );
    }

    public void putInvokeStatic(MethodBuilder builder) {
        builder.getVisitor().visitMethodInsn(
                Opcodes.INVOKESTATIC, getInternalClassName(), getName(),
                asmType.getDescriptor(), isInterfaceMethod()
        );
    }

    public void putInvokeSpecial(MethodBuilder builder) {
        builder.getVisitor().visitMethodInsn(
                Opcodes.INVOKESPECIAL, getInternalClassName(), getName(),
                asmType.getDescriptor(), isInterfaceMethod()
        );
    }

}
