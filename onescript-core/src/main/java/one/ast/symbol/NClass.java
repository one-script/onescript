package one.ast.symbol;

import one.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

/** Class declaration. */
public class NClass extends NElement {

    /** The class' body. */
    private NClassBody body;

    /** The list of declared superclasses/interfaces. */
    private List<Symbol> supers = new ArrayList<>();

    public NClass(Symbol symbol) {
        super(symbol);
    }

    public NClassBody getBody() {
        return body;
    }

    public NClass setBody(NClassBody body) {
        this.body = body;
        return this;
    }

    public List<Symbol> getSupers() {
        return supers;
    }

    public NClass setSupers(List<Symbol> supers) {
        this.supers = supers;
        return this;
    }

    public NClass addSuper(Symbol symbol) {
        this.supers.add(symbol);
        return this;
    }

    @Override
    public String getDataString() {
        return super.getDataString() + ", supers: " + supers;
    }

    @Override
    public String getTypeName() {
        return "class";
    }

}
