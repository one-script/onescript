package one.lang.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Denotes the target element to be special to
 * OneScript. For example, using this on parameters
 * of type {@link one.runtime.OneRuntime} will cause
 * the parameter to be inlined with the calling runtime
 * on call, instead of having the caller provide it.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface OneSpecial {
    
}
