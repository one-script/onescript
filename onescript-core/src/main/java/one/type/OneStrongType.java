package one.type;

import one.type.cast.CastingRules;
import one.util.Placement;
import one.util.asm.MethodBuilder;

/**
 * Denotes a type which is strongly typed.
 */
public abstract class OneStrongType extends OneType {

    protected OneStrongType(String name) {
        super(name);

        // Casting Rules
        addCastingRule(Placement.first(), CastingRules.FROM_ANY);
        addCastingRule(Placement.first(), CastingRules.TO_ANY);
    }

    /**
     * Compile and add a cast from this type to an {@link Any}.
     *
     * @param builder The method bytecode builder.
     */
    public void compileToAny(MethodBuilder builder) {
        throw new UnsupportedOperationException("Type " + getName() + " can not be cast to an any value: Not implemented");
    }

    /**
     * Compile and add a cast to this type from an {@link Any}.
     *
     * @param builder The method bytecode builder.
     */
    public void compileFromAny(MethodBuilder builder) {
        throw new UnsupportedOperationException("Type " + getName() + " can not be cast from an any value: Not implemented");
    }

}
