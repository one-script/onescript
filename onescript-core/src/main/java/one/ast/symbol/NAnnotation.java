package one.ast.symbol;

import one.ast.ASTNode;
import one.ast.expr.NExpression;
import one.symbol.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a parsed annotation.
 */
public class NAnnotation extends ASTNode {

    public NAnnotation() { }

    public NAnnotation(Symbol symbol) {
        this.symbol = symbol;
    }

    /**
     * The unqualified class name for this annotation.
     */
    Symbol symbol;

    /**
     * The unnamed parameter if specified.
     */
    NExpression<?> unnamedParam = null;

    /**
     * The named parameter values.
     */
    Map<String, NExpression<?>> namedParams = new HashMap<>();

    public Symbol getSymbol() {
        return symbol;
    }

    public NAnnotation setSymbol(Symbol symbol) {
        this.symbol = symbol;
        return this;
    }

    public NExpression<?> getUnnamedParam() {
        return unnamedParam;
    }

    public NAnnotation setUnnamedParam(NExpression<?> unnamedParam) {
        this.unnamedParam = unnamedParam;
        return this;
    }

    public Map<String, NExpression<?>> getNamedParams() {
        return namedParams;
    }

    public NAnnotation setNamedParams(Map<String, NExpression<?>> namedParams) {
        this.namedParams = namedParams;
        return this;
    }

    @Override
    public String getTypeName() {
        return "annotation";
    }

}
