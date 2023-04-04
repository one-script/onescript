package one.ast;

@SuppressWarnings("rawtypes")
public class NGetMember extends NExpression {

    /** The target value to index. */
    private NExpression target;
    /** The key. */
    private NExpression key;

    public NGetMember() { }

    public NGetMember(NExpression target, NExpression key) {
        this.target = target;
        this.key = key;
    }

    @Override
    public String getTypeName() {
        return "getMember";
    }

    public NExpression getTarget() {
        return target;
    }

    public NGetMember setTarget(NExpression target) {
        this.target = target;
        return this;
    }

    public NExpression getKey() {
        return key;
    }

    public NGetMember setKey(NExpression key) {
        this.key = key;
        return this;
    }

    public boolean isKeyAnIdentifier() {
        return key instanceof NStringConstant;
    }

}
