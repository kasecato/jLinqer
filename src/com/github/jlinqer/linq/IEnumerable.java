package com.github.jlinqer.linq;

import com.github.jlinqer.collections.Dictionary;
import com.github.jlinqer.collections.List;
import com.github.jlinqer.collections.Set;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.*;

/**
 * Created by Reuben Kuhnert
 * Modified by Keisuke Kato
 */
public interface IEnumerable<TSource> extends Iterable<TSource> {
// -------------------------- STATIC METHODS --------------------------

    /**
     * ﻿Generates a sequence of integral numbers within a specified range.
     *
     * @param start ﻿The value of the first integer in the sequence.
     * @param count ﻿The number of sequential integers to generate.
     * @return ﻿A List&lt;Integer&gt; that ﻿contains a range of sequential integral numbers.
     * @throws IndexOutOfBoundsException ﻿count is less than 0.-or-start + count -1 is larger than Integer.MaxValue.
     */
    static List<Integer> range(final int start, final int count) throws IndexOutOfBoundsException {
        if (count < 0) throw new IndexOutOfBoundsException("count is less than 0.");
        if (Integer.MAX_VALUE < (long) start + (long) count - 1)
            throw new IndexOutOfBoundsException("start + count -1 is larger than Integer.MaxValue.");

        List<Integer> list = new List<>();
        for (int i = 0; i < count; i++) {
            list.add(start + i);
        }

        return list;
    }

    /**
     * ﻿Generates a sequence that contains one repeated value.
     *
     * @param type      ﻿The type of the value to be repeated in the result sequence.
     * @param element   ﻿The value to be repeated.
     * @param count     ﻿The number of times to repeat the value in the generated sequence.
     * @param <TResult> ﻿The type of the value to be repeated in the result sequence.
     * @return ﻿A List&lt;TResult&gt; that contains a repeated value.
     * @throws IllegalArgumentException  ﻿type is null.
     * @throws IndexOutOfBoundsException ﻿count is less than 0.
     */
    static <TResult> List<TResult> repeat(final Class<TResult> type, final TResult element, final int count) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (type == null) throw new IllegalArgumentException("type is null.");
        if (count < 0) throw new IndexOutOfBoundsException("count is less than 0.");

        List<TResult> list = new List<>();
        for (int i = 0; i < count; i++) {
            list.add(element);
        }

        return list;
    }

    /**
     * ﻿Returns an empty List&lt;T&gt; that has the specified ﻿type argument.
     *
     * @param type      The type to assign to the type parameter of the returned generic List&lt;T&gt;.
     * @param <TResult> ﻿The type to assign to the type parameter of the returned generic List&lt;T&gt;.
     * @return ﻿An empty List&lt;T&gt; whose type argument is ﻿TResult.
     * @throws IllegalArgumentException type is null.
     */
    static <TResult> List<TResult> empty(final Class<TResult> type) {
        if (type == null) throw new IllegalArgumentException("type is null.");

        return new List<>();
    }

