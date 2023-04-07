package one.type.cast;

import one.type.*;
import one.util.asm.JavaMethod;
import one.util.asm.MethodBuilder;

import java.lang.invoke.MethodHandle;
import java.util.function.Consumer;

import static one.type.OneTypes.*;

/**
 * Mostly implicit casting rules for casting
 * primitives.
 */
public final class CastingRules {

    // creates a new primitive casting rule
    // based on the input parameters
    private static CastingRule makePrimitive(boolean canImplicit, OneType src, OneType dest, Consumer<MethodBuilder> builderFunc,
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

    public static final CastingRule FROM_ANY = new CastingRule() {
        @Override public boolean canCast(OneType sourceType, OneType resultType) { return sourceType == ANY && resultType instanceof OneStrongType; }
        @Override public boolean canCastImplicit(OneType sourceType, OneType resultType) { return canCast(sourceType, resultType); }
        @Override public void compileCast(MethodBuilder builder, OneType sourceType, OneType resultType) { ((OneStrongType)resultType).putFromAny(builder); }
    };

    public static final CastingRule TO_ANY = new CastingRule() {
        @Override public boolean canCast(OneType sourceType, OneType resultType) { return sourceType instanceof OneStrongType && resultType == ANY; }
        @Override public boolean canCastImplicit(OneType sourceType, OneType resultType) { return canCast(sourceType, resultType); }
        @Override public void compileCast(MethodBuilder builder, OneType sourceType, OneType resultType) { ((OneStrongType)sourceType).putToAny(builder); };
    };

    public static final CastingRule D2I = makePrimitive(true, DOUBLE, INT, MethodBuilder::d2i, false);
    public static final CastingRule D2L = makePrimitive(true, DOUBLE, LONG, MethodBuilder::d2l, false);
    public static final CastingRule D2F = makePrimitive(true, DOUBLE, FLOAT, MethodBuilder::d2f, false);

    public static final CastingRule F2I = makePrimitive(true, FLOAT, INT, MethodBuilder::f2i, false);
    public static final CastingRule F2L = makePrimitive(true, FLOAT, LONG, MethodBuilder::f2l, false);
    public static final CastingRule F2D = makePrimitive(true, FLOAT, DOUBLE, MethodBuilder::f2d, false);

    public static final CastingRule L2I = makePrimitive(true, LONG, INT, MethodBuilder::l2i, false);
    public static final CastingRule L2F = makePrimitive(true, LONG, FLOAT, MethodBuilder::l2f, false);
    public static final CastingRule L2D = makePrimitive(true, LONG, DOUBLE, MethodBuilder::l2d, false);

    public static final CastingRule I2B = makePrimitive(true, INT, BYTE, MethodBuilder::i2b, true);
    public static final CastingRule I2C = makePrimitive(true, INT, CHAR , MethodBuilder::i2c, true);
    public static final CastingRule I2S = makePrimitive(true, INT, SHORT, MethodBuilder::i2s, true);
    public static final CastingRule I2L = makePrimitive(true, INT, LONG, MethodBuilder::i2l, true);
    public static final CastingRule I2F = makePrimitive(true, INT, FLOAT, MethodBuilder::i2f, true);
    public static final CastingRule I2D = makePrimitive(true, INT, DOUBLE, MethodBuilder::i2d, true);

}
