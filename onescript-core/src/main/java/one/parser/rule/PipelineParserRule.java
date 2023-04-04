package one.parser.rule;

import one.ast.ASTNode;
import one.parser.ParseContext;
import one.parser.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A flexible, dynamically buildable parser rule. Probably
 * unfit for more complex parsing but for simple extensions
 * it would work great.
 *
 * TODO: explain how it works
 */
public class PipelineParserRule<N extends ASTNode> extends ParserRule<N> {

    public interface Check {
        boolean test(ParseContext context);
    }

    public interface Producer {
        ASTNode parseNext(ParseContext context, ASTNode last);
    }

    public PipelineParserRule(String name, String tag, int priority) {
        super(name, tag, priority);
    }

    /**
     * The pipeline of predicates for checking if the rule can parse.
     */
    private final List<Check> checkPipeline = new ArrayList<>();

    /**
     * The pipeline of functions to parse it into nodes.
     */
    private final List<Producer> producerPipeline = new ArrayList<>();

    @Override
    public boolean canParse(ParseContext context) {
        context.begin();
        for (Check predicate : checkPipeline) {
            if (!predicate.test(context))
                return false;
        }

        context.reset();
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public N parseNode(ParseContext context) {
        context.begin();
        ASTNode node = null;
        for (Producer producer : producerPipeline) {
            node = producer.parseNext(context, node);
            if (node == null) {
                context.reset();
                return null;
            }
        }

        context.end();
        return (N) node;
    }

    /* Building Methods */

    public PipelineParserRule<N> thenConsumeTokens(final Function<List<Token<?>>, ASTNode> function,
                                                   final String... tokenTypes) {
        checkPipeline.add(context -> {
            for (String tkType : tokenTypes) {
                if (!context.currentType().is(tkType)) {
                    return false;
                }

                context.next();
            }

            return true;
        });

        producerPipeline.add((context, last) -> {
            List<Token<?>> tokens = new ArrayList<>();
            context.prev();
            for (int i = 0; i < tokenTypes.length; i++)
                tokens.add(context.next());
            context.next();

            return function.apply(tokens);
        });

        return this;
    }

}
