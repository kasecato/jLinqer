package com.github.jlinqer.collections;

import com.github.jlinqer.linq.IEnumerable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Keisuke Kato
 */
class ListTest {
// -------------------------- OTHER METHODS --------------------------

    @Test
    void constructorIEnumerable() {
        // arrange
        IEnumerable<String> list = new List<>("ES7", "ES7", "ES2016").where(x -> x.startsWith("ES"));

        // act
        List<String> actual = new List<>(list);

        // assert
        assertEquals(3, actual.size());
        assertEquals("ES7", actual.elementAt(0));
        assertEquals("ES7", actual.elementAt(1));
        assertEquals("ES2016", actual.elementAt(2));
    }

    @Test
    void constructorItems() {
        // act
        List<String> actual = new List<>("ES2015", "ES2015");

        // assert
        assertEquals(2, actual.size());
        assertEquals("ES2015", actual.first());
    }

    @Test
    void functions() {
        // arrange
        List<String> list = new List<>("ES2015", "ES2015", "ES6", "ES2016");

        // act
        boolean equals = list.equals(list);
        int hashCode = list.hashCode();
        int size = list.size();
        boolean isEmpty = list.isEmpty();
        boolean contains = list.contains("ES2015");
        Object[] toArray = list.toArray();
        String[] toArrayT = list.toArray(new String[]{"ES2015"});
        boolean remove = list.remove("ES6");
        boolean containsAll = list.containsAll(list);
        boolean addAll = list.addAll(list);
        boolean retainAll = list.retainAll(list);
        boolean removeAll = list.removeAll(list);
        list.clear();

        boolean addAllC = list.addAll(0, Arrays.asList("ES7", "ES2015", "ES6"));
        String set = list.set(2, "ES2015");
        list.add(3, "ES2016");
        String removeI = list.remove(3);
        int indexOf = list.indexOf("ES2015");
        int lastIndexOf = list.lastIndexOf("ES2015");
        ListIterator<String> listIterator = list.listIterator(1);
        java.util.List<String> subList = list.subList(1, 2);

        // assert
        assertEquals(true, equals);
        assertEquals(1192786890, hashCode);
        assertEquals(4, size);
        assertEquals(false, isEmpty);
        assertEquals(true, contains);
        assertEquals(4, toArray.length);
        assertEquals(4, toArrayT.length);
        assertEquals(true, remove);
        assertEquals(true, containsAll);
        assertEquals(true, addAll);
        assertEquals(false, retainAll);
        assertEquals(true, removeAll);

        assertEquals(true, addAllC);
        assertEquals("ES6", set);
        assertEquals("ES2016", removeI);
        assertEquals(1, indexOf);
        assertEquals(2, lastIndexOf);
        assertEquals(true, listIterator.hasNext());
        assertEquals(1, subList.size());
    }
}