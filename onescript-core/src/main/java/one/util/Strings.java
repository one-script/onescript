package one.util;

import java.util.List;

public class Strings {

    /**
     * Join the given list of objects together into
     * a string with the given delimiter.
     *
     * @param list The objects.
     * @param delimiter The delimiter.
     * @return The joined string.
     */
    public static <T> String join(List<T> list, String delimiter) {
        StringBuilder b = new StringBuilder();
        final int l = list.size();
        for (int i = 0; i < l; i++) {
            Object o = list.get(i);
            if (i != 0) {
                b.append(delimiter);
            }

            b.append(o);
        }

        return b.toString();
    }

    /**
     * Count the amount of newlines in the given string
     * from index {@code start} to including {@code end}.
     *
     * @param str The string.
     * @param start The start index.
     * @param end The end index.
     * @return The amount of newlines in that segment.
     */
    public static int countLines(String str, int start, int end) {
        if (end == -1)
            end = str.length() - 1;
        final int end2 = Math.min(str.length() - 1, end);
        int count = 0;
        int i = start;
        for (; i <= end2; i++) {
            char c = str.charAt(i);
            if (c == '\n')
                count++;
        }

        if (i == str.length()) {
            count++;
        }

        return count;
    }

}
