package ru.academits.mamreyan.my_array_list;

import java.util.*;

public class MyArrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private T[] items;
    private int size;
    private int modCount;

    public MyArrayList() {
        //noinspection unchecked
        items = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Capacity must be >= 0, capacity = " + initialCapacity);
        }

        //noinspection unchecked
        items = (T[]) new Object[initialCapacity];
    }

    public MyArrayList(List<T> list) {
        //noinspection unchecked
        items = (T[]) list.toArray();
        size = list.size();
    }

    public void ensureCapacity(int minCapacity) {
        if (minCapacity > items.length) {
            if (size == 0) {
                //noinspection unchecked
                items = (T[]) new Object[minCapacity];
            } else {
                items = Arrays.copyOf(items, minCapacity);
            }
        }
    }

    public void ensureFreeSpace(int minFreeSpace) {
        if (minFreeSpace > items.length - size) {
            int newLength = size + minFreeSpace;

            if (size == 0) {
                //noinspection unchecked
                items = (T[]) new Object[newLength];
            } else {
                items = Arrays.copyOf(items, newLength);
            }
        }
    }

    public void trimToSize() {
        if (size < items.length) {
            if (size == 0) {
                //noinspection unchecked
                items = (T[]) new Object[]{};
            } else {
                items = Arrays.copyOf(items, size);
            }
        }
    }

    public void increaseCapacity() {
        if (size == 0) {
            //noinspection unchecked
            items = (T[]) new Object[Math.max(items.length * 2, DEFAULT_CAPACITY)];
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
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
    public boolean contains(Object object) {
        return indexOf(object) >= 0;
    }

    @Override
    public int indexOf(Object object) {
        return getIndexInRange(object, 0, size);
    }

    public int getIndexInRange(Object object, int start, int end) {
        for (int i = start; i < end; i++) {
            if (Objects.equals(items[i], object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        return getLastIndexInRange(object, 0, size);
    }

    public int getLastIndexInRange(Object object, int start, int end) {
        for (int i = end - 1; i >= start; i--) {
            if (Objects.equals(items[i], object)) {
                return i;
            }
        }

        return -1;
    }

    public MyArrayList<Integer> getAllIndicesOf(Object object) { // возвращает массив всех индексов, элементы по которым равны переданному объекту
        MyArrayList<Integer> indicesList = new MyArrayList<>();

        int index = indexOf(object);

        while (index != -1) {
            indicesList.add(index);
            index = getIndexInRange(object, index + 1, size);
        }

        return indicesList;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        private int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            checkForComodification();

            if (!hasNext()) {
                throw new NoSuchElementException("Index = " + currentIndex + 1 + ", size = " + size);
            }

            currentIndex++;
            return items[currentIndex];
        }

        @Override
        public void remove() {
            if (currentIndex == -1) {
                throw new IllegalStateException("Index = -1");
            }

            checkForComodification();

            MyArrayList.this.remove(currentIndex);
            expectedModCount = modCount;
            currentIndex--;
        }

        private void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException("The list was modified");
            }
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] array) {
        if (array.length < size) {
            //noinspection unchecked
            return (T1[]) Arrays.copyOf(items, size, array.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public boolean add(T item) {
        add(size, item);

        return true;
    }

    @Override
    public void add(int index, T item) {
        if (items.length == size) {
            increaseCapacity();
        }

        if (index != size) {
            checkIndex(index);

            System.arraycopy(items, index, items, index + 1, size - index);
        }

        items[index] = item;

        size++;
        modCount++;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);

        if (index == -1) {
            return false;
        }

        remove(index);

        return true;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);

        T removedItem = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);

        size--;
        modCount++;

        return removedItem;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object item : collection) {
            if (!contains(item)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        return addAll(size, collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> collection) {
        if (index != size) {
            checkIndex(index);
        }

        int additionSize = collection.size();

        if (additionSize == 0) {
            return false;
        }

        ensureCapacity(size + additionSize);

        // Нужно обойтись без преобразования коллекции в массив или список
        if (index < size) {
            System.arraycopy(items, index, items, index + additionSize, size - index);
        }

        for (T t : collection) {
            add(index, t);
            index++;
        }

        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isChanged = false;

        for (int i = 0; i < size; i++) {
            if (collection.contains(items[i])) {
                remove(i);
                isChanged = true;
                i--;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isChanged = false;

        for (int i = 0; i < size; i++) {
            if (!collection.contains(items[i])) {
                remove(i);
                isChanged = true;
                i--;
            }
        }

        return isChanged;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
        modCount++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public T set(int index, T item) {
        checkIndex(index);

        T removedItem = items[index];
        items[index] = item;

        return removedItem;
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

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index must be 0 <= index < " + size + ", index = " + index);
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (T item : this) {
            stringBuilder.append(item).append(", ");
        }

        int length = stringBuilder.length();
        stringBuilder.replace(length - 2, length, "]");

        return stringBuilder.toString();
    }
}