package one.ast.statement;

import one.ast.expr.NExpression;

public class NLetStatement extends NStatement {

    public NLetStatement() { }

    public NLetStatement(String name, NExpression<?> value) {
        this.name = name;
        this.value = value;
    }

    /** The name of the local variable. */
    private String name;

    /** The initial value to assign. Can be null. */
    private NExpression<?> value;

    public String getName() {
        return name;
    }

    public NLetStatement setName(String name) {
        this.name = name;
        return this;
    }

    public NExpression<?> getValue() {
        return value;
    }

    public NLetStatement setValue(NExpression<?> value) {
        this.value = value;
        return this;
    }

    @Override
    public String getTypeName() {
        return "letStatement";
    }

}
