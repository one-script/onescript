package one.ast;

public class NStringConstant extends NConstant<String> {

    public NStringConstant() { }

    public NStringConstant(String value) {
        super(value);
    }

    @Override
    public String getTypeName() {
        return "strConstant";
    }

}
