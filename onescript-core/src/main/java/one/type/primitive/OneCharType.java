package one.type.primitive;

import one.type.Any;
import one.type.any.AnyByte;
import one.type.any.AnyChar;
import one.util.Placement;
import one.util.asm.JavaMethod;

import static one.type.cast.CastingRules.*;

/** The character primitive. */
public class OneCharType extends OnePrimitiveType {

    protected OneCharType() {
        super("char", Character.TYPE,
                JavaMethod.find(AnyChar.class, "of", char.class), JavaMethod.find(Any.class, "asChar"));

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
