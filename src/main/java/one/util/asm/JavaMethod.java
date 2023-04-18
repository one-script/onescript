package one.util.asm;

import one.util.Throwables;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
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
                JavaClass.of(dc),
                method.getName(),
                Modifier.isStatic(method.getModifiers()),
                dc.isInterface(),
                Type.getType(method)
        );
    }

    public static JavaMethod of(Constructor<?> constructor) {
        Class<?> dc = constructor.getDeclaringClass();
        return new JavaMethod(
                JavaClass.of(dc),
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
            throw new AssertionError();
        }
    }

    public static JavaMethod findAccessible(Class<?> klass, String name, Class<?>... paramTypes) {
        try {
            return ofAccessible(klass.getDeclaredMethod(name, paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            throw new AssertionError();
        }
    }

    public static JavaMethod findConstructor(Class<?> klass, Class<?>... paramTypes) {
        try {
            return of(klass.getDeclaredConstructor(paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            throw new AssertionError();
        }
    }

    public static JavaMethod findAccessibleConstructor(Class<?> klass, Class<?>... paramTypes) {
        try {
            return ofAccessible(klass.getDeclaredConstructor(paramTypes));
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            throw new AssertionError();
        }
    }

    //////////////////////////////////////

    /** The ASM method type. */
    private final Type asmType;

    /** If it is an interface method. */
    private final boolean isItf;

    private Method method;
    private MethodHandle methodHandle;

    public JavaMethod(JavaClass declaringClass, String name, boolean isStatic, boolean isItf, Type methodType) {
        super(declaringClass, name, isStatic, methodType.getReturnType());
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

    public Method asMethod() {
        // TODO: get method from loaded class if null
        return method;
    }

    /**
     * Find a method handle for this given method.
     *
     * @return The method handle.
     */
    public MethodHandle asMethodHandle() {
        try {
            if (methodHandle == null) {
                if (isStatic()) methodHandle = MethodHandles.lookup()
                        .findStatic(getDeclaringClass().getLoadedClass(), getName(), ASMUtil.getMethodType(asmType));
                else methodHandle = MethodHandles.lookup()
                        .findVirtual(getDeclaringClass().getLoadedClass(), getName(), ASMUtil.getMethodType(asmType));
            }

            return methodHandle;
        } catch (Exception e) {
            Throwables.sneakyThrow(e);
            return null;
        }
    }

    /*
        Codegen
     */

    public void putInvoke(MethodBuilder builder) {
        if (isStatic()) putInvokeVirtual(builder);
        else putInvokeStatic(builder);
    }

    public void putInvoke(MethodBuilder builder, boolean special) {
        if (isStatic()) putInvokeSpecial(builder);
        else
            if (!special) putInvokeStatic(builder);
            else putInvokeVirtual(builder);
    }

    public void putInvokeVirtual(MethodBuilder builder) {
        int opcode = isInterfaceMethod() ? Opcodes.INVOKEINTERFACE : Opcodes.INVOKEVIRTUAL;
        builder.getVisitor().visitMethodInsn(
                opcode, getDeclaringClass().getInternalName(), getName(),
                asmType.getDescriptor(), isInterfaceMethod()
        );
    }

    public void putInvokeStatic(MethodBuilder builder) {
        builder.getVisitor().visitMethodInsn(
                Opcodes.INVOKESTATIC, getDeclaringClass().getInternalName(), getName(),
                asmType.getDescriptor(), isInterfaceMethod()
        );
    }

    public void putInvokeSpecial(MethodBuilder builder) {
        builder.getVisitor().visitMethodInsn(
                Opcodes.INVOKESPECIAL, getDeclaringClass().getInternalName(), getName(),
                asmType.getDescriptor(), isInterfaceMethod()
        );
    }

}
