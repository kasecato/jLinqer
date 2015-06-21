package com.jlinqer.util;

import com.jlinqer.linq.IEnumerable;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Keisuke Kato
 */
public class List<TSource> implements java.util.List<TSource>, IEnumerable<TSource> {
// ------------------------------ FIELDS ------------------------------

    private TSource defaultValue;
    private java.util.List<TSource> list = null;

// -------------------------- STATIC METHODS --------------------------

    /**
     * ﻿Returns an empty List<T> that has the specified ﻿type argument.
     *
     * @param type      The type to assign to the type parameter of the returned generic List<T>.
     * @param <TResult> ﻿The type to assign to the type parameter of the returned generic List<T>.
     * @return ﻿An empty List<T> whose type argument is ﻿TResult.
     * @throws IllegalArgumentException type is null.
     */
    public static <TResult> List<TResult> empty(final Class<TResult> type) {
        if (type == null) throw new IllegalArgumentException("type is null.");

        return new List<>();
    }

    /**
     * ﻿Generates a sequence that contains one repeated value.
     *
     * @param type      ﻿The type of the value to be repeated in the result sequence.
     * @param element   ﻿The value to be repeated.
     * @param count     ﻿The number of times to repeat the value in the generated sequence.
     * @param <TResult> ﻿The type of the value to be repeated in the result sequence.
     * @return ﻿An List<TResult> that contains a repeated value.
     * @throws IllegalArgumentException  ﻿type is null.
     * @throws IndexOutOfBoundsException ﻿count is less than 0.
     */
    public static <TResult> List<TResult> repeat(final Class<TResult> type, final TResult element, final int count) throws IllegalArgumentException, IndexOutOfBoundsException {
        if (type == null) throw new IllegalArgumentException("type is null.");
        if (count < 0) throw new IndexOutOfBoundsException("count is less than 0.");

        List<TResult> list = new List<>();
        for (int i = 0; i < count; i++) {
            list.add(element);
        }

        return list;
    }

    @Override
    public boolean add(final TSource t) {
        return list.add(t);
    }

