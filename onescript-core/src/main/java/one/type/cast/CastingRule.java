package one.type.cast;

import one.type.OneType;

/**
 * Defines rules for casting between types.
 */
public interface CastingRule {

    /**
     * Check if this rule applies to the given
     * source and result types.
     *
     * In a casting compilation, the first casting
     * rule which can cast in that context is
     * chosen to compile the cast.
     *
     * This should not take into account eventual
     * runtime edge cases.
     *
     * @param sourceType The source type.
     * @param resultType The result type.
     * @return If this rule applies and can cast the type.
     */
    boolean canCast(OneType sourceType, OneType resultType);

    /**
     * Check if this casting rule can implicitly cast
     * the given source and result types.
     *
     * Same behaviour as {@link #canCast(OneType, OneType)}.
     *
     * @param sourceType The source type.
     * @param resultType The result type.
     * @return If it can cast implicitly.
     */
    boolean canCastImplicit(OneType sourceType, OneType resultType);

    /**
     * Compiles the cast with the given source and
     * result types into the builder.
     *
     * At the execution of the compiled code the
     * source value will be at the top of the stack.
     *
     * @param builder The method builder.
     * @param sourceType The source type.
     * @param resultType The result type.
     */
    void compileCast(/* TODO */ Object builder, OneType sourceType, OneType resultType);

}
