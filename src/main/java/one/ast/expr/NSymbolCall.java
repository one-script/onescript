package one.ast.expr;

import one.symbol.Symbol;

public class NSymbolCall extends NCall {

    /** The symbol. */
    private Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public NSymbolCall setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    @Override
    public String getTypeName() {
        return "symbolCall";
    }

    @Override
    public String getDataString() {
        return super.getDataString() + ", symbol: " + symbol;
    }

}
