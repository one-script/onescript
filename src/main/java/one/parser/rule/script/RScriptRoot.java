package one.parser.rule.script;

import one.ast.script.NScriptRoot;
import one.ast.statement.NStatement;
import one.lang.OneModifier;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.TokenType;
import one.parser.token.Tokens;
import one.symbol.Symbol;
import one.symbol.SymbolQualifier;
import one.symbol.SymbolType;

public class RScriptRoot extends ParserRule<NScriptRoot> {

    public RScriptRoot() {
        super("scriptRoot", "scriptRoot", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NScriptRoot parseNode(ParseContext context) {
        NScriptRoot root = new NScriptRoot();

        // parse until EOF
        while (context.current() != null) {
            // import statements
            if (context.currentType() == Tokens.IMPORT) {
                context.next();

                // import (classes)
                if (context.currentType() == Tokens.IDENTIFIER) {
                    Symbol symbol = context.expectSymbol(SymbolType.TYPE);
                    root.addSymbolQualifier(SymbolQualifier.qualifyClasses(symbol));
                }

                // import static (methods and field)
                if (context.currentType() == TokenType.MODIFIER && context.current().getValueAs() == OneModifier.STATIC) {
                    Symbol symbol = context.expectSymbol(SymbolType.MEMBER);
                    root.addSymbolQualifier(SymbolQualifier.qualifyMembers(symbol));
                }
            }

            // package statement
            if (context.currentType() == Tokens.PACKAGE) {
                context.next();
                Symbol pk = context.expectSymbol(SymbolType.PACKAGE);
                root.setScriptPackage(pk.getName());
            }

            // TODO: class, method and field definitions

            // other statements
            NStatement statement = context.tryParseNext("statement");
            if (statement != null) {
                root.getScriptRunBlock().addStatement(statement);
            }
        }

        return root;
    }

}
