package ru.academits.mamreyan.myarraylist_main;

import ru.academits.mamreyan.myarraylist.MyArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> numbers = new MyArrayList<>();
        System.out.println(Arrays.toString(numbers.toArray()));

        numbers = new MyArrayList<>(5);
        System.out.println(Arrays.toString(numbers.toArray()));

        numbers = new MyArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.add(10);

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.add(11, 11);

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.add(0, -1);

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.remove((Integer) 11);

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.remove((Integer) 10);

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.remove(0);

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.addAll(Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1, 0));

        System.out.println(Arrays.toString(numbers.toArray()));

        System.out.println(Arrays.toString(numbers.allIndicesOf(8)));

        numbers.removeAll(Arrays.asList(0, 3, 6));

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.ensureFreeSpace(10);
        numbers.trimToSize();

        numbers.retainAll(Arrays.asList(2, 4, 6, 8));

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.clear();

        System.out.println(Arrays.toString(numbers.toArray()));

        numbers.addAll(Arrays.asList(10, 11, 12, 11, 10));

        System.out.println(Arrays.toString(numbers.toArray()));
    }
}