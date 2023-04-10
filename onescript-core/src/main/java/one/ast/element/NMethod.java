package one.ast.element;

import one.ast.ASTNode;
import one.symbol.Symbol;

public class NMethod extends NElement {

    public NMethod(Symbol symbol) {
        super(symbol);
    }

    // TODO: params

    /** The return type. */
    private Symbol returnType;

    /** The method body. */
    private ASTNode body;

    public Symbol getReturnType() {
        return returnType;
    }

    public NMethod setReturnType(Symbol returnType) {
        this.returnType = returnType;
        return this;
    }

    public ASTNode getBody() {
        return body;
    }

    public NMethod setBody(ASTNode body) {
        this.body = body;
        return this;
    }

    @Override
    public String getDataString() {
        return "returnType: " + returnType + ", body: " + body;
    }

    @Override
    public String getTypeName() {
        return "method";
    }

}
