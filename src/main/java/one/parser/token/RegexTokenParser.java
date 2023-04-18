package one.parser.token;

import one.parser.LexContext;
import one.parser.util.StringLocation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A token parser which uses regex to find tokens.
 */
public class RegexTokenParser<T> implements TokenParser<T> {

    public static final int DEFAULT_PATTERN_FLAGS = 0;

    //////////////////////////////////////

    final Pattern pattern;
    final RegexTokenFactory<T> tokenFactory;

    public RegexTokenParser(Pattern pattern, RegexTokenFactory<T> factory) {
        this.pattern = pattern;
        this.tokenFactory = factory;
    }

    public RegexTokenParser(String pattern, RegexTokenFactory<T> factory) {
        this(Pattern.compile(pattern, DEFAULT_PATTERN_FLAGS), factory);
    }

    @Override
    public Token<T> parseToken(LexContext context) {
        // match the regex against the
        // string reader
        Matcher matcher = pattern.matcher(context.str());
        if (!matcher.find(context.index())) {
            return null;
        }

        // create a token from the
        // match result
        int startLine = context.currentLine();
        int startIndex = context.index();
        int endIndex = matcher.end();
        Token<T> token = tokenFactory.create(matcher.toMatchResult());
        token.setLocation(new StringLocation(context.getFile(), context.aStr(), startIndex, endIndex, startLine));

        // skip the matched string
        context.index(endIndex + 1);

        return token;
    }

}
