package com.jlinqer.linq;

import com.jlinqer.util.List;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by Keisuke Kato
 */
public interface IEnumerable<TSource> extends Collection<TSource> {
// -------------------------- OTHER METHODS --------------------------

    /**
     * ﻿Applies an accumulator function over a sequence.
     *
     * @param accumulator ﻿An accumulator function to be invoked on each element.
     * @return ﻿The final accumulator value.
     * @throws IllegalArgumentException      accumulator is null.
     * @throws UnsupportedOperationException source contains no elements.
     */
    TSource aggregate(final BinaryOperator<TSource> accumulator) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Determines whether all elements of a sequence satisfy a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿true if every element of the source sequence passes the test in the specified
     * ﻿predicate, or if the sequence is empty; otherwise, false.
     * @throws IllegalArgumentException predicate is null.
     */
    boolean all(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Determines whether a sequence contains any elements.
     *
     * @return ﻿true if the source sequence contains any elements; otherwise, false.
     */
    boolean any();

    /**
     * ﻿Determines whether any element of a sequence satisfies a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿true if any elements in the source sequence pass the test in the specified
     * ﻿predicate; otherwise, false.
     * @throws IllegalArgumentException predicate is null.
     */
    boolean any(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿﻿Computes the average of a sequence of Decimal values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    BigDecimal averageBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException;


    /**
     * ﻿﻿﻿Computes the average of a sequence of Double values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    double averageDouble(final Function<TSource, Double> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿﻿Computes the average of a sequence of Integer values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    double averageInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, UnsupportedOperationException;


    /**
     * ﻿﻿Computes the average of a sequence of Long values that are obtained
     * ﻿﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The average of the sequence of values.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    double averageLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Casts the elements of an List to the specified type.
     *
     * @param toType    ﻿The type to cast the elements of source to.
     * @param <TResult> ﻿The type to cast the elements of source to.
     * @return ﻿An <TResult> that contains each element of
     * ﻿the source sequence cast to the specified type.
     * @throws IllegalArgumentException      toType is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    <TResult> List<TResult> cast(final Class<TResult> toType) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Concatenates two sequences.
     *
     * @param second ﻿The sequence to concatenate to the first sequence.
     * @return ﻿An IEnumerable<TSource> that contains the concatenated ﻿elements of the two input sequences.
     * @throws IllegalArgumentException ﻿﻿second is null.
     */
    IEnumerable<TSource> concat(final List<TSource> second) throws IllegalArgumentException;


    /**
     * ﻿Returns the number of elements in a sequence.
     *
     * @return ﻿﻿The number of elements in the input sequence.
     */
    long count();

    /**
     * ﻿Returns a number that represents how many elements in the specified sequence
     * ﻿satisfy a condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿A number that represents how many elements in the sequence satisfy the condition
     * ﻿in the predicate function.
     * @throws IllegalArgumentException predicate is null.
     */
    long count(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Returns the elements of the specified sequence or the type parameter's default
     * ﻿value in a singleton collection if the sequence is empty.
     *
     * @return ﻿An IEnumerable<TSource> object that contains the default
     * ﻿value for the TSource type if source is empty; otherwise, source.
     */
    IEnumerable<TSource> defaultIfEmpty();

    /**
     * ﻿Returns the elements of the specified sequence or the specified value in
     * ﻿a singleton collection if the sequence is empty.
     *
     * @param defaultValue ﻿The value to return if the sequence is empty.
     * @return ﻿An System.Collections.Generic.IEnumerable<TSource> that contains defaultValue if
     * ﻿source is empty; otherwise, source.
     */
    IEnumerable<TSource> defaultIfEmpty(final TSource defaultValue);

    /**
     * ﻿Returns distinct elements from a sequence by using the default equality ﻿comparer
     * ﻿to compare values.
     *
     * @return ﻿An IEnumerable<TSource> that contains distinct elements ﻿from the source sequence.
     */
    IEnumerable<TSource> distinct();

    /**
     * ﻿Returns the element at a specified index in a sequence.
     *
     * @param index ﻿The zero-based index of the element to retrieve.
     * @return ﻿The element at the specified position in the source sequence.
     * @throws IndexOutOfBoundsException ﻿index is less than 0 or greater than or equal to the number of elements in
     *                                   ﻿source.
     */
    TSource elementAt(final int index) throws IndexOutOfBoundsException;

    /**
     * ﻿Returns the element at a specified index in a sequence or a default value
     * ﻿if the index is out of range.
     *
     * @param index ﻿The zero-based index of the element to retrieve.
     * @return ﻿default(TSource) if the index is outside the bounds of the source sequence;
     * ﻿otherwise, the element at the specified position in the source sequence.
     */
    TSource elementAtOrDefault(final int index);

    /**
     * ﻿Produces the set difference of two sequences by using the default equality
     * ﻿comparer to compare values.
     *
     * @param second ﻿An IEnumerable<TSource> whose elements that also occur ﻿in the first sequence will cause those elements
     *               to be removed from the returned ﻿sequence.
     * @return ﻿A sequence that contains the set difference of the elements of two sequences.
     * @throws IllegalArgumentException second is null.
     */
    IEnumerable<TSource> except(final List<TSource> second) throws IllegalArgumentException;

    /**
     * ﻿Returns the first element of a sequence.
     *
     * @return ﻿The first element in the specified sequence.
     */
    TSource first() throws UnsupportedOperationException;

    /**
     * ﻿Returns the first element in a sequence that satisfies a specified condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿The first element in the sequence that passes the test in the specified predicate ﻿function.
     * @throws UnsupportedOperationException ﻿﻿No element satisfies the condition in predicate.-or-The source sequence is empty.
     */
    TSource first(final Predicate<TSource> predicate) throws UnsupportedOperationException;

    /**
     * ﻿Returns the first element of a sequence, or a default value if the sequence
     * ﻿of a sequence, or a default value if the sequence
     *
     * @return ﻿﻿﻿default(TSource) if source is empty; otherwise, the first element in source.
     */
    TSource firstOrDefault();

    /**
     * ﻿Returns the first element of the sequence that satisfies a condition or a
     * ﻿default value if no such element is found.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿default(TSource) if source is empty or if no element passes the test specified
     * ﻿by predicate; otherwise, the first element in source that passes the test
     * ﻿specified by predicate.
     * @throws IllegalArgumentException ﻿predicate is null.
     */
    TSource firstOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Groups the elements of a sequence according to a specified key selector function.
     *
     * @param keySelector ﻿A function to extract the key for each element.
     * @param <K>         ﻿The type of the key returned by keySelector.
     * @return ﻿An Map<K, IEnumerable<TSource>> ﻿object contains a sequence of objects and a key.
     * @throws IllegalArgumentException ﻿keySelector is null.
     */
    <K> Map<K, List<TSource>> groupBy(final Function<TSource, K> keySelector) throws IllegalArgumentException;


    /**
     * ﻿Produces the set intersection of two sequences by using the default equality
     * ﻿comparer to compare values.
     *
     * @param second ﻿An IEnumerable<TSource> whose distinct elements that ﻿also appear in the first sequence will be returned.
     * @return ﻿A sequence that contains the elements that form the set intersection of two ﻿sequences.
     * @throws IllegalArgumentException second is null.
     */
    IEnumerable<TSource> intersect(final List<TSource> second) throws IllegalArgumentException;

    /**
     * ﻿﻿Returns the last element of a sequence.
     *
     * @return ﻿The value at the last position in the source sequence.
     * @throws UnsupportedOperationException ﻿The source sequence is empty.
     */
    TSource last() throws UnsupportedOperationException;

    /**
     * ﻿﻿﻿Returns the last element of a sequence that satisfies a specified condition.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿﻿The last element in the sequence that passes the test in the specified predicate ﻿function.
     * @throws IllegalArgumentException      predicate is null.
     * @throws UnsupportedOperationException ﻿﻿No element satisfies the condition in predicate.-or-The source sequence is ﻿empty.
     */
    TSource last(final Predicate<TSource> predicate) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿﻿﻿Returns the last element of a sequence, or a default value if the sequence
     * ﻿contains no elements.
     *
     * @return ﻿﻿default(TSource) if the source sequence is empty; otherwise, the last element
     * ﻿in the IEnumerable<TSource>.
     */
    TSource lastOrDefault();

    /**
     * ﻿Returns the last element of a sequence that satisfies a condition or a default
     * ﻿value if no such element is found.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿default(TSource) if the sequence is empty or if no elements pass the test
     * ﻿in the predicate function; otherwise, the last element that passes the test
     * ﻿in the predicate function.
     * throws IllegalArgumentException predicate is null.
     */
    TSource lastOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * ﻿maximum BigDecimal value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The maximum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    BigDecimal maxBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * ﻿maximum Double value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The maximum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    double maxDouble(final Function<TSource, Double> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * ﻿maximum Integer value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The maximum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    int maxInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * ﻿maximum Long value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The maximum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    long maxLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * minimum BigDecimal value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The minimum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    BigDecimal minBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * minimum Double value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The minimum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    double minDouble(final Function<TSource, Double> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * minimum Integer value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The minimum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    int minInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Invokes a transform function on each element of a sequence and returns the
     * minimum Long value.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿The minimum value in the sequence.
     * @throws IllegalArgumentException      selector is null.
     * @throws UnsupportedOperationException ﻿source contains no elements.
     */
    long minLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Filters the elements of an List based on a specified ﻿type.
     *
     * @param type ﻿The type to filter the elements of the sequence on.
     * @param <R>  ﻿The type to filter the elements of the sequence on.
     * @return ﻿An IEnumerable<TSource> that contains elements from ﻿the input sequence of type R.
     * @throws IllegalArgumentException type is null.
     */
    <R> IEnumerable<R> ofType(final Class<R> type) throws IllegalArgumentException;

    /**
     * ﻿Sorts the elements of a sequence in ascending order according to a key.
     *
     * @param keySelector ﻿A function to extract a key from an element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An IEnumerable<TSource> whose elements are sorted according
     * ﻿to a key.
     * @throws IllegalArgumentException ﻿keySelector is null.
     */
    <TKey extends Comparable> IEnumerable<TSource> orderBy(final Function<TSource, TKey> keySelector) throws IllegalArgumentException;

    /**
     * ﻿Sorts the elements of a sequence in descending order according to a key.
     *
     * @param keySelector ﻿A function to extract a key from an element.
     * @param <TKey>      ﻿The type of the key returned by keySelector.
     * @return ﻿An IEnumerable<TSource> whose elements are sorted in
     * ﻿descending order according to a key.
     * @throws IllegalArgumentException ﻿keySelector is null.
     */
    <TKey extends Comparable> IEnumerable<TSource> orderByDescending(final Function<TSource, TKey> keySelector) throws IllegalArgumentException;


    /**
     * ﻿Inverts the order of the elements in a sequence.
     *
     * @return ﻿A sequence whose elements correspond to those of the input sequence in reverse ﻿order.
     */
    IEnumerable<TSource> reverse();


    /**
     * ﻿Projects each element of a sequence into a new form.
     *
     * @param selector  ﻿A transform function to apply to each element.
     * @param <TResult> ﻿The type of the value returned by selector.
     * @return ﻿An IEnumerable<TResult> whose elements are the result
     * ﻿of invoking the transform function on each element of source.
     * @throws IllegalArgumentException ﻿selector is null.
     */
    <TResult> List<TResult> select(final Function<TSource, TResult> selector) throws IllegalArgumentException;

    /**
     * ﻿Projects each element of a sequence to an IEnumerable<TSource> ﻿and flattens the resulting sequences into one sequence.
     *
     * @param selector  ﻿A transform function to apply to each element.
     * @param <TResult> ﻿The type of the elements of the sequence returned by selector.
     * @return ﻿An IEnumerable<TSource> whose elements are the result ﻿of invoking the one-to-many transform function on each element of the input ﻿sequence.
     * @throws IllegalArgumentException ﻿selector is null.
     */
    <TResult> List<TResult> selectMany(final Function<TSource, Stream<TResult>> selector) throws IllegalArgumentException;

    /**
     * ﻿Determines whether two sequences are equal by comparing the elements by using
     * ﻿the default equality comparer for their type.
     *
     * @param second ﻿An IEnumerable<TSource> to compare to the first sequence.
     * @return ﻿true if the two source sequences are of equal length and their corresponding
     * ﻿elements are equal according to the default equality comparer for their type;
     * ﻿otherwise, false.
     * @throws IllegalArgumentException second is null.
     */
    boolean sequenceEqual(final List<TSource> second) throws IllegalArgumentException;

    /**
     * ﻿Returns the only element of a sequence, and throws an exception if there
     * ﻿is not exactly one element in the sequence.
     *
     * @return ﻿The single element of the input sequence.
     * @throws UnsupportedOperationException ﻿The input sequence contains more than one element.-or-The input sequence ﻿is empty.
     */
    TSource single() throws UnsupportedOperationException;

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
    TSource single(final Predicate<TSource> predicate) throws IllegalArgumentException, UnsupportedOperationException;

    /**
     * ﻿Returns the only element of a sequence, or a default value if the sequence
     * ﻿is empty; this method throws an exception if there is more than one element
     * ﻿in the sequence.
     *
     * @return ﻿The single element of the input sequence, or default(TSource) if the sequence
     * ﻿contains no elements.
     */
    TSource singleOrDefault() throws UnsupportedOperationException;

    /**
     * ﻿Returns the only element of a sequence that satisfies a specified condition
     * ﻿or a default value if no such element exists; this method throws an exception
     * ﻿if more than one element satisfies the condition.
     *
     * @param predicate ﻿A function to test an element for a condition.
     * @return ﻿The single element of the input sequence that satisfies the condition, or ﻿default(TSource) if no such element is found.
     * @throws IllegalArgumentException predicate is null.
     */
    TSource singleOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Bypasses a specified number of elements in a sequence and then returns the
     * ﻿remaining elements.
     *
     * @param count ﻿The number of elements to skip before returning the remaining elements.
     * @return ﻿An IEnumerable<TSource> that contains the elements that occur after the specified index in the input sequence.
     */
    IEnumerable<TSource> skip(final int count);

    /**
     * ﻿Bypasses elements in a sequence as long as a specified condition is true
     * ﻿and then returns the remaining elements.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿An System.Collections.Generic.IEnumerable<TSource> that contains the elements from
     * ﻿the input sequence starting at the first element in the linear series that
     * ﻿does not pass the test specified by predicate.
     * @throws IllegalArgumentException predicate is null.
     */
    IEnumerable<TSource> skipWhile(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Computes the sum of the sequence of BigDecimal values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿selector is null.
     * @throws IllegalArgumentException
     */
    BigDecimal sumBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException;

    /**
     * ﻿Computes the sum of the sequence of Double values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿selector is null.
     * @throws IllegalArgumentException
     */
    double sumDouble(final Function<TSource, Double> selector) throws IllegalArgumentException;

    /**
     * ﻿Computes the sum of the sequence of Integer values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿selector is null.
     * @throws IllegalArgumentException
     */
    int sumInt(final Function<TSource, Integer> selector) throws IllegalArgumentException;

    /**
     * ﻿Computes the sum of the sequence of Long values that are obtained
     * ﻿by invoking a transform function on each element of the input sequence.
     *
     * @param selector ﻿A transform function to apply to each element.
     * @return ﻿selector is null.
     * @throws IllegalArgumentException
     */
    long sumLong(final Function<TSource, Long> selector) throws IllegalArgumentException;

    /**
     * ﻿Returns a specified number of contiguous elements from the start of a sequence.
     *
     * @param count ﻿The number of elements to return.
     * @return ﻿An IEnumerable<TSource> that contains the specified
     * ﻿number of elements from the start of the input sequence.
     */
    IEnumerable<TSource> take(final long count);

    /**
     * ﻿Returns elements from a sequence as long as a specified condition is true.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿An IEnumerable<TSource> that contains the elements from
     * @throws IllegalArgumentException predicate is null.
     */
    IEnumerable<TSource> takeWhile(final Predicate<TSource> predicate) throws IllegalArgumentException;

    /**
     * ﻿Produces the set union of two sequences by using the default equality comparer.
     *
     * @param second ﻿An IEnumerable<TSource> whose distinct elements form ﻿the second set for the union.
     * @return ﻿An IEnumerable<TSource> that contains the elements from ﻿both input sequences, excluding duplicates.
     * @throws IllegalArgumentException second is null.
     */
    IEnumerable<TSource> union(final List<TSource> second) throws IllegalArgumentException;

    /**
     * ﻿Filters a sequence of values based on a predicate.
     *
     * @param predicate ﻿A function to test each element for a condition.
     * @return ﻿An IEnumerable<TSource> that contains elements from
     * ﻿the input sequence that satisfy the condition.
     * @throws IllegalArgumentException predicate is null.
     */
    IEnumerable<TSource> where(final Predicate<TSource> predicate) throws IllegalArgumentException;
}