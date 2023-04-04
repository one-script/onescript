package one.parser.rule;

import one.ast.NExpression;
import one.ast.NNumberConstant;
import one.ast.NStringConstant;
import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.parser.token.BuiltInTokenTypes;
import one.parser.token.Token;

/**
 * NOTE: Separated from {@link RFactor} for flexibility.
 * Like this you have the ability to hook into only literals
 * and let factor do the rest of the processing.
 */
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
        if (token.getType() == BuiltInTokenTypes.NUMBER_LITERAL) {
            return new NNumberConstant(token.getValueAs());
        }

        // string literal //
        if (token.getType() == BuiltInTokenTypes.STRING_LITERAL) {
            return new NStringConstant(token.getValueAs());
        }

        throw context.endOrHere(new OneParseException("expected any literal token, got " + context.currentType()));
    }

}
