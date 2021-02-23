package ru.academits.mamreyan.vector_main;

import ru.academits.mamreyan.vector.Vector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Vector v1 = new Vector(new double[]{1, 2, 3});

        System.out.println(v1);
        System.out.println(Vector.reversion(v1));
        System.out.println(v1);

        System.out.println("__________________");

        Vector v2 = new Vector(3, new double[]{4, 5, 6});

        System.out.println(v2);
        System.out.println(v2.reverseVector());
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(3, new double[]{1, 2, 3});
        v2 = new Vector(4, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.addition(v1, v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(4, new double[]{1, 2, 3});
        v2 = new Vector(3, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v1.addVector(v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(4, new double[]{1, 2, 3});
        v2 = new Vector(6, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.subtraction(v1, v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(6, new double[]{1, 2, 3});
        v2 = new Vector(4, new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(v1.subtractVector(v2));
        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(new double[]{1, 2, 3});

        System.out.print(v1);
        System.out.println(" * (" + -2 + ") = " + v1.multiplyVector(-2));
        System.out.println(v1);

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
        System.out.println(Vector.scalarProduct(v1, v2));

        System.out.println("__________________");

        v1 = new Vector(1);

        System.out.println(v1);
        System.out.println(v2);
        System.out.println(Vector.scalarProduct(v1, v2));

        System.out.println("__________________");

        v1 = new Vector(3, new double[]{1, 2});
        v2 = new Vector(3, new double[]{0, 0, 3});

        System.out.println(v1);
        System.out.println(v2);

        System.out.println(v1.getSize());
        System.out.println(v1.getComponentByIndex(2));
        System.out.println(Arrays.toString(v2.getComponentsArray()));

        v1.setComponentByIndex(2, 3);
        v2.setComponentsArray(new double[]{4, 5, 6});

        System.out.println(v1);
        System.out.println(v2);

        System.out.println("__________________");

        v1 = new Vector(3, new double[]{1, 2, 3});

        v1.setComponentsArray(new double[]{0, 10});

        System.out.println(v1);

        System.out.println("__________________");

        v1 = new Vector(new double[]{1, 2, 0, 0});

        System.out.println(v1);
        System.out.println(v1.trimZeroComponents());
        System.out.println(v1);

        System.out.println("__________________");

        v2 = new Vector(new double[]{1, 2});

        System.out.println(v2);
        System.out.println(v2.appendZeroComponents(2));
        System.out.println(v2);
    }
}