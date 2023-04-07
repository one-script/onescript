package one.type.primitive;

import one.util.Placement;

import static one.type.cast.CastingRules.*;

/** Float primitive type. */
public class OneFloatType extends OnePrimitiveType {

    public OneFloatType() {
        super("float", Float.TYPE);

        // Casting Rules
        addCastingRule(Placement.first(), F2I);
        addCastingRule(Placement.first(), F2L);
        addCastingRule(Placement.first(), F2D);
    }

}
