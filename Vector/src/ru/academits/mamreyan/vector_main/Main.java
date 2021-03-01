package ru.academits.mamreyan.vector_main;

import ru.academits.mamreyan.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Vector v1 = new Vector(new double[]{1, 2, 3});

        System.out.println(v1);
        System.out.println(v1.reverse());

        System.out.println("__________________");

        v1 = new Vector(3, new double[]{1, 2, 3});
        Vector v2 = new Vector(4, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.getSum(v1, v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(4, new double[]{1, 2, 3});
        v2 = new Vector(3, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v1.add(v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(4, new double[]{1, 2, 3});
        v2 = new Vector(6, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.getDifference(v1, v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(6, new double[]{1, 2, 3});
        v2 = new Vector(4, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v1.subtract(v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(new double[]{1, 2, 3});

        System.out.print(v1);
        System.out.println(" * (" + -2 + ") = " + v1.multiplyByScalar(-2));

        System.out.println("__________________");

        v1 = new Vector(v2);

        System.out.println(v1);
        System.out.println(v1.getLength());

        System.out.println("__________________");

        v1 = new Vector(1);

        System.out.println(v1);
        System.out.println(v1.getLength());

        System.out.println("__________________");

        v1 = new Vector(v2);

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.getScalarProduct(v1, v2));

        System.out.println("__________________");

        v1 = new Vector(1);

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.getScalarProduct(v1, v2));

        System.out.println("__________________");

        v1 = new Vector(3, new double[]{1, 2});

        System.out.println(v1);
        System.out.println(v1.getSize());
        System.out.println(v1.getComponentByIndex(2));

        v1.setComponentByIndex(2, 3);

        System.out.println(v1);

        System.out.println("__________________");

        v1 = new Vector(new double[]{0, 0, 0, 6, 7, 8, 0, 0, 0});

        System.out.println(v1);
        System.out.println(v1.trimZeroComponents());

        System.out.println("__________________");

        v1 = new Vector(new double[]{0, 0, 0, 6, 7, 8, 0, 0, 0});

        System.out.println(v1);
        System.out.println(v1.trimZeroComponents(1));

        System.out.println("__________________");

        v2 = new Vector(new double[]{1, 2});

        System.out.println(v2);
        System.out.println(v2.appendZeroComponents(2));
    }
}