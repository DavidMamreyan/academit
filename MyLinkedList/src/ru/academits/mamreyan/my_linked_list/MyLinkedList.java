package ru.academits.mamreyan.my_linked_list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> {
    private ListItem<T> head;
    private int size;

    public MyLinkedList() {
    }

    public MyLinkedList(T data) {
        head = new ListItem<>(data);
        size = 1;
    }

    public MyLinkedList(MyLinkedList<T> list) {
        if (list.size == 0) {
            return;
        }

        size = list.size;
        head = new ListItem<>(list.head.getData());

        for (ListItem<T> current1 = head, current2 = list.head;
             current2.getNext() != null;
             current1 = current1.getNext(), current2 = current2.getNext()) {
            current1.setNext(new ListItem<>(current2.getNext().getData()));
        }
    }

    public int size() {
        return size;
    }

    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        }

        return head.getData();
    }

    public T get(int index) {
        checkIndex(index);

        return getItemByIndex(index).getData();
    }

    public T set(int index, T data) {
        checkIndex(index);

        ListItem<T> current = getItemByIndex(index);

        T replacedData = current.getData();

        current.setData(data);

        return replacedData;
    }

    public void addFirst(T data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void add(int index, T data) {
        if (index != size) {
            checkIndex(index);
        }

        if (index == 0) {
            addFirst(data);

            return;
        }

        ListItem<T> current = getItemByIndex(index - 1);

        current.setNext(new ListItem<>(data, current.getNext()));
        size++;
    }

    public void add(T data) {
        add(size, data);
    }

    public T remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<T> previous = getItemByIndex(index - 1);
        ListItem<T> current = previous.getNext();

        T removedData = current.getData();
        previous.setNext(current.getNext());

        size--;
        return removedData;
    }

    public T removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty");
        }

        T removedData = head.getData();
        head = head.getNext();

        size--;
        return removedData;
    }

    public boolean remove(Object object) {
        ListItem<T> current = head;
        ListItem<T> previous = null;

        for (; current != null; previous = current, current = current.getNext()) {
            if (Objects.equals(current.getData(), object)) {
                if (previous == null) {
                    head = current.getNext();
                } else {
                    previous.setNext(current.getNext());
                }

                size--;
                return true;
            }
        }

        return false;
    }

    public MyLinkedList<T> reverse() {
        if (size <= 1) {
            return this;
        }

        ListItem<T> previous = head;
        ListItem<T> current = head.getNext();
        ListItem<T> next = current.getNext();

        for (; next != null; previous = current, current = next, next = next.getNext()) {
            current.setNext(previous);
        }

        current.setNext(previous);
        head.setNext(null);
        head = current;

        return this;
    }

    public MyLinkedList<T> copy() {
        return new MyLinkedList<>(this);
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

        for (ListItem<T> current = head; current != null; current = current.getNext()) {
            stringBuilder.append(current.getData()).append(", ");
        }

        int length = stringBuilder.length();

        stringBuilder.replace(length - 2, length, "]");

        return stringBuilder.toString();
    }

    private ListItem<T> getItemByIndex(int index) {
        ListItem<T> current = head;

        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }

        return current;
    }
}