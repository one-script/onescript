package one.parser.rule.statement;

import one.ast.expr.NExpression;
import one.ast.statement.NLetStatement;
import one.ast.statement.NReturnStatement;
import one.ast.statement.NStatement;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.parser.error.OneSyntaxException;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;
import one.symbol.Symbol;
import one.symbol.SymbolType;

public class RStatement extends ParserRule<NStatement> {

    public RStatement() {
        super("statement", "statement", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NStatement parseNode(ParseContext context) {
        if (context.currentType() == Tokens.RETURN) {
            context.next();

            // collect expression to return
            NExpression<?> expr = context.tryParseNext("exprExpr");
            return new NReturnStatement(expr);
        }

        if (context.currentType() == Tokens.LET) {
            context.next();

            // collect type and name
            Symbol nameOrType = context.expectSymbol(SymbolType.TYPE);
            Symbol nameIfType = context.parseSymbol(SymbolType.NAME);

            Symbol type = nameIfType == null ? null : nameOrType;
            Symbol name = nameIfType == null ? nameOrType : nameIfType;

            NLetStatement statement = new NLetStatement();
            statement
                    .setName(name.getName())
                    .setType(type);

            // collect initial value
            if (context.currentValue() == OneOperator.ASSIGN) {
                context.next();

                NExpression<?> expr = context.tryParseNext("exprExpr");
                if (expr == null)
                    throw context.endOrHere(new OneParseException("Expected expression to initialize variable"));
                statement.setValue(expr);
            }

            return statement;
        }

        throw context.endOrHere(new OneSyntaxException("Expected statement"));
    }

}
