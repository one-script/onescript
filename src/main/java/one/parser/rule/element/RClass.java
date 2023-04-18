package one.parser.rule.element;

import one.ast.element.NClass;
import one.ast.element.NClassBody;
import one.lang.OneModifier;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;
import one.symbol.Symbol;
import one.symbol.SymbolType;

import java.util.ArrayList;
import java.util.List;

public class RClass extends ParserRule<NClass> {

    public RClass() {
        super("class", "declClass", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NClass parseNode(ParseContext context) {
        // collect modifiers
        boolean foundClass = false;
        List<OneModifier> modifiers = new ArrayList<>();
        while (context.currentType() != Tokens.IDENTIFIER) {
            if (context.currentType() == Tokens.CLASS) {
                foundClass = true;
                context.next();
                continue;
            }

            modifiers.add(context.current().getValueAs());
            context.next();
        }

        if (!foundClass)
            return null;

        Symbol name = context.expectSymbol(SymbolType.TYPE);
        NClass nClass = new NClass(name);
        nClass.setModifiers(modifiers);

        // parse data values
        if (context.currentType() == Tokens.LEFT_PAREN) {
            // TODO: implement record/data class parsing

            // example: pub class MyClass(final String a = "hey guys", final any b, any c)
            //  would generate
            /*
                pub class MyClass {
                    final String a = "hey guys";
                    final any b;
                    any c;

                    // Constructors
                    //   Since 'a' is optional it would generate constructors:
                    //     MyClass(a, b, c)
                    //     MyClass(b, c)

                    // Getters and Setters
                }
             */
        }

        // parse supertypes
        if (context.currentType() == Tokens.COLON) {
            context.next();
            Symbol superSymbol;
            while ((superSymbol = context.parseSymbol(SymbolType.TYPE)) != null) {
                nClass.addSuper(superSymbol);
            }
        }

        // parse body
        NClassBody body;
        if (context.currentType() == Tokens.LEFT_BRACE) {
            context.next();
            body = context.tryParseNext("bodyClass");
        } else {
            body = new NClassBody();
        }

        nClass.setBody(body);
        return nClass;
    }

}
