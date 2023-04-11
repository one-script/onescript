package one.util;

import one.parser.util.StringReader;

import java.util.ArrayList;
import java.util.List;

/**
 * String wrapper which stores data about the string.
 */
public class AnalyzedString {

    public record Line(int number, int startIndex, int endIndex, String line) {
        public int getColumn(int index) {
            return index - startIndex;
        }
    }

    /** The utility string reader. */
    private final StringReader reader;

    /** The string. */
    private final String string;

    public AnalyzedString(String string) {
        this.reader = new StringReader(string);
        this.string = string;
    }

    /*
        String
     */

    public String getString() {
        return string;
    }

    public int length() {
        return string.length();
    }

    public char chatAt(int i) {
        return string.charAt(i);
    }

    @Override
    public String toString() {
        return string;
    }

    /*
        Lines
     */

    private int totalLineCount = -1;
    private List<Line> lines = new ArrayList<>();

    public int getTotalLineCount() {
        if (totalLineCount == -1)
            totalLineCount = Strings.countLines(string, 0, -1);
        return totalLineCount;
    }

    public Line getLine(int lineNumber) {
        reader.index(lines.isEmpty() ? 0 : lines.get(lines.size() - 1).endIndex + 1);
        for (int n = lines.size(); n <= lineNumber; n++) {
            int start = reader.index();
            String line = reader.collect(c -> c != '\n');
            int end = reader.index() - 1;
            reader.next();
            lines.add(new Line(n, start, end, line));
        }

        return lines.get(lineNumber);
    }

    public List<Line> getAllLines() {
        reader.index(lines.isEmpty() ? 0 : lines.get(lines.size() - 1).endIndex + 1);
        for (int n = lines.size();; n++) {
            int start = reader.index();
            String line = reader.collect(c -> c != '\n');
            int end = reader.index() - 1;
            reader.next();
            lines.add(new Line(n, start, end, line));

            if (reader.current() == StringReader.EOF)
                break;
        }

        return lines;
    }

}
