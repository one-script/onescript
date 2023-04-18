package one.parser.token;

import one.parser.LexContext;

public class Keyword extends TokenType<Void> {

    public Keyword(String name) {
        super(name);
    }

    @Override
    public Token<Void> parseToken(LexContext context) {
        context.begin();
        if (!context.consumeFullString(name)) {
            context.reset();
            return null;
        }

        return new Token<>(this);
    }

}