// -------------------------- OTHER METHODS --------------------------

    /**
     * ﻿Applies an accumulator function over a sequence.
     *
     * @param accumulator ﻿An accumulator function to be invoked on each element.
     * @return ﻿The final accumulator value.
     * @throws IllegalArgumentException      accumulator is null.
     * @throws UnsupportedOperationException source contains no elements.
     */
    default TSource aggregate(final BinaryOperator<TSource> accumulator) throws IllegalArgumentException, UnsupportedOperationException {
        if (accumulator == null) throw new IllegalArgumentException("accumulator is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        Iterator<TSource> iterator = this.iterator();
        TSource result = iterator.next();
        while (iterator.hasNext()) {
            TSource elem = iterator.next();
            result = accumulator.apply(result, elem);
        }

        return result;
    }

    /**
     * ﻿Returns the number of elements in a sequence.
     *
     * @return ﻿﻿The number of elements in the input sequence.
     * @throws ArithmeticException ﻿The number of elements in source is larger than Integer.MaxValue.
     */
    default int count() throws ArithmeticException {
        int count = 0;
        for (TSource item : this) {
            count = Math.addExact(count, 1);
        }
        return count;
    }

    /**
     * ﻿Determines whether all elements of a sequence satisfy a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿true if every element of the source sequence passes the test in the specified
     * ﻿predicate, or if the sequence is empty    { return null;} otherwise, false.
     * @throws IllegalArgumentException predicate is null.
     */
    default boolean all(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return !this.where(x -> !predicate.test(x)).any();
    }

    /**
     * ﻿Determines whether a sequence contains any elements.
     *
     * @return ﻿true if the source sequence contains any elements    { return null;} otherwise, false.
     */
    default boolean any() {
        return this.iterator().hasNext();
    }

    /**
     * ﻿Determines whether any element of a sequence satisfies a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿true if any elements in the source sequence pass the test in the specified
     * ﻿predicate    { return null;} otherwise, false.
     * @throws IllegalArgumentException predicate is null.
     */
    default boolean any(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return this.where(predicate).any();
    }

    /**
     * ﻿﻿Computes the average of a sequence of Decimal values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    default BigDecimal averageBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        BigDecimal sum = BigDecimal.ZERO;
        long count = 0;

        for (TSource item : this) {
            sum = sum.add(selector.apply(item));
            count++;
        }

        return sum.divide(new BigDecimal(count));
    }

    /**
     * ﻿﻿﻿Computes the average of a sequence of Double values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    default double averageDouble(final Function<TSource, Double> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        double sum = 0;
        long count = 0;

        for (TSource item : this) {
            sum += selector.apply(item);
            count++;
        }

        return sum / (double) count;
    }

    /**
     * ﻿﻿Computes the average of a sequence of Integer values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     * @throws ArithmeticException           ﻿The sum of the elements in the sequence is larger than Integer.MaxValue.
     */
    default double averageInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, UnsupportedOperationException, ArithmeticException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        int sum = 0;
        long count = 0;

        for (TSource item : this) {
            sum = Math.addExact(sum, selector.apply(item));
            count++;
        }

        return sum / (double) count;
    }

    /**
     * ﻿﻿Computes the average of a sequence of Long values that are obtained
     * ﻿﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     * @throws ArithmeticException           ﻿The sum of the elements in the sequence is larger than Long.MaxValue.
     */
    default double averageLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException, ArithmeticException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        long sum = 0;
        long count = 0;

        for (TSource item : this) {
            sum = Math.addExact(sum, selector.apply(item));
            count++;
        }

        return sum / (double) count;
    }

    /**
     * ﻿Concatenates two sequences.
     *
     * @param second ﻿The sequence to concatenate to the first sequence.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains the concatenated ﻿elements of the two input sequences.
     * @throws IllegalArgumentException ﻿﻿second is null.
     */
    default IEnumerable<TSource> concat(final IEnumerable<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return () -> {
            List<TSource> allItems = new List<>();
            for (TSource item : this) allItems.add(item);
            for (TSource item : second) allItems.add(item);

            return allItems.iterator();
        };
    }

    /**
     * ﻿Returns the elements of the specified sequence or the type parameter's default
     * ﻿value in a singleton collection if the sequence is empty.
     *
     * @return ﻿An IEnumerable&lt;TSource&gt; object that contains the default
     * ﻿value for the TSource type if source is empty    { return null;} otherwise, source.
     */
    default IEnumerable<TSource> defaultIfEmpty() {
        if (this.count() == 0) {
            List<TSource> list = new List<>();
            list.add(null);
            return list;
        }

        return this;
    }

    /**
     * ﻿Returns the elements of the specified sequence or the specified value in
     * ﻿a singleton collection if the sequence is empty.
     *
     * @param defaultValue ﻿The value to return if the sequence is empty.
     * @return ﻿An System.Collections.Generic.IEnumerable&lt;TSource&gt; that contains defaultValue if
     * ﻿source is empty    { return null;} otherwise, source.
     */
    default IEnumerable<TSource> defaultIfEmpty(final TSource defaultValue) {
        return (this.count() == 0) ? new List<>(defaultValue) : this;
    }

    /**
     * ﻿Returns distinct elements from a sequence by using the default equality ﻿comparer
     * ﻿to compare values.
     *
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains distinct elements ﻿from the source sequence.
     */
    default IEnumerable<TSource> distinct() {
        IEnumerable<TSource> self = this;
        return () -> {
            Set<TSource> seenItems = new Set<>();
            return self.where(seenItems::add).iterator();
        };
    }

    /**
     * ﻿Returns the element at a specified index in a sequence or a default value
     * ﻿if the index is out of range.
     *
     * @param index ﻿The zero-based index of the element to retrieve.
     * @return ﻿default(TSource) if the index is outside the bounds of the source sequence    { return null;}
     * ﻿otherwise, the element at the specified position in the source sequence.
     */
    default TSource elementAtOrDefault(final int index) {
        return (index < 0 || this.count() < index + 1)
                ? null
                : this.elementAt(index);
    }

    /**
     * ﻿Returns the element at a specified index in a sequence.
     *
     * @param index ﻿The zero-based index of the element to retrieve.
     * @return ﻿The element at the specified position in the source sequence.
     * @throws IndexOutOfBoundsException ﻿index is less than 0 or greater than or equal to the number of elements in
     *                                   ﻿source.
     */
    default TSource elementAt(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || this.count() < index + 1)
            throw new IndexOutOfBoundsException("index is less than 0 or greater than or equal to the number of elements in source.");

        if (this.count() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        Iterator<TSource> iterator = this.iterator();
        if (!iterator.hasNext())
            throw new UnsupportedOperationException("The source sequence is empty.");

        TSource returnValue = null;
        for (int i = 0; i <= index; i++) {
            returnValue = iterator.next();
        }

        return returnValue;
    }

    /**
     * ﻿Produces the set difference of two sequences by using the default equality
     * ﻿comparer to compare values.
     *
     * @param second ﻿An IEnumerable&lt;TSource&gt; whose elements that also occur ﻿in the first sequence will cause those elements
     *               to be removed from the returned ﻿sequence.
     * @return ﻿A sequence that contains the set difference of the elements of two sequences.
     * @throws IllegalArgumentException second is null.
     */
    default IEnumerable<TSource> except(final Iterable<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return () -> {
            Set<TSource> exceptItems;
            if (second instanceof Set)
                exceptItems = (Set<TSource>) second;
            else {
                exceptItems = new Set<>();
                for (TSource item : second) exceptItems.add(item);
            }

            List<TSource> subSet = new List<>();
            for (TSource item : this) {
                if (!exceptItems.contains(item)) {
                    subSet.add(item);
                }
            }

            return subSet.iterator();
        };
    }

    /**
     * ﻿Returns the first element of a sequence.
     *
     * @return ﻿The first element in the specified sequence.
     * @throws UnsupportedOperationException The source sequence is empty.
     */
    default TSource first() throws UnsupportedOperationException {
        if (this.count() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        final Iterator<TSource> iterator = this.iterator();
        if (iterator.hasNext()) return iterator.next();

        throw new UnsupportedOperationException("The source sequence is empty.");
    }

    /**
     * ﻿Returns the first element in a sequence that satisfies a specified condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿The first element in the sequence that passes the test in the specified predicate ﻿function.
     * @throws IllegalArgumentException      ﻿﻿predicate is null.
     * @throws UnsupportedOperationException ﻿﻿No element satisfies the condition in predicate.-or-The source sequence is empty.
     */
    default TSource first(final Predicate<TSource> predicate) throws UnsupportedOperationException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        final Iterator<TSource> iterator = this.where(predicate).iterator();
        if (iterator.hasNext()) return iterator.next();

        throw new UnsupportedOperationException("No element satisfies the condition in predicate.-or-The source sequence is empty.");
    }

    /**
     * ﻿Returns the first element of a sequence, or a default value if the sequence
     * ﻿of a sequence, or a default value if the sequence
     *
     * @return ﻿﻿﻿default(TSource) if source is empty    { return null;} otherwise, the first element in source.
     */
    default TSource firstOrDefault() {
        final Iterator<TSource> iterator = this.iterator();
        if (iterator.hasNext()) return iterator.next();

        return null;
    }

    /**
     * ﻿Returns the first element of the sequence that satisfies a condition or a
     * ﻿default value if no such element is found.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿default(TSource) if source is empty or if no element passes the test specified
     * ﻿by predicate    { return null;} otherwise, the first element in source that passes the test
     * ﻿specified by predicate.
     * @throws IllegalArgumentException ﻿predicate is null.
     */
    default TSource firstOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        final Iterator<TSource> iterator = this.where(predicate).iterator();
        if (iterator.hasNext()) return iterator.next();

        return null;
    }

    /**
     * ﻿Groups the elements of a sequence according to a specified key selector function.
     *
     * @param keySelector ﻿A function to extract the key for each element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An Map&lt;TKey, IEnumerable&lt;TSource&gt;&gt; ﻿object contains a sequence of objects and a key.
     * @throws IllegalArgumentException ﻿keySelector is null.
     */
    default <TKey> Map<TKey, IEnumerable<TSource>> groupBy(final Function<TSource, TKey> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        IEnumerable<TKey> keys = this.select(keySelector);
        Set<TKey> uniqueKeys = new Set<>();
        for (TKey key : keys) {
            uniqueKeys.add(key);
        }

        Map<TKey, IEnumerable<TSource>> result = new HashMap<>();

        for (TKey uniqueKey : uniqueKeys) {
            List<TSource> value = new List<>();
            for (TSource item : this) {
                if (keySelector.apply(item).equals(uniqueKey)) {
                    value.add(item);
                }
            }
            result.put(uniqueKey, value);
        }

        return result;
    }

    /**
     * ﻿Projects each element of a sequence into a new form.
     *
     * @param selector  ﻿A transform function to apply to each element.
     * @param <TResult> ﻿The type of the value returned by selector.
     * @return ﻿An IEnumerable&lt;TResult&gt; whose elements are the result
     * ﻿of invoking the transform function on each element of source.
     * @throws IllegalArgumentException ﻿selector is null.
     */
    default <TResult> IEnumerable<TResult> select(final Function<TSource, TResult> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return () -> {
            java.util.Queue<TResult> queue = new ArrayDeque<>();

            for (TSource item : this) {
                queue.add(selector.apply(item));
            }

            return new Iterator<TResult>() {
                @Override
                public boolean hasNext() {
                    return queue.size() != 0;
                }

                @Override
                public TResult next() {
                    return queue.remove();
                }
            };
        };
    }

    /**
     * ﻿Produces the set intersection of two sequences by using the default equality
     * ﻿comparer to compare values.
     *
     * @param second ﻿An IEnumerable&lt;TSource&gt; whose distinct elements that ﻿also appear in the first sequence will be returned.
     * @return ﻿A sequence that contains the elements that form the set intersection of two ﻿sequences.
     * @throws IllegalArgumentException second is null.
     */
    default IEnumerable<TSource> intersect(final IEnumerable<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return () -> {
            Set<TSource> first;
            if (second instanceof Set) first = (Set<TSource>) second;
            else {
                first = new Set<>();
                for (TSource item : second) {
                    first.add(item);
                }
            }

            return first.iterator();
        };
    }

    /**
     * ﻿﻿Returns the last element of a sequence.
     *
     * @return ﻿The value at the last position in the source sequence.
     * @throws UnsupportedOperationException ﻿The source sequence is empty.
     */
    default TSource last() throws UnsupportedOperationException {
        if (this.count() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        final Iterator<TSource> iterator = this.iterator();
        if (!iterator.hasNext())
            throw new UnsupportedOperationException("The source sequence is empty.");

        TSource returnValue = null;
        while (iterator.hasNext()) {
            returnValue = iterator.next();
        }

        return returnValue;
    }

    /**
     * ﻿﻿﻿Returns the last element of a sequence that satisfies a specified condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿﻿The last element in the sequence that passes the test in the specified predicate ﻿function.
     * @throws IllegalArgumentException      predicate is null.
     * @throws UnsupportedOperationException ﻿﻿No element satisfies the condition in predicate.-or-The source sequence is ﻿empty.
     */
    default TSource last(final Predicate<TSource> predicate) throws IllegalArgumentException, UnsupportedOperationException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        final long count = this.longCount(predicate);
        if (count == 0)
            throw new UnsupportedOperationException("The source sequence is empty.");

        final Iterator<TSource> iterator = this.where(predicate).iterator();
        TSource returnValue = null;
        while (iterator.hasNext()) {
            returnValue = iterator.next();
        }

        return returnValue;
    }

    /**
     * ﻿Returns a number that represents how many elements in the specified sequence
     * ﻿satisfy a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿A number that represents how many elements in the sequence satisfy the condition
     * ﻿in the predicate function.
     * @throws IllegalArgumentException predicate is null.
     * @throws ArithmeticException      ﻿The number of elements exceeds Long.MaxValue.
     */
    default long longCount(final Predicate<TSource> predicate) throws IllegalArgumentException, ArithmeticException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return where(predicate).longCount();
    }

    /**
     * ﻿﻿﻿Returns the last element of a sequence, or a default value if the sequence
     * ﻿contains no elements.
     *
     * @return ﻿﻿default(TSource) if the source sequence is empty    { return null;} otherwise, the last element
     * ﻿in the IEnumerable&lt;TSource&gt;.
     */
    default TSource lastOrDefault() {
        if (this.count() == 0) return null;

        final Iterator<TSource> iterator = this.iterator();
        if (!iterator.hasNext())
            return null;

        TSource returnValue = null;
        while (iterator.hasNext()) {
            returnValue = iterator.next();
        }

        return returnValue;
    }

    /**
     * ﻿Returns the last element of a sequence that satisfies a condition or a default
     * ﻿value if no such element is found.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿default(TSource) if the sequence is empty or if no elements pass the test
     * ﻿in the predicate function    { return null;} otherwise, the last element that passes the test
     * ﻿in the predicate function.
     * throws IllegalArgumentException predicate is null.
     */
    default TSource lastOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        if (this.count() == 0) return null;

        final long count = this.longCount(predicate);
        if (count == 0)
            return null;

        final Iterator<TSource> iterator = this.where(predicate).iterator();
        TSource returnValue = null;
        while (iterator.hasNext()) {
            returnValue = iterator.next();
        }

        return returnValue;
    }

    /**
     * ﻿Returns the number of elements in a sequence.
     *
     * @return ﻿﻿The number of elements in the input sequence.
     * @throws ArithmeticException ﻿The number of elements exceeds Long.MaxValue.
     */
    default long longCount() throws ArithmeticException {
        long count = 0;
        for (TSource item : this) {
            count = Math.addExact(count, 1);
        }
        return count;
    }

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * ﻿maximum TSource value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @param <TKey>   ﻿The type of the elements of source.
     * @return ﻿The maximum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    default <TKey extends Comparable> TSource max(final Function<TSource, TKey> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        TSource maxItem = null;

        for (TSource item : this) {
            if (maxItem == null) {
                maxItem = item;
                continue;
            }

            final TKey second = selector.apply(item);
            final TKey first = selector.apply(maxItem);

            if (second.compareTo(first) > 0) {
                maxItem = item;
            }
        }

        return maxItem;
    }

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * minimum TSource value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @param <TKey>   ﻿The type of the elements of source.
     * @return ﻿The minimum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    default <TKey extends Comparable> TSource min(final Function<TSource, TKey> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        TSource minItem = null;

        for (TSource item : this) {
            if (minItem == null) {
                minItem = item;
                continue;
            }

            final TKey second = selector.apply(item);
            final TKey first = selector.apply(minItem);

            if (second.compareTo(first) < 0) {
                minItem = item;
            }
        }

        return minItem;
    }

    /**
     * ﻿Filters the elements of an IEnumerable based on a specified ﻿type.
     *
     * @param type      ﻿The type to filter the elements of the sequence on.
     * @param <TResult> ﻿The type to filter the elements of the sequence on.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains elements from ﻿the input sequence of type TResult.
     * @throws IllegalArgumentException type is null.
     */
    default <TResult> IEnumerable<TResult> ofType(final Class<TResult> type) throws IllegalArgumentException {
        if (type == null) throw new IllegalArgumentException("type is null.");

        return this.where(x -> type.isAssignableFrom(x.getClass())).cast(type);
    }

    /**
     * ﻿Casts the elements of a IEnumerable to the specified type.
     *
     * @param toType    ﻿The type to cast the elements of source to.
     * @param <TResult> ﻿The type to cast the elements of source to.
     * @return ﻿An &lt;TResult&gt; that contains each element of
     * ﻿the source sequence cast to the specified type.
     * @throws IllegalArgumentException      toType is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    default <TResult> IEnumerable<TResult> cast(final Class<TResult> toType) throws IllegalArgumentException, UnsupportedOperationException {
        if (toType == null) throw new IllegalArgumentException("toType is null.");
        if (this.count() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return new MapEnumerableIterator<>(this, item -> (TResult) item);
    }

    /**
     * ﻿Inverts the order of the elements in a sequence.
     *
     * @return ﻿A sequence whose elements correspond to those of the input sequence in reverse ﻿order.
     */
    default IEnumerable<TSource> reverse() {
        return () -> {
            Stack<TSource> stack = new Stack<>();
            for (TSource item : IEnumerable.this) stack.push(item);

            return new Iterator<TSource>() {
                @Override
                public boolean hasNext() {
                    return !stack.empty();
                }

                @Override
                public TSource next() {
                    return stack.pop();
                }
            };
        };
    }

    /**
     * ﻿Projects each element of a sequence to an IEnumerable&lt;TSource&gt; ﻿and flattens the resulting sequences into one sequence.
     *
     * @param selector  ﻿A transform function to apply to each element.
     * @param <TResult> ﻿The type of the elements of the sequence returned by selector.
     * @return ﻿An IEnumerable&lt;TSource&gt; whose elements are the result ﻿of invoking the one-to-many transform function on each element of the input ﻿sequence.
     * @throws IllegalArgumentException ﻿selector is null.
     */
    default <TResult> IEnumerable<TResult> selectMany(final Function<TSource, IEnumerable<TResult>> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return () -> {
            java.util.Queue<TResult> queue = new ArrayDeque<>();

            for (TSource item : this) {
                final IEnumerable<TResult> selected = selector.apply(item);
                for (TResult aSelected : selected) {
                    queue.add(aSelected);
                }
            }

            return new Iterator<TResult>() {
                @Override
                public boolean hasNext() {
                    return queue.size() != 0;
                }

                @Override
                public TResult next() {
                    return queue.remove();
                }
            };
        };
    }

    /**
     * ﻿Determines whether two sequences are equal by comparing the elements by using
     * ﻿the default equality comparer for their type.
     *
     * @param second ﻿An IEnumerable&lt;TSource&gt; to compare to the first sequence.
     * @return ﻿true if the two source sequences are of equal length and their corresponding
     * ﻿elements are equal according to the default equality comparer for their type    { return null;}
     * ﻿otherwise, false.
     * @throws IllegalArgumentException second is null.
     */
    default boolean sequenceEqual(final IEnumerable<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        if (this.count() != second.count()) return false;

        for (int i = 0; i < second.count(); i++) {
            if (!second.elementAt(i).equals(this.elementAt((i)))) {
                return false;
            }
        }

        return true;
    }

    /**
     * ﻿Returns the only element of a sequence, and throws an exception if there
     * ﻿is not exactly one element in the sequence.
     *
     * @return ﻿The single element of the input sequence.
     * @throws UnsupportedOperationException ﻿The input sequence contains more than one element.-or-The input sequence is empty.
     */
    default TSource single() throws UnsupportedOperationException {
        if (this.count() != 1)
            throw new UnsupportedOperationException("The input sequence contains more than one element.-or-The input sequence is empty.");

        final Iterator<TSource> iterator = this.iterator();
        if (iterator.hasNext()) {
            TSource item = iterator.next();

            if (iterator.hasNext())
                throw new UnsupportedOperationException("The input sequence contains more than one element.");
            return item;
        }

        throw new UnsupportedOperationException("The input sequence is empty.");
    }

    /**
     * ﻿﻿Returns the only element of a sequence that satisfies a specified condition,
     * ﻿and throws an exception if more than one such element exists.
     *
     * @param predicate ﻿The single element of the input sequence that satisfies a condition.
     * @return ﻿﻿The single element of the input sequence that satisfies a condition.
     * @throws IllegalArgumentException      predicate is null.
     * @throws UnsupportedOperationException ﻿No element satisfies the condition in predicate.-or-More than one element
     *                                       ﻿satisfies the condition in predicate.-or-The source sequence is empty.
     */
    default TSource single(final Predicate<TSource> predicate) throws IllegalArgumentException, UnsupportedOperationException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        final Iterator<TSource> iterator = this.where(predicate).iterator();
        if (iterator.hasNext()) {
            TSource item = iterator.next();

            if (iterator.hasNext())
                throw new UnsupportedOperationException("The input sequence contains more than one element.");
            return item;
        }

        throw new UnsupportedOperationException("The input sequence is empty.");
    }

    /**
     * ﻿Returns the only element of a sequence, or a default value if the sequence
     * ﻿is empty    { return null;} this method throws an exception if there is more than one element
     * ﻿in the sequence.
     *
     * @return ﻿The single element of the input sequence, or default(TSource) if the sequence
     * ﻿contains no elements.
     */
    default TSource singleOrDefault() throws UnsupportedOperationException {
        if (0 == this.count()) return null;
        if (1 < this.count())
            throw new UnsupportedOperationException("The input sequence contains more than one element.-or-The input sequence is empty.");

        final Iterator<TSource> iterator = this.iterator();
        if (iterator.hasNext()) {
            TSource item = iterator.next();

            if (iterator.hasNext()) return null;
            return item;
        }

        return null;
    }

    /**
     * ﻿Returns the only element of a sequence that satisfies a specified condition
     * ﻿or a default value if no such element exists    { return null;} this method throws an exception
     * ﻿if more than one element satisfies the condition.
     *
     * @param predicate ﻿A function to test an element for a condition.
     * @return ﻿The single element of the input sequence that satisfies the condition, or ﻿default(TSource) if no such element is found.
     * @throws IllegalArgumentException predicate is null.
     */
    default TSource singleOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        final Iterator<TSource> iterator = this.where(predicate).iterator();
        if (iterator.hasNext()) {
            TSource returnValue = iterator.next();

            if (iterator.hasNext()) return null;
            return returnValue;
        }

        return null;
    }

    /**
     * ﻿Bypasses a specified number of elements in a sequence and then returns the
     * ﻿remaining elements.
     *
     * @param count ﻿The number of elements to skip before returning the remaining elements.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains the elements that occur after the specified index in the input sequence.
     */
    default IEnumerable<TSource> skip(final int count) {
        return () -> {
            final Iterator<TSource> iterator = this.iterator();

            for (int i = 0; i < count; i++) {
                if (!iterator.hasNext()) break;
                iterator.next();
            }

            java.util.Queue<TSource> queue = new ArrayDeque<>();
            while (iterator.hasNext()) {
                queue.add(iterator.next());
            }

            return new Iterator<TSource>() {
                @Override
                public boolean hasNext() {
                    return queue.size() != 0;
                }

                @Override
                public TSource next() {
                    return queue.remove();
                }
            };
        };
    }

    /**
     * ﻿Bypasses elements in a sequence as long as a specified condition is true
     * ﻿and then returns the remaining elements.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿An System.Collections.Generic.IEnumerable&lt;TSource&gt; that contains the elements from
     * ﻿the input sequence starting at the first element in the linear series that
     * ﻿does not pass the test specified by predicate.
     * @throws IllegalArgumentException predicate is null.
     */
    default IEnumerable<TSource> skipWhile(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return () -> {
            java.util.Queue<TSource> queue = new ArrayDeque<>();

            final boolean none = (this.count(predicate) == this.count());
            if (!none) {
                final Iterator<TSource> iterator = this.iterator();
                TSource item = null;
                while (iterator.hasNext()) {
                    item = iterator.next();
                    if (!predicate.test(item)) {
                        break;
                    }
                }

                queue.add(item);
                while (iterator.hasNext()) {
                    queue.add(iterator.next());
                }
            }

            return new Iterator<TSource>() {
                @Override
                public boolean hasNext() {
                    return queue.size() != 0;
                }

                @Override
                public TSource next() {
                    return queue.remove();
                }
            };
        };
    }

    /**
     * ﻿Returns a number that represents how many elements in the specified sequence
     * ﻿satisfy a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿A number that represents how many elements in the sequence satisfy the condition
     * ﻿in the predicate function.
     * @throws IllegalArgumentException predicate is null.
     * @throws ArithmeticException      ﻿The number of elements in source is larger than Integer.MaxValue.
     */
    default int count(final Predicate<TSource> predicate) throws IllegalArgumentException, ArithmeticException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return this.where(predicate).count();
    }

    /**
     * ﻿Computes the sum of the sequence of BigDecimal values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The sum of the values in the sequence.
     * @throws IllegalArgumentException selector is null.
     */
    default BigDecimal sumBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        BigDecimal sum = BigDecimal.ZERO;
        for (TSource item : this) sum = sum.add(selector.apply(item));
        return sum;
    }

    /**
     * ﻿Computes the sum of the sequence of Double values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The sum of the values in the sequence.
     * @throws IllegalArgumentException ﻿selector is null.
     */
    default double sumDouble(final Function<TSource, Double> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        double sum = 0d;
        for (TSource item : this) sum += selector.apply(item);
        return sum;
    }

    /**
     * ﻿Computes the sum of the sequence of Integer values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿﻿The sum of the values in the sequence.
     * @throws IllegalArgumentException selector is null.
     * @throws ArithmeticException      ﻿The sum is larger than Integer.MaxValue.
     */
    default int sumInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, ArithmeticException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        int sum = 0;
        for (TSource item : this) {
            sum = Math.addExact(sum, selector.apply(item));
        }
        return sum;
    }

    /**
     * ﻿Computes the sum of the sequence of Long values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿﻿The sum of the values in the sequence.
     * @throws IllegalArgumentException selector is null.
     * @throws ArithmeticException      ﻿The sum is larger than Integer.MaxValue.
     */
    default long sumLong(final Function<TSource, Long> selector) throws IllegalArgumentException, ArithmeticException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        long sum = 0l;
        for (TSource item : this) {
            sum = Math.addExact(sum, selector.apply(item));
        }
        return sum;
    }

    /**
     * ﻿Returns a specified number of contiguous elements from the start of a sequence.
     *
     * @param count ﻿The number of elements to return.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains the specified
     * ﻿number of elements from the start of the input sequence.
     */
    default IEnumerable<TSource> take(final long count) {
        return () -> {
            final Iterator<TSource> iterator = this.iterator();

            java.util.Queue<TSource> queue = new ArrayDeque<>();
            for (int i = 0; i < count; i++) {
                if (!iterator.hasNext()) break;
                queue.add(iterator.next());
            }

            return new Iterator<TSource>() {
                @Override
                public boolean hasNext() {
                    return queue.size() != 0;
                }

                @Override
                public TSource next() {
                    return queue.remove();
                }
            };
        };
    }

    /**
     * ﻿Returns elements from a sequence as long as a specified condition is true.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains the elements from
     * @throws IllegalArgumentException predicate is null.
     */
    default IEnumerable<TSource> takeWhile(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return () -> {
            java.util.Queue<TSource> queue = new ArrayDeque<>();

            final boolean none = (this.count(predicate) == 0);
            if (!none) {
                for (TSource item : this) {
                    if (!predicate.test(item)) break;
                    queue.add(item);
                }
            }

            return new Iterator<TSource>() {
                @Override
                public boolean hasNext() {
                    return queue.size() != 0;
                }

                @Override
                public TSource next() {
                    return queue.remove();
                }
            };
        };
    }

    /**
     * ﻿﻿An IOrderedEnumerable&lt;TElement&gt; that contains elements to sort.
     *
     * @param keySelector A function to extract a key from an element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An IOrderedEnumerable&lt;TElement&gt; whose elements are sorted according ﻿to a key.
     * @throws IllegalArgumentException keySelector is null.
     */
    default <TKey extends Comparable> IEnumerable<TSource> thenBy(final Function<TSource, TKey> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        Comparator<TKey> comparator = (o1, o2) -> o1.compareTo(o2);
        if (this instanceof OrderedEnumerableIterator) {
            OrderedEnumerableIterator<TSource, TKey> source = (OrderedEnumerableIterator<TSource, TKey>) this;
            return source.createOrderedEnumerable(keySelector, comparator, false);
        } else {
            return orderBy(keySelector);
        }
    }

    /**
     * ﻿Sorts the elements of a sequence in ascending order according to a key.
     *
     * @param keySelector ﻿A function to extract a key from an element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An IEnumerable&lt;TSource&gt; whose elements are sorted according
     * ﻿to a key.
     * @throws IllegalArgumentException ﻿keySelector is null.
     */
    default <TKey extends Comparable> IEnumerable<TSource> orderBy(final Function<TSource, TKey> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        Comparator<TKey> comparator = (o1, o2) -> o1.compareTo(o2);
        return new OrderedEnumerableIterator<>(this, keySelector, comparator, false);
    }

    /**
     * ﻿﻿﻿Performs a subsequent ordering of the elements in a sequence in descending
     * ﻿order, according to a key.
     *
     * @param keySelector A function to extract a key from an element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An IOrderedEnumerable&lt;TElement&gt; whose elements are sorted in ﻿descending order according to a key.
     * @throws IllegalArgumentException keySelector is null.
     */
    default <TKey extends Comparable> IEnumerable<TSource> thenByDescending(final Function<TSource, TKey> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        Comparator<TKey> comparator = (o1, o2) -> o1.compareTo(o2);
        if (this instanceof OrderedEnumerableIterator) {
            OrderedEnumerableIterator<TSource, TKey> source = (OrderedEnumerableIterator<TSource, TKey>) this;
            return source.createOrderedEnumerable(keySelector, comparator, true);
        } else {
            return orderByDescending(keySelector);
        }
    }

    /**
     * ﻿Sorts the elements of a sequence in descending order according to a key.
     *
     * @param keySelector ﻿A function to extract a key from an element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An IEnumerable&lt;TSource&gt; whose elements are sorted in
     * ﻿descending order according to a key.
     * @throws IllegalArgumentException ﻿keySelector is null.
     */
    default <TKey extends Comparable> IEnumerable<TSource> orderByDescending(final Function<TSource, TKey> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        Comparator<TKey> comparator = (o1, o2) -> o1.compareTo(o2);
        return new OrderedEnumerableIterator<>(this, keySelector, comparator, true);
    }


    /**
     * Creates an array from an IEnumerable&lt;TSource&gt;.
     *
     * @param toType The type to cast the elements of source to.
     * @return An array that contains elements from the input sequence.
     * @throws IllegalArgumentException toType is null.
     */
    default TSource[] toArray(Class<TSource> toType) {
        if (toType == null) throw new IllegalArgumentException("toType is null.");

        TSource[] r = (TSource[]) Array.newInstance(toType, this.count());
        return this.toList().toArray(r);
    }

    /**
     * Creates a Dictionary&lt;TKey,TValue&gt; from an IEnumerable&lt;T&gt;
     * according to a specified key selector function.
     *
     * @param keySelector A function to extract a key from each element.
     * @param <TKey>      The type of the key returned by keySelector.
     * @return A Dictionary&lt;TKey,TValue&gt; that contains keys and values.
     * @throws IllegalArgumentException keySelector is null.-or-keySelector produces a key that is null.
     */
    default <TKey> Dictionary<TKey, TSource> toDictionary(final Function<TSource, TKey> keySelector) {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        Dictionary<TKey, TSource> allItems = new Dictionary<>();
        for (TSource element : this) {
            allItems.put(keySelector.apply(element), element);
        }

        return allItems;
    }

    /**
     * Creates a Dictionary&lt;TKey,TValue&gt; from an IEnumerable&lt;T&gt;
     * according to a specified key and element selector functions.
     *
     * @param keySelector     A function to extract a key from each element.
     * @param elementSelector ﻿A transform function to produce a result element value from each element.
     * @param <TKey>          The type of the key returned by keySelector.
     * @param <TElement>      ﻿The type of the value returned by elementSelector.
     * @return A Dictionary&lt;TKey,TValue&gt; that contains values of type TElement selected from the input sequence.
     * @throws IllegalArgumentException keySelector or elementSelector is null.-or-keySelector produces a key that is null.
     */
    default <TKey, TElement> Dictionary<TKey, TElement> toDictionary(final Function<TSource, TKey> keySelector, final Function<TSource, TElement> elementSelector) {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");
        if (elementSelector == null) throw new IllegalArgumentException("elementSelector is null.");

        Dictionary<TKey, TElement> allItems = new Dictionary<>();

        for (TSource element : this) {
            allItems.put(keySelector.apply(element), elementSelector.apply(element));
        }

        return allItems;
    }

    /**
     * Creates a List&lt;TSource&gt; from an IEnumerable&lt;TSource&gt;.
     *
     * @return A List&lt;TSource&gt; that contains elements from the input sequence.
     */
    default List<TSource> toList() {
        return new List<>(this);
    }

    /**
     * ﻿Produces the set union of two sequences by using the default equality comparer.
     *
     * @param second ﻿An IEnumerable&lt;TSource&gt; whose distinct elements form ﻿the second set for the union.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains the elements from ﻿both input sequences, excluding duplicates.
     * @throws IllegalArgumentException second is null.
     */
    default IEnumerable<TSource> union(final IEnumerable<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return () -> {
            Set<TSource> allItems = new Set<>();
            for (TSource item : this) allItems.add(item);
            for (TSource item : second) allItems.add(item);

            return allItems.iterator();
        };
    }

    /**
     * ﻿Filters a sequence of values based on a predicate.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains elements from
     * ﻿the input sequence that satisfy the condition.
     * @throws IllegalArgumentException predicate is null.
     */
    default IEnumerable<TSource> where(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return new WhereEnumerableIterator<>(this, predicate);
    }

    /**
     * ﻿Filters a sequence of values based on a predicate.
     *
     * @param predicate ﻿A function to test each source element for a condition; the second parameter
     *                  of the function represents the index of the source element.
     * @return ﻿An IEnumerable&lt;TSource&gt; that contains elements from
     * ﻿the input sequence that satisfy the condition.
     * @throws IllegalArgumentException predicate is null.
     * @throws ArithmeticException      ﻿The number of elements in source is larger than Integer.MaxValue.
     */
    default IEnumerable<TSource> where(final BiPredicate<TSource, Integer> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return () -> {
            List<TSource> allItems = new List<>();
            int index = -1;
            for (TSource element : this) {
                index = Math.addExact(index, 1);
                if (predicate.test(element, index)) {
                    allItems.add(element);
                }
            }

            return allItems.iterator();
        };
    }

    /**
     * ﻿Merges two sequences by using the specified predicate function.
     *
     * @param second         ﻿The second sequence to merge.
     * @param resultSelector ﻿A function that specifies how to merge the elements from the two sequences.
     * @param <TSecond>      ﻿The type of the elements of the second input sequence.
     * @param <TResult>      ﻿The type of the elements of the result sequence.
     * @return ﻿An IEnumerable&lt;T&gt; that contains merged elements
     * ﻿of two input sequences.
     * @throws IllegalArgumentException second is null.
     */
    default <TSecond, TResult> IEnumerable<TResult> zip(final IEnumerable<TSecond> second, final BiFunction<TSource, TSecond, TResult> resultSelector) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");
        if (resultSelector == null) throw new IllegalArgumentException("resultSelector is null.");

        return () -> {
            List<TResult> allItems = new List<>();

            Iterator<TSource> e1 = this.iterator();
            Iterator<TSecond> e2 = second.iterator();
            while (e1.hasNext() && e2.hasNext()) {
                allItems.add(resultSelector.apply(e1.next(), e2.next()));
            }

            return allItems.iterator();
        };
    }
}