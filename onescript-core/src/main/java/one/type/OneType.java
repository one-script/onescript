package one.type;

import one.type.cast.CastingRule;
import one.util.Placement;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * A compile and runtime description of a type
 * in OneScript.
 */
public abstract class OneType {

    /**
     * The internal name of this type.
     */
    private final String name;

    /**
     * The registered general casting rules.
     */
    private final List<CastingRule> castingRules = new ArrayList<>();

    protected OneType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Get the loaded JVM class mirroring this value
     * type if present, otherwise return null.
     *
     * @return The JVM class.
     */
    public abstract Class<?> getLoadedJVMClass();

    /**
     * Get the name of the JVM class, this should never
     * return null as this does not require anything
     * besides the descriptor to be loaded.
     *
     * @return The JVM class name.
     */
    public abstract String getJVMClassName();

    /**
     * Get the ObjectWeb ASM type mirror for
     * this value type.
     *
     * @return The ASM type.
     */
    public abstract Type getAsmType();

    public List<CastingRule> getCastingRules() {
        return castingRules;
    }

    /**
     * Insert a casting rule into the list.
     *
     * @param placement Determines the placement of the rule.
     * @param rule The rule.
     */
    public void addCastingRule(Placement<CastingRule> placement,
                               CastingRule rule) {
        placement.insert(castingRules, rule);
    }

    /**
     * Get the valid casting rule for casting
     * a value of this type to the given output type.
     *
     * @param outType The result type.
     * @param implicit If it is implicit.
     * @return The casting rule or null if absent.
     */
    public CastingRule getCastTo(OneType outType, boolean implicit) {
        for (CastingRule rule : castingRules) {
            if (implicit) { if (rule.canCastImplicit(this, outType)) return rule; }
            else if (rule.canCast(this, outType)) return rule;
        }

        return null;
    }

    @Override
    public String toString() {
        return "OneType(" + getName() + ")";
    }

}
