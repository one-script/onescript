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

}
