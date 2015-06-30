package com.github.jlinqer.linq;

import java.util.*;
import java.util.function.Function;

/**
 * Created by Keisuke Kato
 */
class OrderedEnumerableIterator<TElement, TKey> implements IEnumerable<TElement> {
// ------------------------------ FIELDS ------------------------------

    final IEnumerable<TElement> source;
    OrderedEnumerableIterator<TElement, TKey> parent;
    final Function<TElement, TKey> keySelector;
    final Comparator<TKey> comparer;
    final boolean descending;

// --------------------------- CONSTRUCTORS ---------------------------

    OrderedEnumerableIterator(IEnumerable<TElement> source, Function<TElement, TKey> keySelector, Comparator<TKey> comparer, boolean descending) {
        this.source = source;
        this.parent = null;
        this.keySelector = keySelector;
        this.comparer = comparer;
        this.descending = descending;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    @Override
    public Iterator<TElement> iterator() {
        java.util.Queue<TElement> queue = new ArrayDeque<>();

        Buffer<TElement> buffer = new Buffer<>(source);
        if (buffer.count > 0) {
            IEnumerableSorter<TElement> sorter = getEnumerableSorter(null);
            int[] map = sorter.sort(buffer.items, buffer.count);

            for (int i = 0; i < buffer.count; i++) {
                queue.add(buffer.items.get(map[i]));
            }

        }
        return new Iterator<TElement>() {
            @Override
            public boolean hasNext() {
                return queue.size() != 0;
            }

            @Override
            public TElement next() {
                return queue.remove();
            }
        };
    }

// -------------------------- OTHER METHODS --------------------------

    public OrderedEnumerableIterator createOrderedEnumerable(Function keySelector, Comparator comparer, boolean descending) {
        OrderedEnumerableIterator result = new OrderedEnumerableIterator<>(source, keySelector, comparer, descending);
        result.parent = this;
        return result;
    }

    public IEnumerableSorter getEnumerableSorter(IEnumerableSorter next) {
        IEnumerableSorter<TElement> sorter = new EnumerableSorter<>(keySelector, comparer, descending, next);
        if (parent != null) sorter = parent.getEnumerableSorter(sorter);
        return sorter;
    }

// -------------------------- INNER CLASSES --------------------------

    abstract class IEnumerableSorter<TElement> {
        int[] sort(List<TElement> elements, int count) {
            computeKeys(elements, count);
            int[] map = new int[count];
            for (int i = 0; i < count; i++) map[i] = i;
            quickSort(map, 0, count - 1);
            return map;
        }

        abstract void computeKeys(List<TElement> elements, int count);

        void quickSort(int[] map, int left, int right) {
            do {
                int i = left;
                int j = right;
                int x = map[i + ((j - i) >> 1)];
                do {
                    while (i < map.length && compareKeys(x, map[i]) > 0) i++;
                    while (j >= 0 && compareKeys(x, map[j]) < 0) j--;
                    if (i > j) break;
                    if (i < j) {
                        int temp = map[i];
                        map[i] = map[j];
                        map[j] = temp;
                    }
                    i++;
                    j--;
                } while (i <= j);
                if (j - left <= right - i) {
                    if (left < j) quickSort(map, left, j);
                    left = i;
                } else {
                    if (i < right) quickSort(map, i, right);
                    right = j;
                }
            } while (left < right);
        }

        abstract int compareKeys(int index1, int index2);
    }

    class EnumerableSorter<TElement, TKey> extends IEnumerableSorter<TElement> {
        final Function<TElement, TKey> keySelector;
        final Comparator<TKey> comparer;
        final boolean descending;
        final IEnumerableSorter<TElement> next;
        java.util.List<TKey> keys;
        EnumerableSorter(Function<TElement, TKey> keySelector, Comparator<TKey> comparer, boolean descending, IEnumerableSorter<TElement> next) {
            this.keySelector = keySelector;
            this.comparer = comparer;
            this.descending = descending;
            this.next = next;
        }

        int compareKeys(int index1, int index2) {
            int c = comparer.compare(keys.get(index1), keys.get(index2));
            if (c == 0) {
                if (next == null) return index1 - index2;
                return next.compareKeys(index1, index2);
            }
            return descending ? -c : c;
        }

        @Override
        void computeKeys(List<TElement> elements, int count) {
            keys = new ArrayList<>();
            for (int i = 0; i < count; i++) keys.add(keySelector.apply(elements.get(i)));
            if (next != null) next.computeKeys(elements, count);
        }
    }

    class Buffer<TElement> {
        final List<TElement> items;
        final int count;
        Buffer(IEnumerable<TElement> source) {
            List<TElement> items = null;
            int count = 0;
            if (source != null) {
                count = source.count();
                if (count > 0) {
                    items = new ArrayList<>(count);
                    items.addAll(source.toList());
                }
            } else {
                for (TElement item : source) {
                    if (items == null) {
                        items = new ArrayList<>(4);
                    } else if (items.size() == count) {
                        List<TElement> newItems = new ArrayList<>(count * 2);
                        items.addAll(newItems);
                    }
                    items.set(count, item);
                    count++;
                }
            }
            this.items = items;
            this.count = count;
        }
    }
}