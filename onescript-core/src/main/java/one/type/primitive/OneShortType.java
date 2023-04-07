package one.type.primitive;

import one.util.Placement;

import static one.type.cast.CastingRules.*;

/** Short primitive type. */
public class OneShortType extends OnePrimitiveType {

    protected OneShortType() {
        super("short", Short.TYPE);

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
