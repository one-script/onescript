package one.ast.expr;

import one.symbol.Symbol;

public class NSymbolGet extends NExpression<Object> {

    /** The symbol. */
    private Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public NSymbolGet setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    @Override
    public String getTypeName() {
        return "symbolGet";
    }

    @Override
    public String getDataString() {
        return "symbol: " + symbol;
    }

}
