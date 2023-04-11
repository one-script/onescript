package one.parser.rule.element;

import one.ast.ASTNode;
import one.ast.expr.NExpression;
import one.ast.element.*;
import one.lang.OneModifier;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.TokenType;
import one.parser.token.Tokens;
import one.symbol.Symbol;
import one.symbol.SymbolType;

import java.util.ArrayList;
import java.util.List;

public class RClassBody extends ParserRule<NClassBody> {

    public RClassBody() {
        super("classBody", "bodyClass", 1);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    /*
        Elements
     */



    /*
        Main Parser
     */

    @Override
    public NClassBody parseNode(ParseContext context) {
        final NClassBody body = new NClassBody();

        final List<NAnnotation> annotationBuf = new ArrayList<>();
        final List<OneModifier> modifierBuf = new ArrayList<>();

        while (context.current() != null && context.currentType() != Tokens.RIGHT_BRACE) {
            // parse annotation
            if (context.currentType() == Tokens.AT) {
                NAnnotation annotation = ElementParsing.parseAnnotation(context);
                annotationBuf.add(annotation);
                continue;
            }

            // parse modifiers
            if (context.currentType() == TokenType.MODIFIER) {
                modifierBuf.add(context.current().getValueAs());
                context.next();
                continue;
            }

            // parse elements
            NElement element = null;

            /* class */
            if (context.currentType() == Tokens.CLASS) {
                element = context.tryParseNext("declClass");
            }

            /* fields and methods */
            else if (context.currentType() == Tokens.IDENTIFIER) {
                Symbol type = context.parseSymbolGeneric(SymbolType.TYPE);
                Symbol name = context.parseSymbol(SymbolType.NAME);

                /* check method or field */
                if (context.currentType() == Tokens.LEFT_PAREN) {
                    // TODO: collect method parameters
                    context.next(2); // for now skip )

                    // collect method body
                    ASTNode methodBody;
                    if (context.currentType() == Tokens.LEFT_BRACE) {
                        context.next();
                        methodBody = context.tryParseNext("block");
                    } else if (context.currentType() == Tokens.ARROW) {
                        context.next();
                        methodBody = context.tryParseNext("exprExpr");
                    } else {
                        methodBody = null;
                    }

                    element = new NMethod(name)
                            .setReturnType(type)
                            .setBody(methodBody);
                } else {
                    NExpression<?> fieldInitializer = null;
                    if (context.currentType() == Tokens.ASSIGN) {
                        context.next();
                        fieldInitializer = context.tryParseNext("exprExpr");
                    }

                    element = new NField(name)
                            .setType(type)
                            .setInitializer(fieldInitializer);
                }
            }

            if (element != null) {
                element.getAnnotations().addAll(annotationBuf);
                element.getModifiers().addAll(modifierBuf);
                annotationBuf.clear();
                modifierBuf.clear();

                body.addElement(element);
            }
        }

        return body;
    }

}
