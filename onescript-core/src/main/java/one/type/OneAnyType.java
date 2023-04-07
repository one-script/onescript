package one.type;

import one.type.cast.CastingRule;
import one.type.cast.CastingRules;
import one.util.Placement;
import org.objectweb.asm.Type;

import static one.type.cast.CastingRules.*;

/**
 * The any type.
 * This is a loose, dynamic type.
 */
public class OneAnyType extends OneType {

    protected OneAnyType() {
        super("any");

        // Casting Rules
        addCastingRule(Placement.first(), CastingRules.FROM_ANY);
        addCastingRule(Placement.first(), CastingRules.TO_ANY);
    }

    @Override
    public Class<?> getLoadedJVMClass() {
        return Any.class;
    }

    @Override
    public String getJVMClassName() {
        return Any.class.getName();
    }

    @Override
    public Type getAsmType() {
        return Type.getType(Any.class);
    }

}
