package one.lang;

/**
 * Describes an export state.
 */
public enum Export {

    /**
     * Exports the symbol publicly to OneScript.
     */
    EXPORT,

    /**
     * Copies the default export settings of the upper symbol.
     */
    DEFAULT,

    /**
     * Does not export the symbol publicly to OneScript.
     */
    PRIVATE

}
