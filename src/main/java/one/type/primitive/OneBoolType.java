package one.type.primitive;

import one.type.Any;
import one.type.any.AnyBool;
import one.util.Placement;
import one.util.asm.JavaMethod;
import one.util.asm.MethodBuilder;
import org.objectweb.asm.Opcodes;

import static one.type.cast.CastingRules.*;

/** Boolean primitive type. */
public class OneBoolType extends OnePrimitiveType {

    public OneBoolType() {
        super("boolean", Boolean.TYPE,
                JavaMethod.find(AnyBool.class, "of", boolean.class), JavaMethod.find(Any.class, "asBool"));

        // Casting Rules
        addCastingRule(Placement.first(), I2B);
        addCastingRule(Placement.first(), I2C);
        addCastingRule(Placement.first(), I2S);
        addCastingRule(Placement.first(), I2L);
        addCastingRule(Placement.first(), I2F);
        addCastingRule(Placement.first(), I2D);
    }

}
