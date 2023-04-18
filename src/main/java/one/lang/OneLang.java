package one.lang;

import one.type.OneClass;

/**
 * Internal OneScript language and runtime specifications.
 */
@one.lang.annotation.OneClass(export = Export.PRIVATE) // dont export this class to scripts
public class OneLang {

    // Core Class Types //
    public static final OneClass<Object> TYPE_OBJECT = null;
    public static final OneClass<String> TYPE_STRING = null;

    // Boxed Primitive Types //

}
