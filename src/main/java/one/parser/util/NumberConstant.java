package one.parser.util;

import one.type.OneType;
import one.type.OneTypes;

/**
 * Stores a parsed number with implicit
 * or explicit type declared.
 */
public class NumberConstant {

    public static NumberConstant parseLiteral(StringReader reader) {
        double value = reader.collectDouble();
        OneType explicitType = getExplicitType(reader.curr());
        if (explicitType != null)
            reader.next();

        return new NumberConstant(explicitType, value);
    }

    public static OneType getExplicitType(char c) {
        return switch (c) {
            case 'f', 'F' -> OneTypes.FLOAT;
            case 'd', 'D' -> OneTypes.DOUBLE;
            case 'l', 'L' -> OneTypes.LONG;
            case 'b', 'B' -> OneTypes.BYTE;
            case 's', 'S' -> OneTypes.SHORT;
            case 'i', 'I' -> OneTypes.INT;
            default -> null;
        };
    }

    public static char getExplictChar(OneType type) {
        if (type == OneTypes.FLOAT) return 'f';
        if (type == OneTypes.DOUBLE) return 'd';
        if (type == OneTypes.LONG) return 'l';
        if (type == OneTypes.BYTE) return 'b';
        if (type == OneTypes.SHORT) return 's';
        if (type == OneTypes.INT) return 'i';
        return '\u0000';
    }

    /** The explicitly defined type of the number.
     *  This is null if it isnt explicitly defined. */
    private final OneType explicitType;

    /** The number value. */
    private final double value;

    public NumberConstant(OneType explicitType,
                          double value) {
        this.explicitType = explicitType;
        this.value = value;
    }

    public NumberConstant(double value) {
        this(null, value);
    }

    public OneType getExplicitType() {
        return explicitType;
    }

    public boolean isExplicit() {
        return explicitType != null;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "" + (explicitType != null ? getExplictChar(explicitType) : "");
    }

}
