package com.github.jlinqer.collections;

import com.github.jlinqer.linq.IEnumerable;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Keisuke Kato
 */
public class SetTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    public void constructorIEnumerable() throws Exception {
        // arrange
        IEnumerable<String> list = new List<>("ES7", "ES7", "ES2016").where(x -> x.startsWith("ES"));

        // act
        Set<String> actual = new Set<>(list);

        // assert
        assertEquals(2, actual.size());
        assertEquals("ES7", actual.elementAt(0));
        assertEquals("ES2016", actual.elementAt(1));
    }

    @Test
    public void constructorItems() throws Exception {
        // act
        Set<String> actual = new Set<>("ES2015", "ES2015");

        // assert
        assertEquals(1, actual.size());
        assertEquals("ES2015", actual.first());
    }

    @Test
    public void functions() throws Exception {
        // arrange
        Set<String> set = new Set<>("ES2015", "ES2015", "ES6", "ES2016");

        // act
        boolean equals = set.equals(set);
        int hashCode = set.hashCode();
        int size = set.size();
        boolean isEmpty = set.isEmpty();
        boolean contains = set.contains("ES2015");
        Object[] toArray = set.toArray();
        String[] toArrayT = set.toArray(new String[]{"ES2015"});
        boolean remove = set.remove("ES6");
        boolean containsAll = set.containsAll(set);
        boolean addAll = set.addAll(set);
        boolean retainAll = set.retainAll(set);
        boolean removeAll = set.removeAll(set);
        set.clear();

        // assert
        assertEquals(true, equals);
        assertEquals(-187696535, hashCode);
        assertEquals(3, size);
        assertEquals(false, isEmpty);
        assertEquals(true, contains);
        assertEquals(3, toArray.length);
        assertEquals(3, toArrayT.length);
        assertEquals(true, remove);
        assertEquals(true, containsAll);
        assertEquals(false, addAll);
        assertEquals(false, retainAll);
        assertEquals(true, removeAll);
    }
}