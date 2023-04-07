package one.type.primitive;

import one.util.Placement;

import static one.type.cast.CastingRules.*;

/** Byte primitive type. */
public class OneByteType extends OnePrimitiveType {

    public OneByteType() {
        super("byte", Byte.TYPE);

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
