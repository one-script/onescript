package one.parser.util;

import java.util.regex.MatchResult;

/**
 * A function which parses a value from the
 * given match result.
 *
 * @param <T> The value type.
 */
@FunctionalInterface
public interface RegexValueFactory<T> {

    T parse(MatchResult result);

}
