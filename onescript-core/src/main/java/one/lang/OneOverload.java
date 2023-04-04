package one.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a method to overload an operator.
 * This does not expose the method by name, but it
 * also does not prevent it from being exposed using
 * {@link OneMethod}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OneOverload {

    /**
     * The name of the operator to overload.
     */
    String value();

}
