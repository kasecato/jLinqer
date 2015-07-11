package com.github.jlinqer.collections;

import com.github.jlinqer.linq.IEnumerable;

import java.util.*;

/**
 * Created by Keisuke Kato
 */
public class Dictionary<TKey, TSource> extends java.util.Dictionary<TKey, TSource> implements IEnumerable<Map.Entry<TKey, TSource>> {
// ------------------------------ FIELDS ------------------------------

    private java.util.Dictionary<TKey, TSource> dictionary = new Hashtable<>();

// --------------------------- CONSTRUCTORS ---------------------------

    public Dictionary() {
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Iterable ---------------------

    @Override
    public Iterator<Map.Entry<TKey, TSource>> iterator() {
        Map<TKey, TSource> allItems = new HashMap<>();
        for (Enumeration<TKey> e = dictionary.keys(); e.hasMoreElements(); ) {
            TKey key = e.nextElement();
            allItems.put(key, dictionary.get(key));
        }
        return allItems.entrySet().iterator();
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public Enumeration<TSource> elements() {
        return dictionary.elements();
    }

    @Override
    public TSource get(Object key) {
        return dictionary.get(key);
    }

    @Override
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    @Override
    public Enumeration<TKey> keys() {
        return dictionary.keys();
    }

    @Override
    public TSource put(TKey key, TSource value) {
        return dictionary.put(key, value);
    }

    @Override
    public TSource remove(Object key) {
        return dictionary.remove(key);
    }

    @Override
    public int size() {
        return dictionary.size();
    }
}
