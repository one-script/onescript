package one.type.primitive;

import one.util.Placement;

import static one.type.cast.PrimitiveCastingRules.*;

/** Integer primitive type. */
public class OneIntType extends OnePrimitiveType {

    public OneIntType() {
        super("int", Integer.TYPE);

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
