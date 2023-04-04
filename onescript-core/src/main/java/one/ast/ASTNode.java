package one.ast;

import one.parser.util.StringLocatable;
import one.parser.util.StringLocation;

/**
 * Represents a node in the abstract syntax tree.
 */
public abstract class ASTNode implements StringLocatable {

    private StringLocation stringLocation;

    /**
     * Get the type name of this node.
     *
     * @return The type name.
     */
    public abstract String getTypeName();

    /**
     * Get a string representing the contained
     * data and nodes in this node.
     *
     * @return The data string or null.
     */
    public String getDataString() {
        return null;
    }

    @SuppressWarnings("unchecked")
    public <N extends ASTNode> N as() {
        return (N) this;
    }

    @Override
    public ASTNode setLocation(StringLocation location) {
        this.stringLocation = location;
        return this;
    }

    @Override
    public StringLocation getLocation() {
        return stringLocation;
    }

    @Override
    public String toString() {
        final String ds = getDataString();
        return getTypeName() + (ds != null ? "(" + ds + ")" : "");
    }

}
