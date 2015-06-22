package com.jlinqer.linq;

import java.util.Iterator;
import java.util.function.Function;

/**
 * Created by sircodesalot
 * Modified by Keisuke Kato
 */
public class MapEnumerableIterator<TSource, TResult> implements IEnumerable<TResult> {
  private final Function<TSource, TResult> projection;
  private final Iterable<TSource> iterable;

  public MapEnumerableIterator(Iterable<TSource> iterable, Function<TSource, TResult> projection) {
    this.iterable = iterable;
    this.projection = projection;
  }

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
