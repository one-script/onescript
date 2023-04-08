package one.parser;

import one.ast.ASTNode;
import one.parser.error.OneParseException;
import one.parser.rule.ParserRule;
import one.parser.token.Token;
import one.parser.token.TokenType;
import one.parser.token.Tokens;
import one.parser.util.StringLocatable;
import one.parser.util.StringLocation;
import one.script.Symbol;
import one.script.SymbolType;
import one.util.Sequence;
import one.util.SequenceReader;

import java.util.ArrayList;
import java.util.List;

/**
 * A context for the grammar parsing.
 *
 * Extends {@link SequenceReader} for convenience of
 * reading the source tokens.
 */
public class ParseContext extends SequenceReader<Token<?>> {

    /** The parser instance. */
    private final OneParser parser;

    /** The output root node. */
    private ASTNode rootNode;

    /** The root parser rule name. */
    private String rootParserRuleName;

    /*
        Settings
     */

    private boolean optimizeConstants = true;

    public ParseContext(OneParser parser,
                        Sequence<Token<?>> str,
                        String rootParserRuleName) {
        super(str, 0);
        this.parser = parser;
        this.rootParserRuleName = rootParserRuleName;
    }

    public OneParser getParser() {
        return parser;
    }

    public String getRootParserRuleName() {
        return rootParserRuleName;
    }

    public ASTNode getRootNode() {
        return rootNode;
    }

    public ParseContext setRootNode(ASTNode rootNode) {
        this.rootNode = rootNode;
        return this;
    }

    public TokenType<?> currentType() {
        Token<?> c = current();
        return c == null ? null : c.getType();
    }

    /**
     * End a segment if present, otherwise use
     * the current index and capture one character.
     *
     * @see #end()
     * @return The location.
     */
    public int endOrHere() {
        if (startIndices.size() == 0)
            return index();
        return end();
    }

    public StringLocation endOrHereStringLocation() {
        if (current() == null)
            return null;
        StringLocation end = current().getLocation();

        Token<?> startToken = getSequence().at(endOrHere());
        StringLocation start = startToken.getLocation();
        if (start == null)
            return null;
        return new StringLocation(start.getFile(), start.getString(), start.getStartIndex(), end.getEndIndex());
    }

    public <S extends StringLocatable> S endOrHere(S locatable) {
        StringLocation loc = endOrHereStringLocation();
        if (loc == null)
            return locatable;
        locatable.setLocation(loc);
        return locatable;
    }

    public Symbol expectSymbol(SymbolType type) {
        if (currentType() != Tokens.IDENTIFIER)
            throw endOrHere(new OneParseException("expected symbol name [id.]..."));
        List<String> parts = new ArrayList<>();
        TokenType<?> t;
        while ((t = currentType()) == Tokens.IDENTIFIER) {
            parts.add(current().getValueAs());
            next();
            if (currentType() != Tokens.DOT)
                break;
            next();
        }

        if (t == Tokens.ASTERISK) {
            parts.add("*");
        }

        return new Symbol(parts, type);
    }

    public void expectSemicolon() {
        if (currentType() != Tokens.SEMICOLON)
            throw endOrHere(new OneParseException("expected semicolon"));
        next();
    }

    /**
     * Tries to parse the next node under the
     * given query tag and matching the input stream
     * of tokens.
     *
     * Returns null if no node could be found.
     *
     * @param queryTag The tag to match.
     * @return The node.
     */
    @SuppressWarnings("unchecked")
    public <N> N tryParseNext(String queryTag) {
        // TODO: maybe cache the last successful parsers per tag
        //  and do some other shit for performance?
        ParserRule<?> bestRule = null;
        int bestPriority = Integer.MIN_VALUE;
        for (ParserRule<?> rule : parser.parserRuleList) {
            if (rule.getPriority() > bestPriority &&
                    rule.tagMatches(queryTag) &&
                    rule.canParse(this)) {
                bestRule = rule;
            }
        }

        if (bestRule == null)
            return null;

        return (N) bestRule.parseNode(this);
    }

    public <N> N tryParseNext(String queryTag, Class<N> nClass) {
        return tryParseNext(queryTag);
    }

    public boolean optimizeConstants() {
        return optimizeConstants;
    }

    /**
     * If the parser should optimize constant expressions.
     * A constant expression is an expression which can
     * be evaluated at compile-time.
     *
     * @param optimizeConstants Value.
     * @return This.
     */
    public ParseContext setOptimizeConstants(boolean optimizeConstants) {
        this.optimizeConstants = optimizeConstants;
        return this;
    }

}
