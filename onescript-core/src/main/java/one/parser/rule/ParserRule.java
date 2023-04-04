package one.parser.rule;

import one.parser.ParseContext;
import one.ast.ASTNode;

/**
 * Each parser rule defines a single unit of parsing behaviour.
 * This behaviour fundamentally converts an input stream
 * of {@link one.parser.token.Token}s into an abstract syntax tree.
 *
 * A rule's tag dictates for what purpose it
 * should be accepted.
 *
 * For example: A parser with tag {@code exprFactor}
 * would be called when a different rule needs an expression
 * in general or a factor specifically.
 *
 * @param <N> The node type.
 */
public abstract class ParserRule<N extends ASTNode> {

    /** The name of this rule. */
    private final String name;

    /** The tag of this rule. */
    private final String tag;

    /** The priority of this rule.
     *  Higher is better. */
    private final int priority;

    public ParserRule(String name,
                      String tag,
                      int priority) {
        this.name = name;
        this.tag = tag;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public int getPriority() {
        return priority;
    }

    /**
     * Checks if the given query tag can match
     * with the tag of this rule.
     *
     * This just checks if this rule's tag starts
     * with or equals the given query.
     *
     * @param query The query.
     * @return If it can match.
     */
    public boolean tagMatches(String query) {
        return tag.startsWith(query);
    }

    /**
     * Checks if this rule could parse a node
     * for the given input stream.
     *
     * @param context The context.
     * @return If it could parse a node.
     */
    public abstract boolean canParse(ParseContext context);

    /**
     * Parses an abstract syntax tree node from the
     * input tokens provided by the context.
     *
     * @param context The context.
     * @return The node or null if failed.
     */
    public abstract N parseNode(ParseContext context);

}
