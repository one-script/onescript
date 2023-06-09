package one.type.primitive;

import one.type.Any;
import one.type.any.AnyByte;
import one.type.any.AnyShort;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** Short primitive type. */
public class OneShortType extends OnePrimitiveType {

    protected OneShortType() {
        super("short", Short.TYPE,
                JavaMethod.find(AnyShort.class, "of", short.class), JavaMethod.find(Any.class, "asShort"));

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
