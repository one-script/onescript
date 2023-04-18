package one.type.primitive;

import one.type.Any;
import one.type.any.AnyByte;
import one.type.any.AnyFloat;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** Float primitive type. */
public class OneFloatType extends OnePrimitiveType {

    public OneFloatType() {
        super("float", Float.TYPE,
                JavaMethod.find(AnyFloat.class, "of", float.class), JavaMethod.find(Any.class, "asFloat"));

        // Casting Rules
        addCastingRule(Placement.first(), F2I);
        addCastingRule(Placement.first(), F2L);
        addCastingRule(Placement.first(), F2D);
    }

}
