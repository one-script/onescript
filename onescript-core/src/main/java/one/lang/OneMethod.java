package one.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a method to be exposed to OneScript (as public)
 * in a native class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OneMethod {

    /**
     * The name of the method.
     *
     * By default it copies the Java methods.
     */
    String value() default "<get>";

}
