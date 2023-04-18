package one.ast.element;

import one.ast.expr.NExpression;
import one.symbol.Symbol;

public class NField extends NElement {

    public NField(Symbol symbol) {
        super(symbol);
    }

    /** The type of this field .*/
    private Symbol type;

    /** The initializer expression. */
    private NExpression<?> initializer;

    public Symbol getType() {
        return type;
    }

    public NField setType(Symbol type) {
        this.type = type;
        return this;
    }

    public NExpression<?> getInitializer() {
        return initializer;
    }

    public NField setInitializer(NExpression<?> initializer) {
        this.initializer = initializer;
        return this;
    }

    @Override
    public String getTypeName() {
        return "field";
    }

    @Override
    public String getDataString() {
        return super.getDataString() + ", type: " + type +
                (initializer != null ? ", init: " + initializer : "");
    }

}
