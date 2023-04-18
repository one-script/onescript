package one.ast.expr;

public class NMemberGet extends NExpression<Object> {

    /** The expression providing the target. */
    private NExpression<?> target;

    /** The name of the member. */
    private String name;

    public NExpression<?> getTarget() {
        return target;
    }

    public NMemberGet setTarget(NExpression<?> target) {
        this.target = target;
        return this;
    }

    public String getName() {
        return name;
    }

    public NMemberGet setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getTypeName() {
        return "memberGet";
    }

    @Override
    public String getDataString() {
        return "target: " + target + ", name: " + name;
    }

}
