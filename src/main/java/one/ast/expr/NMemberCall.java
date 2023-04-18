package one.ast.expr;

public class NMemberCall extends NCall {

    /** The expression providing the target. */
    private NExpression<?> target;

    /** The name of the member. */
    private String name;

    public NExpression<?> getTarget() {
        return target;
    }

    public NMemberCall setTarget(NExpression<?> target) {
        this.target = target;
        return this;
    }

    public String getName() {
        return name;
    }

    public NMemberCall setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getTypeName() {
        return "memberCall";
    }

    @Override
    public String getDataString() {
        return "target: " + target + ", name: " + name + ", " + super.getDataString();
    }

}
