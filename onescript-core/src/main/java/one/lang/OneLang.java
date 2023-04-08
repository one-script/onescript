package one.lang;

import one.lang.annotation.OneClass;
import one.type.OneClassType;

/**
 * Internal OneScript language and runtime specifications.
 */
@OneClass(export = Export.PRIVATE) // dont export this class to scripts
public class OneLang {

    // Core Class Types //
    public static final OneClassType<Object> TYPE_OBJECT = null;
    public static final OneClassType<String> TYPE_STRING = null;

    // Boxed Primitive Types //

}
