package one.type.primitive;

import one.type.Any;
import one.type.any.AnyByte;
import one.type.any.AnyInt;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** Integer primitive type. */
public class OneIntType extends OnePrimitiveType {

    public OneIntType() {
        super("int", Integer.TYPE,
                JavaMethod.find(AnyInt.class, "of", int.class), JavaMethod.find(Any.class, "asInt"));

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
