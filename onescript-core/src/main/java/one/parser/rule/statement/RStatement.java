package one.parser.rule.statement;

import one.ast.statement.NStatement;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;

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
