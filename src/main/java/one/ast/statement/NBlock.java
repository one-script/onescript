package one.ast.statement;

import one.ast.ASTNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of statements.
 */
public class NBlock extends /* maybe this should inherit NStatement? */ ASTNode {

    /** The list of all statements, in order. */
    private final List<NStatement> statements;

    public NBlock() {
        this.statements = new ArrayList<>();
    }

    public NBlock(List<NStatement> statements) {
        this.statements = statements;
    }

    public List<NStatement> getStatements() {
        return statements;
    }

    public NBlock addStatement(NStatement statement) {
        statements.add(statement);
        return this;
    }

    @Override
    public String getTypeName() {
        return "block";
    }

    @Override
    public String getDataString() {
        return statements.toString();
    }

}
