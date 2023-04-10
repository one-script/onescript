package one.parser.rule.symbol;

import one.ast.symbol.NClass;
import one.ast.symbol.NClassBody;
import one.lang.OneModifier;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.TokenType;
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
        return context.currentType() == Tokens.CLASS;
    }

    @Override
    public NClass parseNode(ParseContext context) {
        // skip the class keyword
        context.next();

        // collect modifiers
        List<OneModifier> modifiers = new ArrayList<>();
        while (context.currentType() == TokenType.MODIFIER) {
            modifiers.add(context.current().getValueAs());
            context.next();
        }

        Symbol name = context.expectSymbol(SymbolType.CLASS);
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
            while ((superSymbol = context.parseSymbol(SymbolType.CLASS)) != null) {
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
