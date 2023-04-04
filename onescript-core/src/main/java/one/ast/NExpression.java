package one.ast;

/**
 * An expression is any node which has the
 * ability to return a value.
 */
public abstract class NExpression<V> extends ASTNode {

    /**
     * Tries to evaluate the expression without any
     * context variables. This will only work with
     * very simple expressions and data types, hence
     * the name.
     *
     * @return The resulting value.
     */
    public V evaluateSimple() {
        throw new UnsupportedOperationException("Simple evaluation of " + getClass().getSimpleName() + " not supported");
    }

}
