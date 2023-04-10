package one.parser.rule.symbol;

import one.ast.symbol.NAnnotation;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;
import one.symbol.Symbol;
import one.symbol.SymbolType;

public class RAnnotation extends ParserRule<NAnnotation> {

    public RAnnotation() {
        super("annotation", "decl", 1);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return context.currentType() == Tokens.AT;
    }

    @Override
    public NAnnotation parseNode(ParseContext context) {
        context.begin();
        context.next(); // skip @
        Symbol symbol = context.expectSymbol(SymbolType.CLASS);
        NAnnotation annotation = new NAnnotation(symbol);

        // check for parameters
        if (context.currentType() == Tokens.LEFT_PAREN) {
            context.next();

            // TODO: parse annotation parameters
            //  example: @A("abc")
            //  example: @B(name = "hi", array = { 1, 2, 3 })
            //  example: @C(name = /* runtime expression */ !(/* expr */))
        }

        return context.endOrHere(annotation);
    }

}
