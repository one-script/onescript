package one.ast;

public class NStringConstant extends NExpression {

    public NStringConstant() { }

    public NStringConstant(String value) {
        this.value = value;
    }

    /** The value. */
    private String value;

    @Override
    public String getTypeName() {
        return "strConstant";
    }

    public String getValue() {
        return value;
    }

}
