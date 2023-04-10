package one.lang;

import one.parser.util.Parsable;

import java.util.Locale;

public enum OneModifier implements Parsable {

    PUBLIC("pub", java.lang.reflect.Modifier.PUBLIC),
    PRIVATE("private", java.lang.reflect.Modifier.PRIVATE),
    PROTECTED("protected", java.lang.reflect.Modifier.PROTECTED),

    STATIC("static", java.lang.reflect.Modifier.STATIC),
    FINAL("final", java.lang.reflect.Modifier.FINAL),
    VOLATILE("volatile", java.lang.reflect.Modifier.VOLATILE),
    ABSTRACT("abstract", java.lang.reflect.Modifier.ABSTRACT),
    INTERFACE(null /* dont parse as a modifier */, java.lang.reflect.Modifier.INTERFACE),

    ;

    /** The parsing alias of this modifier. */
    private final String alias;

    /** The bit mask for this modifier. */
    private final int mask;

    OneModifier(String alias,
                int mask) {
        this.alias = alias;
        this.mask = mask;
    }

    public String getAlias() {
        return alias;
    }

    public int getMask() {
        return mask;
    }

    public long set(long l) {
        return l | mask;
    }

    public long unset(long l) {
        return l & ~mask;
    }

    public boolean isSet(long l) {
        return (l & mask) != 0;
    }

    @Override
    public String[] getAliases() {
        if (alias == null)
            return null;
        return new String[] { alias };
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
