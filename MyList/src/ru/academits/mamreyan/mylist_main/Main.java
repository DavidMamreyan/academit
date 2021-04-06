package ru.academits.mamreyan.mylist_main;

import ru.academits.mamreyan.mylist.MyList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        MyList<Integer> names = new MyList<>();
        System.out.println(Arrays.toString(names.toArray()));

        names = new MyList<>(5);
        System.out.println(Arrays.toString(names.toArray()));

        names = new MyList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(Arrays.toString(names.toArray()));

        names.add(10);

        System.out.println(Arrays.toString(names.toArray()));

        names.add(11, 11);

        System.out.println(Arrays.toString(names.toArray()));

        names.add(0, -1);

        System.out.println(Arrays.toString(names.toArray()));

        names.remove((Integer) 11);

        System.out.println(Arrays.toString(names.toArray()));

        names.remove((Integer) 10);

        System.out.println(Arrays.toString(names.toArray()));

        names.remove(0);

        System.out.println(Arrays.toString(names.toArray()));

        names.addAll(Arrays.asList(8, 7, 6, 5, 4, 3, 2, 1, 0));

        System.out.println(Arrays.toString(names.toArray()));

        System.out.println(Arrays.toString(names.allIndicesOf(8)));

        names.removeAll(Arrays.asList(0, 3, 6));

        System.out.println(Arrays.toString(names.toArray()));

        names.ensureFreeSpace(10);
        names.trimToSize();

        names.retainAll(Arrays.asList(1, 4, 8, 9));

        System.out.println(Arrays.toString(names.toArray()));

        names.clear();

        System.out.println(Arrays.toString(names.toArray()));

        names.addAll(Arrays.asList(10, 11, 12, 11, 10));

        System.out.println(Arrays.toString(names.toArray()));
    }
}