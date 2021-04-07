package ru.academits.mamreyan.myarraylist;

import java.util.*;

@SuppressWarnings("unchecked")
public class MyArrayList<T> implements List<T> {
    private T[] items;
    private int size;
    private final int DEFAULT_CAPACITY = 10;
    int modCount = 0;

    public MyArrayList() {
        items = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            items = (T[]) new Object[initialCapacity];
            size = 0;
        } else if (initialCapacity == 0) {
            items = (T[]) new Object[]{};
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    public MyArrayList(List<T> list) {
        items = (T[]) list.toArray();
        size = list.size();
    }

    public void ensureCapacity(int minimumCapacity) {
        if (minimumCapacity > items.length) {
            if (size == 0) {
                items = (T[]) new Object[minimumCapacity];
            } else {
                items = Arrays.copyOf(items, minimumCapacity);
            }

            modCount++;
        }
    }

    public void ensureFreeSpace(int minimumFreeSpace) {
        if (minimumFreeSpace > items.length - size) {
            int newLength = size + minimumFreeSpace;

            if (size == 0) {
                items = (T[]) new Object[newLength];
            } else {
                items = Arrays.copyOf(items, newLength);
            }

            modCount++;
        }
    }

    public void trimToSize() {
        if (size < items.length) {
            items = (size == 0) ? items = (T[]) new Object[DEFAULT_CAPACITY] : Arrays.copyOf(items, size);
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
        return indexOfRange(object, 0, size);
    }

    public int indexOfRange(Object object, int start, int end) {
        if (object == null) {
            for (int i = start; i < end; i++) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (object.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        return lastIndexOfRange(object, 0, size);
    }

    public int lastIndexOfRange(Object object, int start, int end) {
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

    public int[] allIndicesOf(Object object) { // возвращает массив всех индексов, элементы по которым равны переданному объекту
        MyArrayList<Integer> indicesList = new MyArrayList<>();

        int start;
        int end = size;

        for (int index = indexOf(object); index != -1; index = indexOfRange(object, start, end)) {
            indicesList.add(index);
            start = index + 1;
        }

        if (indicesList.size == 0) {
            return new int[]{};
        }

        int[] indicesArray = new int[indicesList.size];

        for (int i = 0; i < indicesArray.length; i++) {
            indicesArray[i] = indicesList.get(i);
        }

        return indicesArray;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    public Iterator<T> iterator(int index) {
        return new MyArrayListIterator(index);
    }

    private class MyArrayListIterator implements Iterator<T> {
        int currentIndex = -1;
        int expectedModCount = modCount;

        MyArrayListIterator() {
        }

        MyArrayListIterator(int index) {
            indexCheck(index);

            currentIndex = index;
        }

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public T next() {
            checkForComodification();

            if (currentIndex >= size - 1) {
                throw new NoSuchElementException();
            }

            if (currentIndex >= items.length - 1) {
                throw new ConcurrentModificationException();
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

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
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
            return (T1[]) Arrays.copyOf(items, size, array.getClass());
        }

        System.arraycopy(items, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    @Override
    public boolean add(T element) {
        add(size, element);

        return true;
    }

    @Override
    public void add(int index, T element) {
        ensureCapacity(size + 1);

        if (index != size) {
            indexCheck(index);

            System.arraycopy(items, index, items, index + 1, size - index);
        }

        items[index] = element;

        size++;
        modCount++;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);

        if (index != -1) {
            remove(index);

            return true;
        }

        return false;
    }

    @Override
    public T remove(int index) {
        indexCheck(index);

        T removedElement = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);

        size--;
        modCount++;

        return removedElement;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) {
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
            indexCheck(index);
        }

        int additionSize = collection.size();

        if (additionSize == 0) {
            return false;
        }

        ensureCapacity(size + additionSize);

        T[] array = (T[]) collection.toArray();

        if (index < size) {
            System.arraycopy(items, index, items, index + additionSize, size - index);
        }

        System.arraycopy(array, 0, items, index, additionSize);

        size += additionSize;
        modCount += additionSize;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isChanged = false;

        for (Object element : collection) {
            for (int index = indexOf(element); index != -1; index = indexOf(element)) {
                remove(index);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isChanged = false;
        boolean needsRemoving;

        for (Iterator<T> iterator = iterator(); iterator.hasNext(); ) {
            Object element1 = iterator.next();

            needsRemoving = true;

            for (Object element2 : collection) {
                if (element1.equals(element2)) {
                    needsRemoving = false;
                    break;
                }
            }

            if (needsRemoving) {
                iterator.remove();
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public void clear() {
        items = (T[]) new Object[DEFAULT_CAPACITY];
        size = 0;
        modCount = 0;
    }

    @Override
    public T get(int index) {
        indexCheck(index);

        return items[index];
    }

    @Override
    public T set(int index, T element) {
        indexCheck(index);

        T removedElement = items[index];
        items[index] = element;

        return removedElement;
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

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Size = " + size + ", index =" + index);
        }
    }
}