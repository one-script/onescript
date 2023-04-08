package one.parser.rule;

import one.ast.NStatement;
import one.parser.ParseContext;

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
        return null;
    }

}
