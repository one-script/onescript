package one.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Automatically sorts added items into their correct position
 * on insertion.
 */
public class SortedList<T> extends ArrayList<T> {

    /** The comparator to use for sorting. */
    private final Comparator<T> comparator;

    public SortedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    @Override
    public boolean add(T t) {
        return addSorted(t) != -1;
    }

    public int addSorted(T t) {
        // TODO: do a search for the index using
        //  a partitioning system with a pivot, more performant

        if (size() == 0) {
            add(0, t);
            return 0;
        }

        int rIndex = size() - 1;

        final int l = size();
        for (int i = 0; i < l; i++) {
            T val = get(i);
            int comp = comparator.compare(val, t);
            if (comp >= 1) {
                continue;
            }

            rIndex = i;
            break;
        }

        add(rIndex, t);
        sort(comparator);

        for (int i = 0; i < size(); i++) {
            if (get(i) instanceof ListRegistrable r) {
                r.registered(i);
            }
        }

        return rIndex;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
    }

    public int find(Predicate<T> predicate) {
        for (int i = 0; i < size(); i++)
            if (predicate.test(get(i)))
                return i;
        return -1;
    }

    public T with(T v) {
        add(v);
        return v;
    }

}
