package one.type.primitive;

import one.util.Placement;

import static one.type.cast.PrimitiveCastingRules.*;

/** Long primitive type. */
public class OneLongType extends OnePrimitiveType {

    public OneLongType() {
        super("long", Long.TYPE);

        // Casting Rules
        addCastingRule(Placement.first(), L2I);
        addCastingRule(Placement.first(), L2F);
        addCastingRule(Placement.first(), L2D);
    }

}
