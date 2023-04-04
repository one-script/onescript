package one.parser.token;

import one.parser.LexContext;

public class KeywordTokenType extends TokenType<Void> {

    public KeywordTokenType(String name) {
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
