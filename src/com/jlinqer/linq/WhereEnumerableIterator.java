package com.jlinqer.linq;

import java.util.Iterator;
import java.util.function.Predicate;

/**
 * Created by Reuben Kuhnert
 * Modified by Keisuke Kato
 */
public class WhereEnumerableIterator<TSource> implements IEnumerable<TSource> {
// ------------------------------ FIELDS ------------------------------

    private final IEnumerable<TSource> iterable;
    private final Predicate<TSource> predicate;

// --------------------------- CONSTRUCTORS ---------------------------

    public WhereEnumerableIterator(IEnumerable<TSource> iterable, Predicate<TSource> predicate) {
        this.iterable = iterable;
        this.predicate = predicate;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    @Override
    public Iterator<TSource> iterator() {
        return new WhereIterator(iterable, predicate);
    }

// -------------------------- INNER CLASSES --------------------------

    public class WhereIterator implements Iterator<TSource> {
        private final Iterator<TSource> iterator;
        private final Predicate<TSource> predicate;
        private boolean hasUpdated = false;
        private TSource nextItem;

        private WhereIterator(Iterable<TSource> iterable, Predicate<TSource> predicate) {
            this.iterator = iterable.iterator();
            this.predicate = predicate;
        }

        @Override
        public boolean hasNext() {
            hasUpdated = true;
            while (this.iterator.hasNext()) {
                this.nextItem = iterator.next();
                if (predicate.test(nextItem)) return true;
            }

            nextItem = null;
            return false;
        }

        @Override
        public TSource next() {
            if (!hasUpdated) {
                this.hasNext();
            }

            hasUpdated = false;
            return nextItem;
        }
    }
}
