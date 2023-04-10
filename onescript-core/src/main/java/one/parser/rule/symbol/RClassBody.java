package one.parser.rule.symbol;

import one.ast.ASTNode;
import one.ast.symbol.NAnnotation;
import one.ast.symbol.NClass;
import one.ast.symbol.NClassBody;
import one.ast.symbol.NElement;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;

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

    @Override
    public NClassBody parseNode(ParseContext context) {
        final NClassBody body = new NClassBody();
        final List<NAnnotation> annotationBuf = new ArrayList<>();

        while (context.current() != null && context.currentType() != Tokens.RIGHT_BRACE) {
            // parse a new declaration
            ASTNode decl = context.tryParseNext("decl");

            // check for annotations
            if (decl instanceof NAnnotation annotation) {
                annotationBuf.add(annotation);
            } else if (decl instanceof NElement element) {
                // add accumulated annotations
                element.getAnnotations().addAll(annotationBuf);
                annotationBuf.clear();

                // TODO: register element to the class
                if (element instanceof NClass nClass) {
                    body.addDeclaredClass(nClass);
                }
            }
        }

        return body;
    }

}
