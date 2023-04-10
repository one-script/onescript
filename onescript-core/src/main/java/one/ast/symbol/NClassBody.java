package one.ast.symbol;

import one.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the definitions in a body of a class.
 *
 * Class bodies are the only scopes where symbols like
 * fields, methods and sub-classes can be defined.
 */
public class NClassBody extends ASTNode {

    /** All classes declared in this body. */
    private List<NClass> declaredClasses = new ArrayList<>();

    public List<NClass> getDeclaredClasses() {
        return declaredClasses;
    }

    public NClassBody setDeclaredClasses(List<NClass> declaredClasses) {
        this.declaredClasses = declaredClasses;
        return this;
    }

    public NClassBody addDeclaredClass(NClass nClass) {
        declaredClasses.add(nClass);
        return this;
    }

    @Override
    public String getTypeName() {
        return "classBody";
    }

    @Override
    public String getDataString() {
        return "classes: " + declaredClasses;
    }

}
