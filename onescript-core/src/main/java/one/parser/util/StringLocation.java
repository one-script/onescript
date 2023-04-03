package one.parser.util;

/**
 * Represents a location in a string.
 *
 * It contains the (virtual) file name, the string
 * and the start and end indices.
 */
public class StringLocation {

    final String file;
    final String string;

    /* Both the start and end indices are inclusive. */
    final int startIndex;
    final int endIndex;

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

}
