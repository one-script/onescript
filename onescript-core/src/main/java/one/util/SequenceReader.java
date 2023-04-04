package one.util;

import one.parser.util.StringLocatable;
import one.parser.util.StringLocation;
import one.parser.util.StringReader;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A class which sequentially reads.
 * data from a sequence.
 * @param <T> The type the sequence serves.
 */
@SuppressWarnings("rawtypes")
public class SequenceReader<T> {

    // the current index
    private int index = 0;
    // the string to read
    private Sequence<T> str;
    // the total string length
    private int len;

    protected Stack<Integer> startIndices = new Stack<>();

    /**
     * Constructor.
     * Creates a new string reader for the provided
     * string from the provided index.
     * @param str The string.
     * @param index The index.
     */
    public SequenceReader(Sequence<T> str, int index) {
        this.str   = str;
        this.len   = str.size();
        this.index = index;
    }

    /**
     * @see SequenceReader#SequenceReader(Sequence, int)
     * Parameter {@code index} is defaulted to 0.
     */
    public SequenceReader(Sequence<T> str) {
        this(str, 0);
    }

    /**
     * Begin a segment, pushing the start index
     * onto the indices stack.
     *
     * @return This.
     */
    public SequenceReader<T> begin() {
        startIndices.push(index());
        return this;
    }

    /**
     * End a segment, returning the starting index.
     */
    public int end() {
        return startIndices.pop();
    }

    /**
     * End the current segment if present and reset to
     * the segments start position. If absent it will
     * reset to index 0.
     *
     * @return This.
     */
    public SequenceReader<T> reset() {
        if (startIndices.size() != 0) {
            index(startIndices.pop());
        } else {
            index(0);
        }

        return this;
    }

    /**
     * Clamps an index in to the minimum (0) and
     * maximum (string length) index.
     * @param index The index.
     * @return The clamped index.
     */
    public int clamp(int index) {
        return Math.min(len - 1, Math.max(0, index));
    }

    /**
     * Get a data from the string by
     * index. Clamped.
     * @param i The index.
     * @return The data.
     */
    public T peekAt(int i) {
        return str.at(clamp(i));
    }

    /**
     * Get a data from the string relative
     * to the current index. Not clamped, rather
     * returns {@code null} if the
     * index is in an invalid position.
     * @param i The index.
     * @return The data or {@code null}
     */
    public T peek(int i) {
        int idx = index + i;
        if (idx < 0 || idx >= len)
            return null;
        return str.at(idx);
    }

    /**
     * Advances the position by 1 and
     * returns the data or {@code null}
     * if in an invalid position.
     * @return The data.
     */
    public T next() {
        if ((index += 1) >= len || index < 0) return null;
        return str.at(index);
    }

    /**
     * Advances the position by {@code a} and
     * returns the data or {@code null}
     * if in an invalid position.
     * @param a The amount to advance by.
     * @return The data.
     */
    public T next(int a) {
        if ((index += a) >= len || index < 0) return null;
        return str.at(index);
    }

    /**
     * Decreases the position by 1 and
     * returns the data or {@code null}
     * if in an invalid position.
     * @return The data.
     */
    public T prev() {
        if ((index -= 1) >= len || index < 0) return null;
        return str.at(index);
    }

    /**
     * Decreases the position by {@code a} and
     * returns the data or {@code null}
     * if in an invalid position.
     * @param a The amount to decrease by.
     * @return The data.
     */
    public T prev(int a) {
        if ((index -= a) >= len || index < 0) return null;
        return str.at(index);
    }

    /**
     * Returns the data at the current position
     * or {@code null} if in an invalid position.
     * @return The data.
     */
    public T current() {
        if (index < 0 || index >= len) return null;
        return str.at(index);
    }

    // predicate to always return true
    private final Predicate<T> ALWAYS = c -> true;

    /**
     * Collects until the end of the string.
     * @return The string.
     */
    public String collect() {
        return collect(ALWAYS, null);
    }

    public String collect(Predicate<T> pred, int offEnd) {
        String str = collect(pred);
        next(offEnd);
        return str;
    }

    public String collect(Predicate<T> pred) {
        return collect(pred, null);
    }

    public String collect(Predicate<T> pred, Predicate<T> skip, int offEnd) {
        String str = collect(pred, skip);
        next(offEnd);
        return str;
    }

    public String collect(Predicate<T> pred, Predicate<T> skip) {
        return collect(pred, skip, null);
    }

    public String collect(Predicate<T> pred, Predicate<T> skip, Consumer<T> TEval) {
        if (pred == null)
            pred = ALWAYS;
        StringBuilder b = new StringBuilder();
        prev();
        T c;
        while ((c = next()) != null && pred.test(c)) {
            if (skip == null || !skip.test(c)) {
                if (TEval != null)
                    TEval.accept(c);
                b.append(c);
            }
        }

        return b.toString();
    }

    public String pcollect(Predicate<T> pred) {
        return pcollect(pred, null);
    }

    public String pcollect(Predicate<T> pred, Predicate<T> skip) {
        if (pred == null)
            pred = ALWAYS;
        StringBuilder b = new StringBuilder();
        int off = 0;
        T c;
        while ((c = peek(off++)) != null && pred.test(c)) {
            if (skip == null || !skip.test(c)) {
                b.append(c);
            }
        }

        return b.toString();
    }

    public List<String> split(T... Ts) {
        List<String> list = new ArrayList<>(len / 10);
        HashSet<T> tSet = new HashSet<>(Arrays.asList(Ts));
        while (current() != null) {
            list.add(collect(c -> !tSet.contains(c)));
            next();
        }

        return list;
    }

    public int index() {
        return index;
    }

    public SequenceReader<T> index(int i) {
        this.index = i;
        return this;
    }

    public Sequence<T> getSequence() {
        return str;
    }

    public SequenceReader<T> subForward(int from, int len) {
        SequenceReader<T> reader = new SequenceReader<T>(str, from);
        reader.len = Math.min(from + len, this.len - from);
        return reader;
    }

    public SequenceReader<T> subFrom(int from, int len) {
        SequenceReader<T> reader = new SequenceReader<T>(str, index + from);
        reader.len = Math.min(from + len, this.len - from - index);
        return reader;
    }

    public SequenceReader<T> branch() {
        return new SequenceReader<>(str, index);
    }

}
