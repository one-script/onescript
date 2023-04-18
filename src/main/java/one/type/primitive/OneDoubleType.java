package one.type.primitive;

import one.type.Any;
import one.type.any.AnyDouble;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** Double primitive type. */
public class OneDoubleType extends OnePrimitiveType {

    public OneDoubleType() {
        super("double", Double.TYPE,
                JavaMethod.find(AnyDouble.class, "of", double.class), JavaMethod.find(Any.class, "asDouble"));

        // Casting Rules
        addCastingRule(Placement.first(), D2I);
        addCastingRule(Placement.first(), D2L);
        addCastingRule(Placement.first(), D2F);
    }

}
