package one.ast.statement;

import one.ast.expr.NExpression;
import one.symbol.Symbol;

public class NLetStatement extends NStatement {

    public NLetStatement() { }

    public NLetStatement(String name, NExpression<?> value) {
        this.name = name;
        this.value = value;
    }

    /** The name of the local variable. */
    private String name;

    /** The type of the local variable. */
    private Symbol type;

    /** The initial value to assign. Can be null. */
    private NExpression<?> value;

    public String getName() {
        return name;
    }

    public NLetStatement setName(String name) {
        this.name = name;
        return this;
    }

    public Symbol getType() {
        return type;
    }

    public NLetStatement setType(Symbol type) {
        this.type = type;
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
        return "let";
    }

    @Override
    public String getDataString() {
        return (type != null ? "type: " + type + ", " : "") + "name: " + name + (value != null ? ", value: " + value : "");
    }
}
