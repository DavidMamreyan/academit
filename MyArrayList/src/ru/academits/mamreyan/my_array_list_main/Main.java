package ru.academits.mamreyan.my_array_list_main;

import ru.academits.mamreyan.my_array_list.MyArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //ArrayList

        MyArrayList<Integer> numbers = new MyArrayList<>();
        System.out.println(numbers);

        numbers = new MyArrayList<>(5);
        System.out.println(numbers);

        numbers = new MyArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(numbers);

        numbers.add(10);

        System.out.println(numbers);

        numbers.add(11, 11);

        System.out.println(numbers);

        numbers.add(0, -1);

        System.out.println(numbers);

        numbers.remove((Integer) 11);

        System.out.println(numbers);

        numbers.remove((Integer) 10);

        System.out.println(numbers);

        numbers.remove(0);

        System.out.println(numbers);

        numbers.addAll(Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1, 0));

        System.out.println(numbers);

        System.out.println(numbers.getAllIndicesOf(8));

        numbers.removeAll(Arrays.asList(0, 1, 2, 5, 6, 7));

        System.out.println(numbers);

        numbers.ensureFreeSpace(10);
        numbers.trimToSize();
        numbers = new MyArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));

        numbers.retainAll(Arrays.asList(0, 1, 2, 5, 6, 7));

        System.out.println(numbers);

        numbers.clear();

        System.out.println(numbers);

        numbers.addAll(Arrays.asList(10, 11, 12, 11, 10));

        System.out.println(numbers);
    }
}