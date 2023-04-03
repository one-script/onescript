package one.parser.token;

import one.parser.OneParser;
import one.parser.util.StringLocation;

/**
 * Represents a token as a result of lexical
 * analysis.
 *
 * A token has a type and optionally extra data
 * in the form of a contained value, which is of
 * type {@code T}.
 *
 * @param <T> The value type.
 */
public class Token<T> {

    TokenType<T> type;
    T value;
    boolean hasValue;

    /**
     * The location in the analyzed string which
     * this token was found at.
     */
    StringLocation location;

    public Token(TokenType<T> type, T value) {
        this.type  = type;
        this.value = value;
        this.hasValue = true;
    }

    /**
     * Valueless constructor (value is set to null).
     * @see #Token(TokenType, Object)
     */
    public Token(TokenType<T> type) {
        this(type, null);
        this.hasValue = false;
    }

    public TokenType<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    public Token<T> setType(TokenType<T> type) {
        this.type = type;
        return this;
    }

    public Token<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public StringLocation getLocation() {
        return location;
    }

    public Token<T> setLocation(StringLocation location) {
        this.location = location;
        return this;
    }

    /**
     * Creates a basic string including only
     * the type and value.
     *
     * @return The string.
     */
    public String toBasicString() {
        return type.getName() + "(" + (hasValue ? value : "") + ")";
    }

    @Override
    public String toString() {
        return toBasicString();
    }

}
