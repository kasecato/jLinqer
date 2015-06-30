package com.jlinqer;

import com.jlinqer.collections.List;
import com.jlinqer.linq.IEnumerable;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by Keisuke Kato
 */
public class IEnumerableAbnomalTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void aggregate_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>();

        // act and assert
        try {
            list.aggregate(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }

        try {
            list.aggregate((sum, elem) -> sum + elem);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void all_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.all(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void any_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.any(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void average_abnormal() throws Exception {
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
        try {
            listInt.averageInt(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            listIntEmpty.averageInt(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            listLong.averageLong(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            listLongEmpty.averageLong(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            listDouble.averageDouble(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            listDoubleEmpty.averageDouble(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            listBigDecimal.averageBigDecimal(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            listBigDecimalEmpty.averageBigDecimal(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void average_overflow() throws Exception {
        // arrange
        List<Integer> listOverflowInt = new List<>(Integer.MAX_VALUE, 1);
        List<Integer> listUnderflowInt = new List<>(Integer.MIN_VALUE, -1);
        List<Long> listOverflowLong = new List<>(Long.MAX_VALUE, 1l);
        List<Long> listUnderflowLong = new List<>(Long.MIN_VALUE, -1l);

        // act and assert
        try {
            listOverflowInt.averageInt(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }
        try {
            listUnderflowInt.averageInt(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }

        try {
            listOverflowLong.averageLong(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }
        try {
            listUnderflowLong.averageLong(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }
    }

    @Test
    public void cast_abnormal() throws Exception {
        // arrange
        List<Object> list = new List<>();

        // act and assert
        try {
            list.cast(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            list.cast(Integer.class);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void concat_abnormal() throws Exception {
        // arrange
        List<Object> list = new List<>();

        // act and assert
        try {
            list.concat(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void count_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.count(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }

        try {
            list.longCount(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void elementAt_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act and assert
        try {
            list.elementAt(-1);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }

        try {
            list.elementAt(3);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test
    public void empty_abnormal() throws Exception {
        // act and assert
        try {
            IEnumerable.empty(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void except_abnormal() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);

        // act and assert
        try {
            first.except(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void firstOrDefault_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.firstOrDefault(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void first_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);
        List<Integer> listEmpty = new List<>();

        // act and assert
        try {
            listEmpty.first();
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            list.first(x -> x == 4);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void groupBy_abnormal() throws Exception {
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
        try {
            list.groupBy(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void intersect_abnormal() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);

        // act and assert
        try {
            first.intersect(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void last_abnormal() throws Exception {
        // arrange
        List<Integer> listEmpty = new List<>();

        // act and assert
        try {
            listEmpty.last();
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            listEmpty.last(x -> x == 0);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            listEmpty.last(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void max_abnormal() throws Exception {
        // arrange
        List<Integer> listInt = new List<>();

        // act and assert
        try {
            listInt.max(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            listInt.max(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void min_abnormal() throws Exception {
        // arrange
        List<Integer> listInt = new List<>();

        // act and assert
        try {
            listInt.min(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            listInt.min(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void ofType_abnormal() throws Exception {
        // arrange
        List<Object> list = new List<>(1, "2", 3, "4");

        // act and assert
        try {
            list.ofType(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void orderByDescending_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.orderByDescending(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void orderBy_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.orderBy(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void range_abnormal() throws Exception {
        // act and assert
        try {
            IEnumerable.range(0, -1);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }

        try {
            IEnumerable.range(2, Integer.MAX_VALUE);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test
    public void repeat_abnormal() throws Exception {
        // act and assert
        try {
            IEnumerable.repeat(null, "React", 10);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
        try {
            IEnumerable.repeat(String.class, "React", -1);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IndexOutOfBoundsException.class));
        }
    }

    @Test
    public void select_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act and assert
        try {
            list.select(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void sequenceEqual_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.sequenceEqual(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void singleOrDefault_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1);

        // act and assert
        try {
            list.singleOrDefault(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void single_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1);
        List<Integer> listEmpty = new List<>();

        // act and assert
        try {
            listEmpty.single();
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }

        try {
            listEmpty.single(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }

        try {
            list.single(x -> x == 0);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(UnsupportedOperationException.class));
        }
    }

    @Test
    public void skipWhile_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3, 4, 5);

        // act and assert
        try {
            list.skipWhile(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void sum_abnormal() throws Exception {
        // arrange
        List<Integer> listInt = new List<>();
        List<Long> listLong = new List<>();
        List<Double> listDouble = new List<>();
        List<BigDecimal> listBigDecimal = new List<>();

        // act and assert
        try {
            listInt.sumInt(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }

        try {
            listLong.sumLong(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }

        try {
            listDouble.sumDouble(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }

        try {
            listBigDecimal.sumBigDecimal(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void sum_overflow() throws Exception {
        // arrange
        List<Integer> listOverflowInt = new List<>(Integer.MAX_VALUE, 1);
        List<Integer> listUnderflowInt = new List<>(Integer.MIN_VALUE, -1);
        List<Long> listOverflowLong = new List<>(Long.MAX_VALUE, 1l);
        List<Long> listUnderflowLong = new List<>(Long.MIN_VALUE, -1l);

        // act and assert
        try {
            listOverflowInt.sumInt(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }
        try {
            listUnderflowInt.sumInt(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }

        try {
            listOverflowLong.sumLong(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }
        try {
            listUnderflowLong.sumLong(x -> x);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(ArithmeticException.class));
        }
    }

    @Test
    public void takeWhile_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3, 4, 5);

        // act and assert
        try {
            list.takeWhile(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void thenByDescending_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.thenByDescending(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void thenBy_abnormal() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act and assert
        try {
            list.thenBy(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void union_abnormal() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);

        // act and assert
        try {
            first.union(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }

    @Test
    public void where_abnormal() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act and assert
        try {
            list.where(null);
            fail();
        } catch (Exception e) {
            assertThat(e, instanceOf(IllegalArgumentException.class));
        }
    }
}