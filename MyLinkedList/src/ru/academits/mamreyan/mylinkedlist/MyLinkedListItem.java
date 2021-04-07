package ru.academits.mamreyan.mylinkedlist;

public class MyLinkedListItem<T> {
    private T data;
    private MyLinkedListItem<T> next;

    public MyLinkedListItem() {
    }

    public MyLinkedListItem(T data) {
        this.data = data;
    }

    public MyLinkedListItem(T data, MyLinkedListItem<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MyLinkedListItem<T> getNext() {
        return next;
    }

    public void setNext(MyLinkedListItem<T> next) {
        this.next = next;
    }
}