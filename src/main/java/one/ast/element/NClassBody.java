package one.ast.element;

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

    /** All declared fields. */
    private List<NField> declaredFields = new ArrayList<>();

    /** All declared methods. */
    private List<NMethod> declaredMethods = new ArrayList<>();

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

    public List<NField> getDeclaredFields() {
        return declaredFields;
    }

    public NClassBody setDeclaredFields(List<NField> declaredFields) {
        this.declaredFields = declaredFields;
        return this;
    }

    public NClassBody addDeclaredField(NField field) {
        this.declaredFields.add(field);
        return this;
    }

    public List<NMethod> getDeclaredMethods() {
        return declaredMethods;
    }

    public NClassBody setDeclaredMethods(List<NMethod> declaredMethods) {
        this.declaredMethods = declaredMethods;
        return this;
    }

    public NClassBody addDeclaredMethod(NMethod method) {
        this.declaredMethods.add(method);
        return this;
    }

    public NClassBody addElement(NElement element) {
        if (element instanceof NClass nClass)
            addDeclaredClass(nClass);
        else if (element instanceof NField nField)
            addDeclaredField(nField);
        else if (element instanceof NMethod nMethod)
            addDeclaredMethod(nMethod);

        return this;
    }

    @Override
    public String getTypeName() {
        return "classBody";
    }

    @Override
    public String getDataString() {
        return "classes: " + declaredClasses + ", fields: " + declaredFields + ", methods: " + declaredMethods;
    }

}
