package com.jlinqer;

import com.jlinqer.util.List;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Keisuke Kato
 */
public class ListTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void aggregate() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        int actual = list.aggregate((sum, elem) -> sum + elem);

        // assert
        assertEquals(6, actual);
    }

    @Test
    public void all() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act
        boolean actual = list.all(x -> x.equals("Angular") || x.equals("Backbone") || x.equals("React"));
        boolean actualNotFound = list.all(x -> x.equals("Angular") || x.equals("React"));

        // assert
        assertEquals(true, actual);
        assertEquals(false, actualNotFound);
    }

    @Test
    public void any() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");
        List<String> listEmpty = new List<>();

        // act
        boolean actual = list.any(x -> x.equals("Angular"));
        boolean actualNotFound = list.any(x -> x.equals("jquery"));
        boolean actualNotEmpty = list.any();
        boolean actualEmpty = listEmpty.any();

        // assert
        assertEquals(true, actual);
        assertEquals(false, actualNotFound);
        assertEquals(true, actualNotEmpty);
        assertEquals(false, actualEmpty);
    }

    @Test
    public void average() throws Exception {
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

        // act
        double actualInt = listInt.averageInt(x -> x);
        double actualLong = listLong.averageLong(x -> x);
        double actualDouble = listDouble.averageDouble(x -> x);
        BigDecimal actualBigDecimal = listBigDecimal.averageBigDecimal(x -> x);

        // assert
        assertEquals(2.5d, actualInt, 0);
        assertEquals(2.5d, actualLong, 0);
        assertEquals(2.5d, actualDouble, 0);
        assertEquals(2.5d, actualBigDecimal.doubleValue(), 0);
    }

    @Test
    public void cast() throws Exception {
        // arrange
        List<Object> list = new List<>(1, 2, 3);

        // act
        List<Integer> actual = list.cast(Integer.class);

        // assert
        assertEquals(1, actual.get(0).intValue());
        assertEquals(2, actual.get(1).intValue());
        assertEquals(3, actual.get(2).intValue());
    }

    @Test
    public void concat() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);
        List<Integer> listSecond = new List<>(4, 5, 6);

        // act
        List<Integer> actual = list.concat(listSecond);

        // assert
        assertEquals(6, actual.count());
    }

    @Test
    public void count() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act
        long actual = list.count();
        long actualOne = list.count(x -> x.equals("React"));
        long actualZero = list.count(x -> x.equals("jquery"));

        // assert
        assertEquals(3, actual);
        assertEquals(1, actualOne);
        assertEquals(0, actualZero);
    }

    @Test
    public void defaultIfEmpty() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");
        List<String> listEmpty = new List<>();

        // act
        List<String> actual = list.defaultIfEmpty();
        List<String> actualEmpty = listEmpty.defaultIfEmpty();
        List<String> actualDefault = listEmpty.defaultIfEmpty("ES7");
        List<String> actualDefaultNull = listEmpty.defaultIfEmpty(null);

        // assert
        assertEquals(3, actual.size());
        assertEquals(1, actualEmpty.size());
        assertEquals("ES7", actualDefault.get(0));
        assertEquals(1, actualDefaultNull.size());
    }

    @Test
    public void distinct() throws Exception {
        // arrange
        List<Integer> list =
                new List<>(
                        1, 2, 3,
                        1, 2, 3, 4
                );

        // act
        List<Integer> actual = list.distinct();

        // assert
        assertEquals(4, actual.count());
    }

    @Test
    public void elementAt() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        int actual = list.elementAt(2);

        // assert
        assertEquals(3, actual);
    }

    @Test
    public void elementAtOrDefault() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        int actual = list.elementAtOrDefault(2);
        Integer actualDefault = list.elementAtOrDefault(3);

        // assert
        assertEquals(3, actual);
        assertEquals(null, actualDefault);
    }

    @Test
    public void empty() throws Exception {
        // act
        List<Double> actual = List.empty(Double.class);

        // assert
        assertEquals(0, actual.count());
    }

    @Test
    public void except() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);
        List<Integer> second = new List<>(1, 3);

        // act
        List<Integer> actual = first.except(second);

        // assert
        assertEquals(1, actual.size());
        assertEquals(2, actual.get(0).intValue());
    }

    @Test
    public void first() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        int actual = list.first();

        // assert
        assertEquals(1, actual);
    }

    @Test
    public void firstOrDefault() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");
        List<String> listEmpty = new List<>();

        // act
        String actualEmpty = listEmpty.firstOrDefault();
        String actualFirst = list.firstOrDefault();
        String actualMatch = list.firstOrDefault(x -> x.equals("Angular"));
        String actualUnMatch = list.firstOrDefault(x -> x.equals("jquery"));

        // assert
        assertEquals(null, actualEmpty);
        assertEquals("Backbone", actualFirst);
        assertEquals("Angular", actualMatch);
        assertEquals(null, actualUnMatch);
    }

    @Test
    public void groupBy() throws Exception {
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

        // act
        Map<Integer, List<Javascript>> actual = list.groupBy(x -> x.age);


        // assert
        assertEquals(true, actual.get(1).any(x -> x.name.equals("Angular")));
        assertEquals(true, actual.get(1).any(x -> x.name.equals("React")));
        assertEquals(false, actual.get(1).any(x -> x.name.equals("Backbone")));
        assertEquals(null, actual.get(2));
        assertEquals(true, actual.get(5).any(x -> x.name.equals("Backbone")));
    }

    @Test
    public void intersect() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);
        List<Integer> second = new List<>(1, 3);

        // act
        List<Integer> actual = first.intersect(second);

        // assert
        assertEquals(2, actual.size());
        assertEquals(1, actual.get(0).intValue());
        assertEquals(3, actual.get(1).intValue());
    }

    @Test
    public void last() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        int actual = list.last();
        int actualOne = list.last(x -> x == 1);

        // assert
        assertEquals(3, actual);
        assertEquals(1, actualOne);
    }

    @Test
    public void lastOrDefault() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);
        List<Integer> listEmpty = new List<>();

        // act
        int actual = list.lastOrDefault();
        int actualOne = list.lastOrDefault(x -> x == 1);

        Integer actualDefaultEmpty = listEmpty.lastOrDefault();
        Integer actualDefaultNone = listEmpty.lastOrDefault(x -> x == 0);

        // assert
        assertEquals(3, actual);
        assertEquals(1, actualOne);
        assertEquals(null, actualDefaultEmpty);
        assertEquals(null, actualDefaultNone);
    }

    @Test
    public void max() throws Exception {
        // arrange
        List<Integer> listInt = new List<>(1, 2, 3);
        List<Long> listLong = new List<>(1l, 2l, 3l);
        List<Double> listDouble = new List<>(1d, 2d, 3d);
        List<BigDecimal> listBigDecimal = new List<>(
                new BigDecimal(1d),
                new BigDecimal(2d),
                new BigDecimal(3d)
        );

        // act
        double actualInt = listInt.maxInt(x -> x);
        double actualLong = listLong.maxLong(x -> x);
        double actualDouble = listDouble.maxDouble(x -> x);
        BigDecimal actualBigDecimal = listBigDecimal.maxBigDecimal(x -> x);

        // assert
        assertEquals(3, actualInt, 0);
        assertEquals(3l, actualLong, 0);
        assertEquals(3d, actualDouble, 0);
        assertEquals(3d, actualBigDecimal.doubleValue(), 0);
    }

    @Test
    public void min() throws Exception {
        // arrange
        List<Integer> listInt = new List<>(1, 2, 3);
        List<Long> listLong = new List<>(1l, 2l, 3l);
        List<Double> listDouble = new List<>(1d, 2d, 3d);
        List<BigDecimal> listBigDecimal = new List<>(
                new BigDecimal(1d),
                new BigDecimal(2d),
                new BigDecimal(3d)
        );

        // act
        double actualInt = listInt.minInt(x -> x);
        double actualLong = listLong.minLong(x -> x);
        double actualDouble = listDouble.minDouble(x -> x);
        BigDecimal actualBigDecimal = listBigDecimal.minBigDecimal(x -> x);

        // assert
        assertEquals(1, actualInt, 0);
        assertEquals(1l, actualLong, 0);
        assertEquals(1d, actualDouble, 0);
        assertEquals(1d, actualBigDecimal.doubleValue(), 0);
    }

    @Test
    public void ofType() throws Exception {
        // arrange
        List<Object> list = new List<>(1, "2", 3, "4");

        // act
        List<String> actualStr = list.ofType(String.class);
        List<Integer> actualInt = list.ofType(Integer.class);
        List<StringBuffer> actualZero = list.ofType(StringBuffer.class);

        // assert
        assertEquals("2", actualStr.get(0));
        assertEquals("4", actualStr.get(1));
        assertEquals(1, actualInt.get(0).intValue());
        assertEquals(3, actualInt.get(1).intValue());
        assertEquals(0, actualZero.count());
    }

    @Test
    public void orderBy() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act
        List<String> actual = list.orderBy(x -> x);

        // assert
        assertEquals("Angular", actual.get(0));
        assertEquals("Backbone", actual.get(1));
        assertEquals("React", actual.get(2));
    }

    @Test
    public void orderByDescending() throws Exception {
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
                new Javascript("Angular", 3),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
        );

        // act
        List<Javascript> actual = list.orderByDescending(x -> x.age);

        // assert
        assertEquals(5, actual.get(0).age);
        assertEquals(3, actual.get(1).age);
        assertEquals(1, actual.get(2).age);
    }

    @Test
    public void range() throws Exception {
        // act
        List<Integer> actual = List.range(-2, 3);

        // assert
        assertEquals(3, actual.count());
        assertEquals(-2, actual.get(0).intValue());
        assertEquals(0, actual.get(2).intValue());
    }

    @Test
    public void repeat() throws Exception {
        // act
        List<String> actual = List.repeat(String.class, "circle", 10);

        // assert
        assertEquals(10, actual.count());
        assertEquals("circle", actual.get(9));
    }

    @Test
    public void reverse() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        List<Integer> actual = list.reverse();

        // assert
        assertEquals(3, actual.get(0).intValue());
        assertEquals(2, actual.get(1).intValue());
        assertEquals(1, actual.get(2).intValue());

        assertEquals(1, list.get(0).intValue());
        assertEquals(2, list.get(1).intValue());
        assertEquals(3, list.get(2).intValue());
    }

    @Test
    public void select() throws Exception {
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
                new Javascript("Angular", 3),
                new Javascript("React", 1),
                new Javascript("Backbone", 5)
        );

        // act
        List<Integer> actual = list.select(x -> x.age);

        // assert
        assertEquals(3, actual.get(0).intValue());
        assertEquals(1, actual.get(1).intValue());
        assertEquals(5, actual.get(2).intValue());
    }

    @Test
    public void selectMany() throws Exception {
        // arrange
        class Javascript {
            String name;
            int age;
            java.util.List ver;

            Javascript(String name, int age, java.util.List<String> ver) {
                this.name = name;
                this.age = age;
                this.ver = ver;
            }
        }

        List<Javascript> list = new List<>(
                new Javascript("Angular", 3, Arrays.asList("1.0.1", "1.0.2")),
                new Javascript("React", 1, Arrays.asList("2.0.1", "2.0.2")),
                new Javascript("Backbone", 1, Arrays.asList("3.0.1", "3.0.2"))
        );

        // act
        List<String> actual = list.selectMany(x -> x.ver.stream());

        // assert
        assertEquals(6, actual.count());
        assertEquals("1.0.1", actual.get(0));
        assertEquals("1.0.2", actual.get(1));
        assertEquals("2.0.1", actual.get(2));
        assertEquals("2.0.2", actual.get(3));
        assertEquals("3.0.1", actual.get(4));
        assertEquals("3.0.2", actual.get(5));
    }

    @Test
    public void sequenceEqual() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);
        List<Integer> secondMatch = new List<>(1, 2, 3);
        List<Integer> secondUnMatchElem = new List<>(1, 2, 4);
        List<Integer> secondUnMatchCount = new List<>(1, 2, 3, 4);

        // act
        boolean actualMatch = first.sequenceEqual(secondMatch);
        boolean actualUnMatchElm = first.sequenceEqual(secondUnMatchElem);
        boolean actualUnMatchCount = first.sequenceEqual(secondUnMatchCount);

        // assert
        assertEquals(true, actualMatch);
        assertEquals(false, actualUnMatchElm);
        assertEquals(false, actualUnMatchCount);
    }

    @Test
    public void single() throws Exception {
        // arrange
        List<Integer> list = new List<>(1);
        List<Integer> listMany = new List<>(1, 2, 3);

        // act
        int actual = list.single();
        int actualFilter = listMany.single(x -> x == 3);

        // assert
        assertEquals(1, actual);
        assertEquals(3, actualFilter);
    }

    @Test
    public void singleOrDefault() throws Exception {
        // arrange
        List<Integer> list = new List<>(1);
        List<Integer> listMany = new List<>(1, 2, 3);
        List<Integer> listEmpty = new List<>();

        // act
        int actual = list.singleOrDefault();
        int actualFilter = listMany.singleOrDefault(x -> x == 3);
        Integer actualEmpty = listEmpty.singleOrDefault();
        Integer actualUnMatch = listEmpty.singleOrDefault(x -> x == 0);

        // assert
        assertEquals(1, actual);
        assertEquals(3, actualFilter);
        assertEquals(null, actualEmpty);
        assertEquals(null, actualUnMatch);
    }

    @Test
    public void skip() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        List<Integer> actual = list.skip(2);
        List<Integer> actualOver = list.skip(3);

        // assert
        assertEquals(3, actual.get(0).intValue());
        assertEquals(0, actualOver.count());
    }

    @Test
    public void skipWhile() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3, 4, 5);

        // act
        List<Integer> actual = list.skipWhile(x -> x <= 3);
        List<Integer> actualUnMatch = list.skipWhile(x -> x <= 5);

        // assert
        assertEquals(4, actual.get(0).intValue());
        assertEquals(0, actualUnMatch.count());
    }

    @Test
    public void sum() throws Exception {
        // arrange
        List<Integer> listInt = new List<>(1, 2, 3);
        List<Long> listLong = new List<>(1l, 2l, 3l);
        List<Double> listDouble = new List<>(1.1d, 2.2d, 3.3d);
        List<BigDecimal> listBigDecimal = new List<>(
                new BigDecimal(1.1),
                new BigDecimal(2.2),
                new BigDecimal(3.3));

        // act
        int actualInt = listInt.sumInt(x -> x);
        long actualLong = listLong.sumLong(x -> x);
        double actualDouble = listDouble.sumDouble(x -> x);
        BigDecimal actualBigDecimal = listBigDecimal.sumBigDecimal(x -> x);

        // assert
        assertEquals(6, actualInt);
        assertEquals(6l, actualLong);
        assertEquals(6.6d, actualDouble, 0d);
        assertEquals(new BigDecimal(6.6).doubleValue(), actualBigDecimal.doubleValue(), 0d);
    }

    @Test
    public void take() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act
        List<String> actual = list.take(2);
        List<String> actualOver = list.take(4);

        // assert
        assertEquals(2, actual.size());
        assertEquals("Backbone", actual.get(0));
        assertEquals("Angular", actual.get(1));

        assertEquals(3, actualOver.size());
        assertEquals("React", actualOver.get(2));
    }

    @Test
    public void takeWhile() throws Exception {
        // arrange
        List<String> list = new List<>("Backbone", "Angular", "React");

        // act
        List<String> actual = list.takeWhile(x -> x.equals("Backbone") || x.equals("Angular"));

        // assert
        assertEquals(2, actual.size());
        assertEquals("Backbone", actual.get(0));
        assertEquals("Angular", actual.get(1));
    }

    @Test
    public void union() throws Exception {
        // arrange
        List<Integer> first = new List<>(1, 2, 3);
        List<Integer> second = new List<>(0, 1, 3, 4);

        // act
        List<Integer> actual = first.union(second);

        // assert
        assertEquals(5, actual.size());
        assertEquals(1, actual.get(0).intValue());
        assertEquals(2, actual.get(1).intValue());
        assertEquals(3, actual.get(2).intValue());
        assertEquals(0, actual.get(3).intValue());
        assertEquals(4, actual.get(4).intValue());
    }

    @Test
    public void where() throws Exception {
        // arrange
        List<Integer> list = new List<>(1, 2, 3);

        // act
        List<Integer> actual = list.where(x -> x == 1 || x == 3);

        // assert
        assertEquals(true, actual.contains(1));
        assertEquals(false, actual.contains(2));
        assertEquals(true, actual.contains(3));
    }
}