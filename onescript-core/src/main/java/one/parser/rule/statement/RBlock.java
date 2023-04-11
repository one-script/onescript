package one.parser.rule.statement;

import one.ast.statement.NBlock;
import one.ast.statement.NStatement;
import one.parser.ParseContext;
import one.parser.rule.ParserRule;
import one.parser.token.Tokens;

public class RBlock extends ParserRule<NBlock> {

    public RBlock() {
        super("block", "block", 0);
    }

    @Override
    public boolean canParse(ParseContext context) {
        return true;
    }

    @Override
    public NBlock parseNode(ParseContext context) {
        NBlock block = new NBlock();
        while (context.currentType() != Tokens.RIGHT_BRACE) {
            NStatement statement = context.tryParseNext("statement");
            block.addStatement(statement);

            while (context.currentType() == Tokens.SEMICOLON)
                context.next();
        }

        context.next();

        return block;
    }

}
