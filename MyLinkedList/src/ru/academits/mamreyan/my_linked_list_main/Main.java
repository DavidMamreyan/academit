package ru.academits.mamreyan.my_linked_list_main;

import ru.academits.mamreyan.my_linked_list.MyLinkedList;

public class Main {
    public static void main(String[] args) {
        MyLinkedList<Integer> numbers = new MyLinkedList<>();
        System.out.println(numbers);
        System.out.println(numbers.size());

        numbers.addFirst(8);
        numbers.addFirst(7);
        numbers.addFirst(6);
        numbers.addFirst(5);
        numbers.addFirst(4);
        numbers.addFirst(3);
        numbers.addFirst(2);
        numbers.addFirst(1);
        numbers.addFirst(0);

        System.out.println(numbers);
        System.out.println(numbers.size());
        System.out.println(numbers.getFirst());
        System.out.println(numbers.get(0));
        System.out.println(numbers.set(0, -1));
        System.out.println(numbers);
        numbers.add(1, 0);
        System.out.println(numbers);
        System.out.println(numbers.remove(1));
        System.out.println(numbers);
        System.out.println(numbers.removeFirst());
        System.out.println(numbers);
        System.out.println(numbers.remove(Integer.valueOf(9)));
        System.out.println(numbers);
        System.out.println(numbers.remove(Integer.valueOf(8)));
        System.out.println(numbers);
        System.out.println(numbers.copy().reverse());
    }
}