package com.github.jlinqer.collections;

import com.github.jlinqer.linq.IEnumerable;

import java.util.*;

/**
 * Created by Reuben Kuhnert
 * Modified by Keisuke Kato
 */
public class List<TSource> implements IEnumerable<TSource>, java.util.List<TSource> {
// ------------------------------ FIELDS ------------------------------

    private java.util.List<TSource> list = new ArrayList<>();

// --------------------------- CONSTRUCTORS ---------------------------

    public List() {
    }

    @SafeVarargs
    public List(TSource... items) {
        Collections.addAll(this, items);
    }

    public List(IEnumerable<TSource> items) {
        for (TSource item : items) this.add(item);
    }

    public List(java.util.List<TSource> list) {
        this.list = list;
    }

    @Override
    public boolean add(final TSource t) {
        return list.add(t);
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
