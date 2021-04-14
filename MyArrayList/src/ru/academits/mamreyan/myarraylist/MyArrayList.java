package ru.academits.mamreyan.myarraylist;

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
        } else {
            //noinspection unchecked
            items = (T[]) new Object[initialCapacity];
        }
    }

    public MyArrayList(List<T> list) {
        //noinspection unchecked
        items = (T[]) list.toArray();
        size = list.size();
    }

    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > items.length) {
            if (size == 0) {
                //noinspection unchecked
                items = (T[]) new Object[minimumCapacity];
            } else {
                items = Arrays.copyOf(items, minimumCapacity);
            }
        }
    }

    public void ensureFreeSpace(int minimumFreeSpace) {
        if (minimumFreeSpace > items.length - size) {
            int newLength = size + minimumFreeSpace;

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
                items = (T[]) new Object[DEFAULT_CAPACITY];
            } else {
                items = Arrays.copyOf(items, size);
            }
        }
    }

    public void increaseCapacity() {
        if (size == 0) {
            //noinspection unchecked
            items = (T[]) new Object[items.length * 2];
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
        return getIndexOfRange(object, 0, size);
    }

    public int getIndexOfRange(Object object, int start, int end) {
        for (int i = start; i < end; i++) {
            if (Objects.equals(items[i], object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        return getLastIndexOfRange(object, 0, size);
    }

    public int getLastIndexOfRange(Object object, int start, int end) {
        if (object == null) {
            for (int i = end - 1; i >= start; i--) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = end - 1; i >= start; i--) {
                if (object.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public MyArrayList<Integer> getAllIndicesOf(Object object) { // возвращает массив всех индексов, элементы по которым равны переданному объекту
        MyArrayList<Integer> indicesList = new MyArrayList<>();

        int start;
        int end = size;
        int index = indexOf(object);

        while (index != -1) {
            indicesList.add(index);
            start = index + 1;
            index = getIndexOfRange(object, start, end);
        }

        return indicesList;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<T> {
        private int currentIndex = -1;
        @SuppressWarnings("FieldMayBeFinal")
        private int expectedModCount = modCount;

        MyArrayListIterator() {
        }

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
                throw new IllegalStateException();
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

        if (items.length <= size + additionSize) {
            increaseCapacity();
        }

        //noinspection unchecked
        T[] array = (T[]) collection.toArray();

        if (index < size) {
            System.arraycopy(items, index, items, index + additionSize, size - index);
        }

        System.arraycopy(array, 0, items, index, additionSize);

        size += additionSize;
        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isChanged = false;

        for (Object item : collection) {
            int index = indexOf(item);

            while (index != -1) {
                remove(index);
                isChanged = true;
                index = indexOf(item);
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isChanged = false;

        for (Iterator<T> iterator = iterator(); iterator.hasNext(); ) {
            Object item = iterator.next();

            boolean needsRemoving = !collection.contains(item);

            if (needsRemoving) {
                iterator.remove();
                isChanged = true;
            }
        }

        return isChanged;
    }

/*  Вариант без итератора и без remove итератора - не работает
    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isChanged = false;

        for (Object item : this) {
            boolean needsRemoving = !collection.contains(item);

            if (needsRemoving) {
                remove(item);
                isChanged = true;
            }
        }

        return isChanged;
    }
*/


    @Override
    public void clear() {
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