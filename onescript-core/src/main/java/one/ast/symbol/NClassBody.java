package one.ast.symbol;

import one.ast.ASTNode;

/**
 * Represents the definitions in a body of a class.
 */
public class NClassBody extends ASTNode {

    @Override
    public String getTypeName() {
        return "classBody";
    }

}
