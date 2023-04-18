package one.ast.expr;

import one.symbol.Symbol;

public class NSymbolSet extends NExpression<Object> {

    /** The symbol. */
    private Symbol symbol;

    /** The value to set. */
    private NExpression<?> value;

    public Symbol getSymbol() {
        return symbol;
    }

    public NSymbolSet setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public NExpression<?> getValue() {
        return value;
    }

    public NSymbolSet setValue(NExpression<?> value) {
        this.value = value;
        return this;
    }

    @Override
    public String getTypeName() {
        return "symbolSet";
    }

    @Override
    public String getDataString() {
        return "symbol: " + symbol + ", value: " + value;
    }

}
