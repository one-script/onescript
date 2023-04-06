package one.type.cast;

import one.type.OneType;
import one.util.asm.MethodBuilder;

import java.util.function.Consumer;

import static one.type.primitive.OnePrimitives.*;

/**
 * Mostly implicit casting rules for casting
 * primitives.
 */
public final class PrimitiveCastingRules {

    // creates a new primitive casting rule
    // based on the input parameters
    private static CastingRule makeRule(boolean canImplicit, OneType src, OneType dest, Consumer<MethodBuilder> builderFunc,
                                        boolean ignoreSrc) {
        return new CastingRule() {
            @Override
            public boolean canCast(OneType sourceType, OneType resultType) {
                return (ignoreSrc || sourceType == src) && resultType == dest;
            }

            @Override
            public boolean canCastImplicit(OneType sourceType, OneType resultType) {
                return canImplicit && canCast(sourceType, resultType);
            }

            @Override
            public void compileCast(MethodBuilder builder, OneType sourceType, OneType resultType) {
                builderFunc.accept(builder);
            }
        };
    }

    /////////////////////////////////////////////////////

    public static final CastingRule D2I = makeRule(true, DOUBLE, INT, MethodBuilder::d2i, false);
    public static final CastingRule D2L = makeRule(true, DOUBLE, LONG, MethodBuilder::d2l, false);
    public static final CastingRule D2F = makeRule(true, DOUBLE, FLOAT, MethodBuilder::d2f, false);

    public static final CastingRule F2I = makeRule(true, FLOAT, INT, MethodBuilder::f2i, false);
    public static final CastingRule F2L = makeRule(true, FLOAT, LONG, MethodBuilder::f2l, false);
    public static final CastingRule F2D = makeRule(true, FLOAT, DOUBLE, MethodBuilder::f2d, false);

    public static final CastingRule L2I = makeRule(true, LONG, INT, MethodBuilder::l2i, false);
    public static final CastingRule L2F = makeRule(true, LONG, FLOAT, MethodBuilder::l2f, false);
    public static final CastingRule L2D = makeRule(true, LONG, DOUBLE, MethodBuilder::l2d, false);

    public static final CastingRule I2B = makeRule(true, INT, BYTE, MethodBuilder::i2b, true);
    public static final CastingRule I2C = makeRule(true, INT, CHAR , MethodBuilder::i2c, true);
    public static final CastingRule I2S = makeRule(true, INT, SHORT, MethodBuilder::i2s, true);
    public static final CastingRule I2L = makeRule(true, INT, LONG, MethodBuilder::i2l, true);
    public static final CastingRule I2F = makeRule(true, INT, FLOAT, MethodBuilder::i2f, true);
    public static final CastingRule I2D = makeRule(true, INT, DOUBLE, MethodBuilder::i2d, true);

}
