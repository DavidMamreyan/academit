package ru.academits.mamreyan.mylist;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyList<T> implements List<T> {
    private T[] items;
    private int size;
    private final int DEFAULT_CAPACITY = 10;

    public MyList() {
        items = (T[]) new Object[]{};
        size = 0;
    }

    public MyList(int initialCapacity) {
        if (initialCapacity > 0) {
            items = (T[]) new Object[Math.max(DEFAULT_CAPACITY, initialCapacity)];
            size = 0;
        } else if (initialCapacity == 0) {
            items = (T[]) new Object[]{};
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public MyList(List<T> list) {
        items = (T[]) list.toArray();
        size = list.size();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    public int indexOfRange(Object o, int start, int end) {
        if (o == null) {
            for (int i = start; i < end; i++) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
    }

    public int lastIndexOfRange(Object o, int start, int end) {
        if (o == null) {
            for (int i = end - 1; i >= start; i--) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = end - 1; i >= start; i--) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<T> {
        int cursor;
        int lastRet = -1;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }

            if (cursor >= items.length) {
                throw new ConcurrentModificationException();
            }

            int i = cursor;

            cursor++;
            return (T) items[lastRet = i];
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            try {
                MyList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public void add(int index, T element) {

    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Unsupported Operation Exception");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Unsupported Operation Exception");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Unsupported Operation Exception");
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > items.length) {
            if (isEmpty()) {
                items = (T[]) new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
            } else {
                items = Arrays.copyOf(items, minCapacity);
            }
        }
    }

    public void trimToSize() {
        if (size < items.length) {
            items = (size == 0)
                    ?
                    items = (T[]) new Object[DEFAULT_CAPACITY]
                    : Arrays.copyOf(items, size);
        }
    }
}