package one.ast.expr;

public class NMemberSet extends NExpression<Object> {

    /** The expression providing the target. */
    private NExpression<?> target;

    /** The name of the member. */
    private String name;

    /** The value of the assignment. */
    private NExpression<?> value;

    public NExpression<?> getTarget() {
        return target;
    }

    public NMemberSet setTarget(NExpression<?> target) {
        this.target = target;
        return this;
    }

    public String getName() {
        return name;
    }

    public NMemberSet setName(String name) {
        this.name = name;
        return this;
    }

    public NExpression<?> getValue() {
        return value;
    }

    public NMemberSet setValue(NExpression<?> value) {
        this.value = value;
        return this;
    }

    @Override
    public String getTypeName() {
        return "memberSet";
    }

    @Override
    public String getDataString() {
        return "target: " + target + ", name: " + name + ", value: " + value;
    }

}
