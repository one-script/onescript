package one.parser.rule.element;

import one.ast.element.NAnnotation;
import one.parser.ParseContext;
import one.parser.token.Tokens;
import one.symbol.Symbol;
import one.symbol.SymbolType;

/**
 * Methods for parsing OneScript elements.
 */
public class ElementParsing {

    public static NAnnotation parseAnnotation(ParseContext context) {
        context.begin();
        context.next(); // skip @
        Symbol symbol = context.expectSymbol(SymbolType.TYPE);
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
