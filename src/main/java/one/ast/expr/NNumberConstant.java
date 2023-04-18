package one.ast.expr;

import one.parser.util.NumberConstant;

public class NNumberConstant extends NConstant<NumberConstant> {

    public NNumberConstant() { }

    public NNumberConstant(NumberConstant value) {
        super(value);
    }

    @Override
    public String getTypeName() {
        return "numberConstant";
    }

}
