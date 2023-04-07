package one.type.primitive;

import one.type.Any;
import one.type.any.AnyByte;
import one.type.any.AnyLong;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** Long primitive type. */
public class OneLongType extends OnePrimitiveType {

    public OneLongType() {
        super("long", Long.TYPE,
                JavaMethod.find(AnyLong.class, "of", long.class), JavaMethod.find(Any.class, "asLong"));

        // Casting Rules
        addCastingRule(Placement.first(), L2I);
        addCastingRule(Placement.first(), L2F);
        addCastingRule(Placement.first(), L2D);
    }

}
