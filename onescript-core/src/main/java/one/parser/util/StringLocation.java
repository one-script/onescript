package one.parser.util;

/**
 * Represents a location in a string.
 *
 * It contains the (virtual) file name, the string
 * and the start and end indices.
 */
public class StringLocation implements StringLocatable {

    String file;
    String string;

    /* Both the start and end indices are inclusive. */
    int startIndex;
    int endIndex;

    public StringLocation(String file, String string, int startIndex, int endIndex) {
        this.file = file;
        this.string = string;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getFile() {
        return file;
    }

    public String getString() {
        return string;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
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
