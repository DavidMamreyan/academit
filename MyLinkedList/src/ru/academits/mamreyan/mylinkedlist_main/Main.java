package ru.academits.mamreyan.mylinkedlist_main;

import ru.academits.mamreyan.mylinkedlist.MyLinkedList;
import ru.academits.mamreyan.mylinkedlist.MyLinkedListItem;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> numbers = new MyLinkedList<>();
        System.out.println(numbers);
        System.out.println(numbers.size());
        numbers = new MyLinkedList<>(new MyLinkedListItem<>());
        System.out.println(numbers);
        System.out.println(numbers.size());
        numbers = new MyLinkedList<>(new MyLinkedListItem<>(9));
        System.out.println(numbers);
        System.out.println(numbers.size());

        numbers.addToStart(8);
        numbers.addToStart(7);
        numbers.addToStart(6);
        numbers.addToStart(5);
        numbers.addToStart(4);
        numbers.addToStart(3);
        numbers.addToStart(2);
        numbers.addToStart(1);
        numbers.addToStart(0);

        System.out.println(numbers);
        System.out.println(numbers.size());
        System.out.println(numbers.head().getData());
        System.out.println(numbers.getFirstItemData());
        System.out.println(numbers.getData(0));
        System.out.println(numbers.setData(0, -1));
        System.out.println(numbers);
        numbers.add(1, 0);
        System.out.println(numbers);
        System.out.println(numbers.remove(1));
        System.out.println(numbers);
        System.out.println(numbers.removeFirstItem());
        System.out.println(numbers);
        System.out.println(numbers.remove("9"));
        System.out.println(numbers);
        System.out.println(numbers.copy().reverse());
    }
}