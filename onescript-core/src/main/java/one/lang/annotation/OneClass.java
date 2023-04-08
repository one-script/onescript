package one.lang.annotation;

import one.lang.Export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a native class to export.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OneClass {

    /**
     * The fully qualified script class name.
     * Derived from the class name by default.
     */
    String value() default "<get>";

    /**
     * If this class should be exported, if this is
     * false it is explicitly inaccessible from scripts.
     */
    Export export() default Export.EXPORT;

}
