package com.github.jlinqer.linq;

import com.github.jlinqer.collections.List;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Keisuke Kato
 */
class IEnumerableAbnomalTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    void aggregate_abnormal() {
        // arrange
        List<Integer> list = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.aggregate(null));

        assertThrows(UnsupportedOperationException.class,
                () -> list.aggregate((sum, elem) -> sum + elem));
    }

    @Test
    void all_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.all(null));
    }

    @Test
    void any_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.any(null));
    }

    @Test
    void average_abnormal() {
        // arrange
        List<Integer> listInt = new List<>(1, 2, 3, 4);
        List<Long> listLong = new List<>(1l, 2l, 3l, 4l);
        List<Double> listDouble = new List<>(1d, 2d, 3d, 4d);
        List<BigDecimal> listBigDecimal = new List<>(
                new BigDecimal(1d),
                new BigDecimal(2d),
                new BigDecimal(3d),
                new BigDecimal(4d)
        );

        List<Integer> listIntEmpty = new List<>();
        List<Long> listLongEmpty = new List<>();
        List<Double> listDoubleEmpty = new List<>();
        List<BigDecimal> listBigDecimalEmpty = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> listInt.averageInt(null));

        assertThrows(UnsupportedOperationException.class,
                () -> listIntEmpty.averageInt(Function.identity()));

        assertThrows(IllegalArgumentException.class,
                () -> listLong.averageLong(null));

        assertThrows(UnsupportedOperationException.class,
                () -> listLongEmpty.averageLong(Function.identity()));

        assertThrows(IllegalArgumentException.class,
                () -> listDouble.averageDouble(null));

        assertThrows(UnsupportedOperationException.class,
                () -> listDoubleEmpty.averageDouble(Function.identity()));

        assertThrows(IllegalArgumentException.class,
                () -> listBigDecimal.averageBigDecimal(null));

        assertThrows(UnsupportedOperationException.class,
                () -> listBigDecimalEmpty.averageBigDecimal(Function.identity()));
    }

    @Test
    void average_overflow() {
        // arrange
        List<Integer> listOverflowInt = new List<>(Integer.MAX_VALUE, 1);
        List<Integer> listUnderflowInt = new List<>(Integer.MIN_VALUE, -1);
        List<Long> listOverflowLong = new List<>(Long.MAX_VALUE, 1l);
        List<Long> listUnderflowLong = new List<>(Long.MIN_VALUE, -1l);

        // act and assert
        assertThrows(ArithmeticException.class,
                () -> listOverflowInt.averageInt(Function.identity()));

        assertThrows(ArithmeticException.class,
                () -> listUnderflowInt.averageInt(Function.identity()));

        assertThrows(ArithmeticException.class,
                () -> listOverflowLong.averageLong(Function.identity()));

        assertThrows(ArithmeticException.class,
                () -> listUnderflowLong.averageLong(Function.identity()));
    }

    @Test
    void cast_abnormal() {
        // arrange
        List<Object> list = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.cast(null));

        assertThrows(UnsupportedOperationException.class,
                () -> list.cast(Integer.class));
    }

    @Test
    void concat_abnormal() {
        // arrange
        List<Object> list = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.concat(null));
    }

    @Test
    void count_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.count(null));

        assertThrows(IllegalArgumentException.class,
                () -> list.longCount(null));
    }

    @Test
    void elementAt_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act and assert
        assertThrows(IndexOutOfBoundsException.class,
                () -> list.elementAt(-1));

        assertThrows(IndexOutOfBoundsException.class,
                () -> list.elementAt(3));
    }

    @Test
    void empty_abnormal() {
        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> IEnumerable.empty(null));
    }

    @Test
    void except_abnormal() {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> first.except(null));
    }

    @Test
    void firstOrDefault_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.firstOrDefault(null));
    }

    @Test
    void first_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);
        List<Integer> listEmpty = new List<>();

        // act and assert
        assertThrows(UnsupportedOperationException.class,
                () -> listEmpty.first());


        assertThrows(UnsupportedOperationException.class,
                () -> list.first(x -> x == 4));
    }

    @Test
    void groupBy_abnormal() {
        // arrange
        class Javascript {
            String name;
            int age;

            Javascript(String name, int age) {
                this.name = name;
                this.age = age;
            }
        }

        List<Javascript> list = new List<>(
                new Javascript("Angular", 1),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
        );

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.groupBy(null));
    }

    @Test
    void groupJoin_abnormal() {
        // arrange
        List<Integer> outer = new List<>(1, 2, 3);
        List<Integer> inner = new List<>(1, 2, 3);
        Function<Integer, Integer> outerKey = x -> x;
        Function<Integer, Integer> innerKey = y -> y;
        BiFunction<Integer, IEnumerable<Integer>, Integer> selector = (x, y) -> x;

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> outer.groupJoin(null, outerKey, innerKey, selector));

        assertThrows(IllegalArgumentException.class,
                () -> outer.groupJoin(inner, null, innerKey, selector));

        assertThrows(IllegalArgumentException.class,
                () -> outer.groupJoin(inner, outerKey, null, selector));

        assertThrows(IllegalArgumentException.class,
                () -> outer.groupJoin(inner, outerKey, innerKey, null));
    }

    @Test
    void intersect_abnormal() {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> first.intersect(null));
    }

    @Test
    void join_abnormal() {
        // arrange
        List<Integer> outer = new List<>(1, 2, 3);
        List<Integer> inner = new List<>(1, 2, 3);
        Function<Integer, Integer> outerKey = x -> x;
        Function<Integer, Integer> innerKey = y -> y;
        BiFunction<Integer, Integer, Integer> selector = (x, y) -> x + y;

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> outer.join(null, outerKey, innerKey, selector));

        assertThrows(IllegalArgumentException.class,
                () -> outer.join(inner, null, innerKey, selector));

        assertThrows(IllegalArgumentException.class,
                () -> outer.join(inner, outerKey, null, selector));

        assertThrows(IllegalArgumentException.class,
                () -> outer.join(inner, outerKey, innerKey, null));
    }

    @Test
    void last_abnormal() {
        // arrange
        List<Integer> listEmpty = new List<>();

        // act and assert
        assertThrows(UnsupportedOperationException.class,
                () -> listEmpty.last());

        assertThrows(UnsupportedOperationException.class,
                () -> listEmpty.last(x -> x == 0));

        assertThrows(IllegalArgumentException.class,
                () -> listEmpty.last(null));
    }

    @Test
    void max_abnormal() {
        // arrange
        List<Integer> listInt = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> listInt.max(null));

        assertThrows(UnsupportedOperationException.class,
                () -> listInt.max(Function.identity()));
    }

    @Test
    void min_abnormal() {
        // arrange
        List<Integer> listInt = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> listInt.min(null));

        assertThrows(UnsupportedOperationException.class,
                () -> listInt.min(Function.identity()));
    }

    @Test
    void ofType_abnormal() {
        // arrange
        List<Object> list = new List<>(1, "2", 3, "4");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.ofType(null));
    }

    @Test
    void orderByDescending_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.orderByDescending(null));
    }

    @Test
    void orderBy_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.orderBy(null));
    }

    @Test
    void range_abnormal() {
        // act and assert
        assertThrows(IndexOutOfBoundsException.class,
                () -> IEnumerable.range(0, -1));

        assertThrows(IndexOutOfBoundsException.class,
                () -> IEnumerable.range(2, Integer.MAX_VALUE));
    }

    @Test
    void repeat_abnormal() {
        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> IEnumerable.repeat(null, "React", 10));

        assertThrows(IndexOutOfBoundsException.class,
                () -> IEnumerable.repeat(String.class, "React", -1));
    }

    @Test
    void select_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.select(null));
    }

    @Test
    void sequenceEqual_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.sequenceEqual(null));
    }

    @Test
    void singleOrDefault_abnormal() {
        // arrange
        List<Integer> list = new List<>(1);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.singleOrDefault(null));
    }

    @Test
    void single_abnormal() {
        // arrange
        List<Integer> list = new List<>(1);
        List<Integer> listEmpty = new List<>();

        // act and assert
        assertThrows(UnsupportedOperationException.class,
                () -> listEmpty.single());

        assertThrows(IllegalArgumentException.class,
                () -> listEmpty.single(null));

        assertThrows(UnsupportedOperationException.class,
                () -> list.single(x -> x == 0));
    }

    @Test
    void skipWhile_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3, 4, 5);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.skipWhile(null));
    }

    @Test
    void sum_abnormal() {
        // arrange
        List<Integer> listInt = new List<>();
        List<Long> listLong = new List<>();
        List<Double> listDouble = new List<>();
        List<BigDecimal> listBigDecimal = new List<>();

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> listInt.sumInt(null));

        assertThrows(IllegalArgumentException.class,
                () -> listLong.sumLong(null));

        assertThrows(IllegalArgumentException.class,
                () -> listDouble.sumDouble(null));

        assertThrows(IllegalArgumentException.class,
                () -> listBigDecimal.sumBigDecimal(null));
    }

    @Test
    void sum_overflow() {
        // arrange
        List<Integer> listOverflowInt = new List<>(Integer.MAX_VALUE, 1);
        List<Integer> listUnderflowInt = new List<>(Integer.MIN_VALUE, -1);
        List<Long> listOverflowLong = new List<>(Long.MAX_VALUE, 1l);
        List<Long> listUnderflowLong = new List<>(Long.MIN_VALUE, -1l);

        // act and assert
        assertThrows(ArithmeticException.class,
                () -> listOverflowInt.sumInt(Function.identity()));

        assertThrows(ArithmeticException.class,
                () -> listUnderflowInt.sumInt(Function.identity()));

        assertThrows(ArithmeticException.class,
                () -> listOverflowLong.sumLong(Function.identity()));

        assertThrows(ArithmeticException.class,
                () -> listUnderflowLong.sumLong(Function.identity()));
    }

    @Test
    void takeWhile_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3, 4, 5);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.takeWhile(null));
    }

    @Test
    void thenByDescending_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.thenByDescending(null));
    }

    @Test
    void thenBy_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.thenBy(null));
    }

    @Test
    void toArray_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");
        Class<String> clazz = null;

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.toArray(clazz));
    }

    @Test
    void toDictionary_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.toDictionary(null));
    }

    @Test
    void toDictionarySelect_abnormal() {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.toDictionary(x -> x.contains("c"), null));
    }

    @Test
    void union_abnormal() {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> first.union(null));
    }

    @Test
    void where_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);
        Predicate<Integer> predicate = null;

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.where(predicate));
    }

    @Test
    void where_index_abnormal() {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);
        BiPredicate<Integer, Integer> predicate = null;

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> list.where(predicate));
    }

    @Test
    void zip_abnormal() {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);
        List<String> second = new List<>("Angular", "React", "Backbone");
        BiFunction<Integer, String, String> resultSelector = (x, y) -> String.format("%d, %s", x, y);

        // act and assert
        assertThrows(IllegalArgumentException.class,
                () -> first.zip(null, resultSelector));

        assertThrows(IllegalArgumentException.class,
                () -> first.zip(second, null));
    }
}