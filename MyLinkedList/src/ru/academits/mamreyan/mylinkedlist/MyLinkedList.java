package ru.academits.mamreyan.mylinkedlist;

public class MyLinkedList<T> {
    private MyLinkedListItem<T> head;
    private int size;

    public MyLinkedList() {
        size = 0;
    }

    public MyLinkedList(MyLinkedListItem<T> head) {
        this.head = head;
        size = 0;

        for (MyLinkedListItem<T> p = this.head; p != null; p = p.getNext()) {
            size++;
        }
    }

    public MyLinkedList(MyLinkedList<T> list) {
        size = list.size;
        head = new MyLinkedListItem<>(list.head.getData());

        for (MyLinkedListItem<T> p1 = head, p2 = list.head;
             p1 != null && p2.getNext() != null;
             p1 = p1.getNext(), p2 = p2.getNext()) {
            p1.setNext(new MyLinkedListItem<>(p2.getNext().getData()));
        }
    }

    public int size() {
        return size;
    }

    public MyLinkedListItem<T> head() {
        return head;
    }

    public T getFirstItemData() {
        return head.getData();
    }

    public T getData(int index) {
        checkIndex(index);

        if (index == 0) {
            return getFirstItemData();
        }

        MyLinkedListItem<T> p = head;

        for (int i = 0; i < index; i++) {
            p = p.getNext();
        }

        return p.getData();
    }

    public T setData(int index, T data) {
        checkIndex(index);

        MyLinkedListItem<T> p = head;

        for (int i = 0; i < index; i++) {
            p = p.getNext();
        }

        T replacedData = p.getData();

        p.setData(data);

        return replacedData;
    }

    public void addToStart(T element) {
        head = new MyLinkedListItem<>(element, head);
        size++;
    }

    public void add(int index, T element) {
        if (index == size) {
            addToEnd(element);
        }

        checkIndex(index);

        if (index == 0) {
            addToStart(element);
        }

        MyLinkedListItem<T> p = head;

        for (int i = 0; i < index - 1; i++) {
            p = p.getNext();
        }

        p.setNext(new MyLinkedListItem<>(element, p.getNext()));
        size++;
    }

    public void addToEnd(T element) {
        MyLinkedListItem<T> p = head;

        for (int i = 0; i < size - 1; i++) {
            p = p.getNext();
        }

        p.setNext(new MyLinkedListItem<>(element));
        size++;
    }

    public T remove(int index) {
        checkIndex(index);

        MyLinkedListItem<T> p = head;

        for (int i = 0; i < index - 1; i++) {
            p = p.getNext();
        }

        MyLinkedListItem<T> prev = p;
        p = p.getNext();

        T removedData = p.getData();
        prev.setNext(p.getNext());

        size--;
        return removedData;
    }

    public T removeFirstItem() {
        T removedItem = head.getData();
        head = head.getNext();

        size--;
        return removedItem;
    }

    public boolean remove(Object object) {
        MyLinkedListItem<T> p = head;
        MyLinkedListItem<T> prev = null;

        if (object == null) {
            for (; p != null; prev = p, p = p.getNext()) {
                if (p.getData() == null) {
                    break;
                }
            }

        } else {
            for (; p != null; prev = p, p = p.getNext()) {
                if (p.getData().equals(object)) {
                    break;
                }
            }

        }

        if (p == null) {
            return false;
        }

        if (prev == null) {
            head = p.getNext();
        } else {
            prev.setNext(p.getNext());

        }

        size--;
        return true;
    }

    public MyLinkedList<T> reverse() {
        MyLinkedListItem<T> prev = head, p = head.getNext(), next = p.getNext();

        for (;
             next != null;
             prev = p, p = next, next = next.getNext()) {
            p.setNext(prev);
        }

        p.setNext(prev);
        head.setNext(null);
        head = p;

        return this;
    }

    public MyLinkedList<T> copy() {
        return new MyLinkedList<>(this);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Size = " + size + ", index = " + index);
        }
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder string = new StringBuilder("[");

        for (MyLinkedListItem<T> p = head; p != null; p = p.getNext()) {
            string.append(p.getData()).append(", ");
        }

        string.replace(string.length() - 2, string.length() - 1, "]");

        return string.toString();
    }
}