    /**
     * ﻿Generates a sequence of integral numbers within a specified range.
     *
     * @param start ﻿The value of the first integer in the sequence.
     * @param count ﻿The number of sequential integers to generate.
     * @return ﻿An List<Integer> that ﻿contains a range of sequential integral numbers.
     * @throws IndexOutOfBoundsException ﻿count is less than 0.-or-start + count -1 is larger than Integer.MaxValue.
     */
    public static List<Integer> range(final int start, final int count) throws IndexOutOfBoundsException {
        if (count < 0) throw new IndexOutOfBoundsException("count is less than 0.");
        if (Integer.MAX_VALUE < (long) start + (long) count - 1)
            throw new IndexOutOfBoundsException("start + count -1 is larger than Integer.MaxValue.");

        List<Integer> list = new List<>();
        for (int i = 0; i < count; i++) {
            list.add(start + i);
        }

        return list;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public List() {
        list = new ArrayList<>();
    }

    @SafeVarargs
    public List(final TSource... args) {
        list = Arrays.asList(args);
    }

    public List(final java.util.List<TSource> list) {
        this.list = list;
    }

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Collection ---------------------

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <TSource> TSource[] toArray(TSource[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends TSource> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

// --------------------- Interface IEnumerable ---------------------

    @Override
    public TSource aggregate(final BinaryOperator<TSource> accumulator) throws IllegalArgumentException, UnsupportedOperationException {
        if (accumulator == null) throw new IllegalArgumentException("accumulator is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream().reduce(accumulator).get();
    }

    @Override
    public boolean all(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return list.stream().allMatch(predicate);
    }

    @Override
    public boolean any() {
        return list.size() != 0;
    }

    @Override
    public boolean any(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return list.stream().anyMatch(predicate);
    }

    @Override
    public BigDecimal averageBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return sumBigDecimal(selector)
                .divide(new BigDecimal(count()));
    }

    @Override
    public double averageDouble(final Function<TSource, Double> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .collect(Collectors.averagingDouble(x -> x));
    }

    @Override
    public double averageInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .collect(Collectors.averagingInt(x -> x));
    }

    @Override
    public double averageLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .collect(Collectors.averagingLong(x -> x));
    }

    @Override
    public <TResult> List<TResult> cast(final Class<TResult> toType) throws UnsupportedOperationException {
        if (toType == null) throw new IllegalArgumentException("toType is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return new List<>(
                list.stream()
                        .map(x -> (TResult) x)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<TSource> concat(final List<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return new List<>(
                Stream.concat(
                        list.stream(),
                        second.stream()
                ).collect(Collectors.toList())
        );
    }

    @Override
    public long count() {
        return list.stream().count();
    }

    @Override
    public long count(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return list.stream()
                .filter(predicate)
                .count();
    }

    @Override
    public List<TSource> defaultIfEmpty() {
        return (list.size() == 0) ? new List<>(defaultValue) : this;
    }

    @Override
    public List<TSource> defaultIfEmpty(final TSource defaultValue) {
        return (list.size() == 0) ? new List<>(defaultValue) : this;
    }

    @Override
    public List<TSource> distinct() {
        return new List<>(
                list.stream()
                        .distinct()
                        .collect(Collectors.toList())
        );
    }

    @Override
    public TSource elementAt(final int index) throws IndexOutOfBoundsException {
        if (index < 0 || list.size() < index + 1)
            throw new IndexOutOfBoundsException("index is less than 0 or greater than or equal to the number of elements in source.");

        return list.get(index);
    }

    @Override
    public TSource elementAtOrDefault(final int index) {
        return (index < 0 || list.size() < index + 1)
                ? defaultValue
                : list.get(index);
    }

    @Override
    public List<TSource> except(final List<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return new List<>(
                list.stream()
                        .filter(x -> !second.contains(x))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public TSource first() throws UnsupportedOperationException {
        if (list.size() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        return list.stream()
                .findFirst().get();
    }

    @Override
    public TSource first(final Predicate<TSource> predicate) throws UnsupportedOperationException {
        if (list.size() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        final Optional<TSource> first =
                list.stream()
                        .filter(predicate)
                        .findFirst();

        if (!first.isPresent())
            throw new UnsupportedOperationException("No element satisfies the condition in predicate.");

        return first.get();
    }

    @Override
    public TSource firstOrDefault() {
        return list.stream()
                .findFirst().orElse(defaultValue);
    }

    @Override
    public TSource firstOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return list.stream()
                .filter(predicate)
                .findFirst().orElse(defaultValue);
    }

    @Override
    public <K> Map<K, List<TSource>> groupBy(final Function<TSource, K> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        final Map<K, java.util.List<TSource>> groupedBy =
                list.stream()
                        .collect(Collectors.groupingBy(keySelector));

        Map<K, List<TSource>> toMapList = new HashMap<>();
        for (K key : groupedBy.keySet()) {
            toMapList.put(key, new List<>(groupedBy.get(key)));
        }

        return toMapList;
    }

    @Override
    public List<TSource> intersect(final List<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return new List<>(
                list.stream()
                        .filter(second::contains)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public TSource last() throws UnsupportedOperationException {
        if (list.size() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        return list.get(list.size() - 1);
    }

    @Override
    public TSource last(final Predicate<TSource> predicate) throws IllegalArgumentException, UnsupportedOperationException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        final java.util.List<TSource> filtered =
                list.stream()
                        .filter(predicate)
                        .collect(Collectors.toList());

        if (filtered.size() == 0) throw new UnsupportedOperationException("The source sequence is empty.");

        return filtered.get(filtered.size() - 1);
    }

    @Override
    public TSource lastOrDefault() {
        if (list.size() == 0) return defaultValue;

        return list.get(list.size() - 1);
    }

    @Override
    public TSource lastOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        if (list.size() == 0) return defaultValue;

        final java.util.List<TSource> filtered =
                list.stream()
                        .filter(predicate)
                        .collect(Collectors.toList());

        return (filtered.size() == 0)
                ? defaultValue
                : filtered.get(filtered.size() - 1);
    }

    @Override
    public BigDecimal maxBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(BigDecimal::max)
                .get();
    }

    @Override
    public double maxDouble(final Function<TSource, Double> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(Double::max)
                .get();
    }

    @Override
    public int maxInt(final Function<TSource, Integer> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(Integer::max)
                .get();
    }

    @Override
    public long maxLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(Long::max)
                .get();
    }

    @Override
    public BigDecimal minBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(BigDecimal::min)
                .get();
    }

    @Override
    public double minDouble(final Function<TSource, Double> selector) {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(Double::min)
                .get();
    }

    @Override
    public int minInt(final Function<TSource, Integer> selector) {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(Integer::min)
                .get();
    }

    @Override
    public long minLong(final Function<TSource, Long> selector) throws IllegalArgumentException, UnsupportedOperationException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");
        if (list.size() == 0) throw new UnsupportedOperationException("source contains no elements.");

        return list.stream()
                .map(selector)
                .reduce(Long::min)
                .get();
    }

    @Override
    public <R> List<R> ofType(final Class<R> type) throws IllegalArgumentException {
        if (type == null) throw new IllegalArgumentException("type is null.");

        return new List<>(
                list.stream()
                        .filter(x -> type.isAssignableFrom(x.getClass()))
                        .map(x -> (R) x)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public <TKey extends Comparable> List<TSource> orderBy(final Function<TSource, TKey> keySelector) throws IllegalArgumentException {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        return new List<>(
                list.stream()
                        .sorted((l, r) -> keySelector.apply(l).compareTo(keySelector.apply(r)))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public <TKey extends Comparable> List<TSource> orderByDescending(final Function<TSource, TKey> keySelector) {
        if (keySelector == null) throw new IllegalArgumentException("keySelector is null.");

        return new List<>(
                list.stream()
                        .sorted((l, r) -> keySelector.apply(r).compareTo(keySelector.apply(l)))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<TSource> reverse() {
        final java.util.List<TSource> copy = new ArrayList<>(list);
        Collections.reverse(copy);
        return new List<>(copy);
    }

    @Override
    public <TResult> List<TResult> select(final Function<TSource, TResult> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return new List(
                list.stream()
                        .map(selector)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public <TResult> List<TResult> selectMany(final Function<TSource, Stream<TResult>> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return new List(
                list.stream()
                        .flatMap(selector)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean sequenceEqual(final List<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        if (list.size() != second.count()) return false;

        for (int i = 0; i < second.count(); i++) {
            if (!second.get(i).equals(list.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public TSource single() throws UnsupportedOperationException {
        if (list.size() != 1)
            throw new UnsupportedOperationException("The input sequence contains more than one element.-or-The input sequence is empty.");

        return list.get(0);
    }

    @Override
    public TSource single(final Predicate<TSource> predicate) throws IllegalArgumentException, UnsupportedOperationException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        long single = list.stream().filter(predicate).count();

        if (single != 1)
            throw new UnsupportedOperationException("The input sequence contains more than one element.-or-The input sequence is empty.");

        return list.stream().filter(predicate).findFirst().get();
    }

    @Override
    public TSource singleOrDefault() throws UnsupportedOperationException {
        if (0 == list.size()) return defaultValue;
        if (1 < list.size())
            throw new UnsupportedOperationException("The input sequence contains more than one element.-or-The input sequence is empty.");

        return list.get(0);
    }

    @Override
    public TSource singleOrDefault(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        final Optional<TSource> single = list.stream().filter(predicate).findFirst();

        if (!single.isPresent()) return defaultValue;

        return single.get();
    }

    @Override
    public List<TSource> skip(final int count) {
        return new List<>(
                list.stream()
                        .skip(count)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<TSource> skipWhile(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        long cnt = 0;
        for (TSource row : list) {
            if (!predicate.test(row)) break;
            cnt++;
        }
        return new List<>(
                list.stream()
                        .skip(cnt)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public BigDecimal sumBigDecimal(final Function<TSource, BigDecimal> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return list.stream()
                .map(selector)
                .reduce(BigDecimal::add)
                .get();
    }

    @Override
    public double sumDouble(final Function<TSource, Double> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return list.stream()
                .map(selector)
                .reduce(Double::sum)
                .get();
    }

    @Override
    public int sumInt(final Function<TSource, Integer> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return list.stream()
                .map(selector)
                .reduce(Integer::sum)
                .get();
    }

    @Override
    public long sumLong(final Function<TSource, Long> selector) throws IllegalArgumentException {
        if (selector == null) throw new IllegalArgumentException("selector is null.");

        return list.stream()
                .map(selector)
                .reduce(Long::sum)
                .get();
    }

    @Override
    public List<TSource> take(final long count) {
        return new List<>(
                list.stream()
                        .limit(count)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<TSource> takeWhile(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        long cnt = 0;
        for (TSource row : list) {
            if (!predicate.test(row)) break;
            cnt++;
        }

        return new List<>(
                list.stream()
                        .limit(cnt)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<TSource> union(final List<TSource> second) throws IllegalArgumentException {
        if (second == null) throw new IllegalArgumentException("second is null.");

        return new List<>(
                Stream.concat(list.stream(),
                        second.stream()
                                .filter(x -> list.stream().noneMatch(y -> y.equals(x)))
                ).collect(Collectors.toList())
        );
    }

    @Override
    public List<TSource> where(final Predicate<TSource> predicate) throws IllegalArgumentException {
        if (predicate == null) throw new IllegalArgumentException("predicate is null.");

        return new List<>(
                list.stream()
                        .filter(predicate)
                        .collect(Collectors.toList())
        );
    }

// --------------------- Interface Iterable ---------------------

    @Override
    public Iterator<TSource> iterator() {
        return list.iterator();
    }

// --------------------- Interface List ---------------------

    @Override
    public boolean addAll(int index, Collection<? extends TSource> c) {
        return list.addAll(index, c);
    }

    @Override
    public TSource get(int index) {
        return list.get(index);
    }

    @Override
    public TSource set(int index, TSource element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, TSource element) {
        list.add(index, element);
    }

    @Override
    public TSource remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<TSource> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<TSource> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public java.util.List<TSource> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
