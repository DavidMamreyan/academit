package ru.academits.mamreyan.matrix_main;

import ru.academits.mamreyan.matrix.Matrix;
import ru.academits.mamreyan.vector.Vector;

public class Main {
    public static void main(String[] args) {
        Matrix matrix1 = new Matrix(new Vector[]{
                new Vector(new double[]{1}),
                new Vector(new double[]{1, 2}),
                new Vector(new double[]{1, 2, 3})
        });

        System.out.println(matrix1);
        System.out.println(matrix1.getVectorByIndex(1));
        System.out.println(matrix1.getColumnByIndex(1));
        matrix1.setVectorByIndex(1, new Vector(new double[]{10, 20, 30}));
        System.out.println(matrix1);
        System.out.println(matrix1.getStringsAmount());
        System.out.println(matrix1.getColumnsAmount());

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        Matrix matrix2 = new Matrix(new double[][]{{4, 3}, {2, 1}});

        System.out.println(matrix1);
        System.out.println(matrix2);
        System.out.println(Matrix.getSum(matrix1, matrix2));

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        matrix2 = new Matrix(matrix1.transpose());

        System.out.println(matrix1.transpose());
        System.out.println(matrix2);
        System.out.println(Matrix.getDifference(matrix1, matrix2));

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1, 2}, {3, 4}, {5, 6}});
        matrix2 = new Matrix(new double[][]{{6, 5, 4}, {3, 2, 1}});

        System.out.println(matrix1);
        System.out.println(matrix2);
        System.out.println(Matrix.getMultiplication(matrix1, matrix2));

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1, 2}, {3, 4}, {5, 6}});

        System.out.println(matrix1);
        System.out.println(matrix1.multiplyByScalar(2.5));

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{76, 40, 82}, {21, 80, 2}, {31, 23, 70}});

        System.out.println(matrix1);
        System.out.println(matrix1.getMatrixDeterminant());

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1}, {2}, {3}});
        Vector vector = new Vector(new double[]{1, 2, 3});

        System.out.println(matrix1);
        System.out.println(matrix1.getStringsAmount());
        System.out.println(matrix1.getColumnsAmount());
        System.out.println(vector);
        System.out.println(vector.getSize());
        System.out.println(matrix1.multiplyByVector(vector));

        System.out.println("__________________");

        matrix2 = new Matrix(new double[][]{{1, 2, 3}, {3, 4, 5}});
        vector = new Vector(new double[]{1, 2, 3});

        System.out.println(matrix2);
        System.out.println(matrix2.getStringsAmount());
        System.out.println(matrix2.getColumnsAmount());
        System.out.println(vector);
        System.out.println(vector.getSize());
        System.out.println(matrix2.multiplyByVector(vector));

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        matrix2 = new Matrix(new double[][]{{4, 3}, {2, 1}});

        System.out.println(matrix1);
        System.out.println(matrix2);
        System.out.println(matrix1.add(matrix2));

        System.out.println("__________________");

        matrix1 = new Matrix(new double[][]{{1, 2}, {3, 4}});
        matrix2 = new Matrix(new double[][]{{4, 3}, {2, 1}});

        System.out.println(matrix1);
        System.out.println(matrix2);
        System.out.println(matrix1.subtract(matrix2));
    }
}