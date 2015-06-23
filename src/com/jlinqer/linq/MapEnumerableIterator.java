package com.jlinqer.linq;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Created by Reuben Kuhnert
 * Modified by Keisuke Kato
 */
class MapEnumerableIterator<TSource, TResult> implements IEnumerable<TResult> {
// ------------------------------ FIELDS ------------------------------

    private final Function<TSource, TResult> projection;
    private final Iterable<TSource> iterable;

// --------------------------- CONSTRUCTORS ---------------------------

    public MapEnumerableIterator(Iterable<TSource> iterable, Function<TSource, TResult> projection) {
        this.iterable = iterable;
        this.projection = projection;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    @Override
    public Iterator<TResult> iterator() {
        final Iterator<TSource> iterator = this.iterable.iterator();

        // Return a projection iterator.
        return new Iterator<TResult>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public TResult next() {
                return projection.apply(iterator.next());
            }
        };
    }
}
