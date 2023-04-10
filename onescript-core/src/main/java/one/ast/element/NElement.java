package one.ast.element;

import one.ast.ASTNode;
import one.lang.OneModifier;
import one.symbol.Symbol;

import java.util.ArrayList;
import java.util.List;

public abstract class NElement extends ASTNode {

    /** The name/symbol of this element. */
    private final Symbol symbol;

    /** The annotations on this element. */
    private List<NAnnotation> annotations = new ArrayList<>();

    /** The modifiers on this element. */
    private List<OneModifier> modifiers = new ArrayList<>();

    public NElement(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public List<NAnnotation> getAnnotations() {
        return annotations;
    }

    public NElement setAnnotations(List<NAnnotation> annotations) {
        this.annotations = annotations;
        return this;
    }

    public NElement addAnnotation(NAnnotation annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public List<OneModifier> getModifiers() {
        return modifiers;
    }

    public NElement setModifiers(List<OneModifier> modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public NElement addModifier(OneModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    @Override
    public String getDataString() {
        return symbol + " modifiers: " + modifiers + ", annotations: " + annotations;
    }

}
