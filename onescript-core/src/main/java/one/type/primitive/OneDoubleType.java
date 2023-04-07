package one.type.primitive;

import one.util.Placement;

import static one.type.cast.CastingRules.*;

/** Double primitive type. */
public class OneDoubleType extends OnePrimitiveType {

    public OneDoubleType() {
        super("double", Double.TYPE);

        // Casting Rules
        addCastingRule(Placement.first(), D2I);
        addCastingRule(Placement.first(), D2L);
        addCastingRule(Placement.first(), D2F);
    }

}
