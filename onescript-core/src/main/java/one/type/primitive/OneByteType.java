package one.type.primitive;

import one.type.Any;
import one.type.any.AnyBool;
import one.type.any.AnyByte;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** Byte primitive type. */
public class OneByteType extends OnePrimitiveType {

    public OneByteType() {
        super("byte", Byte.TYPE,
                JavaMethod.find(AnyByte.class, "of", byte.class), JavaMethod.find(Any.class, "asByte"));

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
