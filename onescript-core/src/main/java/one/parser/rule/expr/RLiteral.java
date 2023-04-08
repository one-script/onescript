package one.parser.rule.expr;

import one.ast.expr.NExpression;
import one.ast.expr.NNumberConstant;
import one.ast.expr.NStringConstant;
import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;
import one.parser.token.Token;

/**
 * NOTE: Separated from {@link RFactor} for flexibility.
 * Like this you have the ability to hook into only literals
 * and let factor do the rest of the processing.
 */
@SuppressWarnings("rawtypes")
public class RLiteral extends ParserRule<NExpression> {

    public RLiteral() {
        super("literal", "literal", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return context.current().getType().is("literal");
    }

    @Override
    public NExpression parseNode(ParseContext context) {
        Token<?> token = context.current();

        // number literal //
        if (token.getType() == Tokens.NUMBER_LITERAL) {
            context.next();
            return new NNumberConstant(token.getValueAs());
        }

        // string literal //
        if (token.getType() == Tokens.STRING_LITERAL) {
            context.next();
            return new NStringConstant(token.getValueAs());
        }

        throw context.endOrHere(new OneParseException("expected any literal token, got " + context.currentType()));
    }

}
