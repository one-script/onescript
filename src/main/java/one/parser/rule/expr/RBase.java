package one.parser.rule.expr;

import one.ast.expr.*;
import one.lang.OneOperator;
import one.parser.ParseContext;
import one.parser.error.OneParseException;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;
import one.parser.token.TokenType;
import one.symbol.Symbol;
import one.symbol.SymbolType;

@SuppressWarnings("rawtypes")
public class RBase extends ParserRule<NExpression> {

    public RBase() {
        super("base", "exprBase", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NExpression parseNode(ParseContext context) {
        // check for prefix unary operators
        if (context.currentType() == TokenType.OPERATOR) {
            OneOperator currentOp = context.current().getValueAs();
            OneOperator unaryOp = currentOp.toPrefixUnary();
            if (unaryOp == null)
                throw context.here(new OneParseException("operator " + currentOp + " does not apply as a prefix unary operator"));
            context.next();
            // TODO: maybe do this through rules by calling ParseContext#tryParseNext
            //  once ive optimized that, as this is unintuitive for developers
            NExpression factor =
                    /* note that this refers to RBase#parseNode,
                       so this will parse another factor, not any applicable rule */
                    this.parseNode(context);

            NExpression node = new NUnaryOp(unaryOp, factor);

            // perform constant optimization
            if (context.optimizeConstants() && factor instanceof NConstant) {
                node = NConstant.of(node.evaluateSimple());
            }

            return node;
        }

        /* collect initial node */
        NExpression node;

        // check for literal
        if ((node = context.tryParseNext("literal", NExpression.class)) != null) {

        } else if /* check for expression */ (context.currentType() == Tokens.LEFT_PAREN) {
            context.next();
            node = context.tryParseNext("exprExpr", NExpression.class);
            if (context.currentType() != Tokens.RIGHT_PAREN)
                throw context.here(new OneParseException("expected right parenthesis to close expression"));
            context.next();
        } else if /* check for identifier */ (context.currentType() == Tokens.IDENTIFIER) {
            // parse initial symbol
            Symbol symbol = context.parseSymbol(SymbolType.NAME);

            if /* parse assign */ (context.currentValue() == OneOperator.ASSIGN) {
                context.next();

                NExpression<?> expr = context.tryParseNext("exprExpr");

                node = new NSymbolSet()
                        .setSymbol(symbol)
                        .setValue(expr);
            } else /* parse call */ if (context.currentType() == Tokens.LEFT_PAREN) {
                context.next();

                node = NCall.parseCall(context,
                        new NSymbolCall().setSymbol(symbol));
            } else /* basic get, return get */ {
                node = new NSymbolGet()
                        .setSymbol(symbol);
            }
        }

        while (context.currentType() == Tokens.DOT) {
            context.next();
            if (context.currentType() != Tokens.IDENTIFIER)
                throw context.here(new OneParseException("Expected identifier to index with"));

            // get identifier
            String name = context.current().getValueAs();
            context.next();

            // check for assignment or call
            if (context.currentValue() == OneOperator.ASSIGN) {
                context.next();
                NExpression<?> v = context.tryParseNext("exprExpr");

                node = new NMemberSet()
                        .setTarget(node)
                        .setName(name)
                        .setValue(v);
            } else if (context.currentType() == Tokens.LEFT_PAREN) {
                context.next();
                node = NCall.parseCall(context,
                        new NMemberCall()
                                .setTarget(node)
                                .setName(name));
            } else {
                node = new NMemberGet()
                        .setTarget(node)
                        .setName(name);
            }
        }

        // check for postfix unary operators
        if (context.currentType() == TokenType.OPERATOR) {
            OneOperator currentOp = context.current().getValueAs();
            OneOperator unaryOp = currentOp.toPostfixUnary();
            if (unaryOp != null) {
                context.next();
                node = new NUnaryOp(unaryOp, node);

                // perform constant optimization
                if (context.optimizeConstants() && node instanceof NConstant) {
                    node = NConstant.of(node.evaluateSimple());
                }

                return node;
            }
        }

        return node;
    }

}
