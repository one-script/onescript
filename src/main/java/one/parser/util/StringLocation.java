package one.parser.util;

import one.util.AnalyzedString;
import one.util.Strings;

import static one.util.ANSI.*;

/**
 * Represents a location in a string.
 *
 * It contains the (virtual) file name, the string
 * and the start and end indices.
 */
public class StringLocation implements StringLocatable {

    String file;
    AnalyzedString string;

    /* Both the start and end indices are inclusive. */
    int startIndex;
    int endIndex;

    /* The start and end line numbers. */
    int startLine;
    int endLine = -1;

    public StringLocation(String file, AnalyzedString str, int startIndex, int endIndex, int startLine) {
        this.file = file;
        this.string = str;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startLine = startLine;
    }

    public StringLocation(String file, AnalyzedString str, int startIndex, int endIndex, int startLine, int endLine) {
        this.file = file;
        this.string = str;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.startLine = startLine;
        this.endLine = endLine;
    }

    public String getFile() {
        return file;
    }

    public AnalyzedString getString() {
        return string;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getStartLineNumber() {
        return startLine;
    }

    public int getEndLineNumber() {
        if (endLine == -1) {
            // calculate
            endLine = startLine + Strings.countLines(string.getString(), startIndex, endIndex);
        }

        return endLine;
    }

    public int getStartColumn() {
        return startIndex - string.getLine(startLine).startIndex();
    }

    public int getEndColumn() {
        return endIndex - string.getLine(getEndLineNumber()).startIndex();
    }

    @Override
    public String toString() {
        return "StringLocation(" +
                "file='" + file + '\'' +
                ", string='" + string + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ')';
    }

    // creates an ansi code from the given
    // format and then returns it escaped if the
    // boolean is true
    private static String ac(String c, boolean fmt) {
        if (!fmt)
            return "";
        return "\u001B[" + c + "m";
    }

    public String toStringFancy(boolean fmt, int indent) {
        final String str = string.getString();
        final int surround = 10;

        final String indentStr = " ".repeat(indent);
        StringBuilder b = new StringBuilder(ac("31", fmt) + indentStr);

        AnalyzedString.Line startLine = string.getLine(getStartLineNumber());
        AnalyzedString.Line endLine   = string.getLine(getEndLineNumber());

        int startLineIdx = startLine.getColumn(startIndex);
        int endLineIdx   = endLine.getColumn(endIndex);

        // append info
        if (file != null)
            b.append(ac("1", fmt)).append("in file ").append(ac("0", fmt)).append(file).append(", ");
        b.append(startLine.number()).append(":").append(startLineIdx);
        b.append(ac("1", fmt)).append(ac("31", fmt)).append(" to ").append(ac("0", fmt));
        b.append(endLine.number()).append(":").append(endLineIdx).append(ac("0", fmt)).append("\n");

        // append lines
        if (endLine == startLine) {
            b.append(indentStr);
            b.append(ac("90", fmt)).append(ac("1", fmt))
                    .append(startLine.number()).append(" | ").append(ac("0", fmt));

            String lStr = startLine.line();

            int sIdx = startLineIdx - surround;
            b.append(ac("90", fmt));
            b.append(sIdx <= 0 ? "" : "... ");
            b.append(lStr, Math.max(0, sIdx), startLineIdx);

            b.append(ac("31", fmt)).append(ac("4", fmt));
            b.append(lStr, startLineIdx, endLineIdx + 1);
            b.append(ac("0", fmt)).append(ac("90", fmt));

            int eIdx = endLineIdx + surround;
            b.append(ac("90", fmt));
            b.append(lStr, endLineIdx + 1, Math.min(lStr.length() - 1, eIdx));
            b.append(eIdx >= lStr.length() ? "" : " ...");
        } else {
            // todo: cant be fucking bothered fuck this shit
        }

        return b.toString();
    }

    @Override
    public StringLocation getLocation() {
        return this;
    }

    @Override
    public StringLocation setLocation(StringLocation location) {
        this.startIndex = location.startIndex;
        this.endIndex = location.endIndex;
        this.file = location.file;
        this.string = location.string;
        return this;
    }

}
