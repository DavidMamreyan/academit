package ru.academits.mamreyan.matrix;

import ru.academits.mamreyan.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] strings;

    public Matrix(int stringsAmount, int columnsAmount) {
        if (stringsAmount <= 0 || columnsAmount <= 0) {
            throw new IllegalArgumentException("strings and columns amount must be > 0. " +
                    "strings amount = " + stringsAmount + ", columns amount = " + columnsAmount);
        }

        strings = new Vector[stringsAmount];

        for (int i = 0; i < stringsAmount; i++) {
            strings[i] = new Vector(columnsAmount);
        }
    }

    public Matrix(Matrix matrix) {
        strings = new Vector[matrix.getStringsAmount()];

        for (int i = 0, stringsAmount = matrix.getStringsAmount(); i < stringsAmount; i++) {
            strings[i] = new Vector(matrix.strings[i]);
        }
    }

    public Matrix(double[][] doubles) {
        if (doubles.length == 0) {
            throw new IllegalArgumentException("strings amount must be > 0. strings amount = " + doubles.length);
        }

        strings = new Vector[doubles.length];

        int maxLength = 0;

        for (double[] string : doubles) {
            if (string.length > maxLength) {
                maxLength = string.length;
            }
        }

        if (maxLength == 0) {
            throw new IllegalArgumentException("columns amount must be > 0. columns amount = " + maxLength);
        }

        for (int i = 0; i < doubles.length; i++) {
            strings[i] = new Vector(maxLength, doubles[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("strings amount must be > 0. strings amount = " + vectors.length);
        }

        strings = new Vector[vectors.length];

        int maxLength = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxLength) {
                maxLength = vector.getSize();
            }
        }

        if (maxLength == 0) {
            throw new IllegalArgumentException("columns amount must be > 0. columns amount = " + maxLength);
        }

        for (int i = 0; i < vectors.length; i++) {
            strings[i] = new Vector(maxLength, vectors[i]);
        }
    }

    public int getStringsAmount() {
        return strings.length;
    }

    public int getColumnsAmount() {
        return strings[0].getSize();
    }

    public Vector getStringByIndex(int index) {
        if (index < 0 || index >= getStringsAmount()) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < matrix's strings amount. index = " + index +
                    ", matrix's strings amount = " + getStringsAmount());
        }

        return strings[index];
    }

    public void setStringByIndex(int index, Vector vector) {
        if (index < 0 || index >= getStringsAmount()) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < matrix's strings amount. index = " + index +
                    ", matrix's strings amount = " + getStringsAmount());
        }

        if (vector.getSize() != getColumnsAmount()) {
            throw new IllegalArgumentException("vector's size must be equal to matrix's columns amount. " +
                    "vector's size = " + vector.getSize() + ", matrix's columns amount = " + getColumnsAmount());
        }

        strings[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("index must me 0 <= index < matrix's columns amount. index = " + index +
                    ", matrix's columns amount = " + getColumnsAmount());
        }

        Vector vector = new Vector(getStringsAmount());

        for (int i = 0; i < getStringsAmount(); i++) {
            vector.setComponentByIndex(i, strings[i].getComponentByIndex(index));
        }

        return vector;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector string : strings) {
            stringBuilder.append(string).append(", ");
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "}");

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Matrix matrix = (Matrix) o;
        return getStringsAmount() == matrix.getStringsAmount() && getColumnsAmount() == matrix.getColumnsAmount()
                && Arrays.equals(strings, matrix.strings);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(strings);
        return hash;
    }

    public Matrix add(Matrix matrix) {
        checkForDimensionsEquality(this, matrix);

        for (int i = 0; i < getStringsAmount(); i++) {
            strings[i].add(matrix.strings[i]);
        }

        return this;
    }

    public Matrix subtract(Matrix matrix) {
        checkForDimensionsEquality(this, matrix);

        for (int i = 0; i < getStringsAmount(); i++) {
            strings[i].subtract(matrix.strings[i]);
        }

        return this;
    }

    public Matrix transpose() {
        Vector[] tempArray = new Vector[getColumnsAmount()];

        for (int i = 0; i < getColumnsAmount(); i++) {
            tempArray[i] = getColumnByIndex(i);
        }

        strings = tempArray;

        return this;
    }

    public Matrix multiplyByScalar(double scalar) {
        for (Vector string : strings) {
            string.multiplyByScalar(scalar);
        }

        return this;
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != getStringsAmount() && vector.getSize() != getColumnsAmount()) {
            throw new IllegalArgumentException("vector's size must be equal to matrix's strings or columns amount. " +
                    "vector's size = " + vector.getSize() +
                    ", matrix's strings amount = " + getStringsAmount() + ", matrix's columns amount =" + getColumnsAmount());
        }

        Vector resultVector;

        if (vector.getSize() == getStringsAmount()) {
            resultVector = new Vector(getColumnsAmount());

            for (int i = 0; i < resultVector.getSize(); i++) {
                resultVector.setComponentByIndex(i, Vector.getScalarProduct(vector, getColumnByIndex(i)));
            }
        } else {
            resultVector = new Vector(getStringsAmount());

            for (int i = 0; i < resultVector.getSize(); i++) {
                resultVector.setComponentByIndex(i, Vector.getScalarProduct(getStringByIndex(i), vector));
            }
        }

        return resultVector;
    }

    public double getDeterminant() {
        if (getStringsAmount() != getColumnsAmount()) {
            throw new UnsupportedOperationException("matrix must be square. " +
                    "matrix's strings amount = " + getStringsAmount() + ", matrix's columns amount = " + getColumnsAmount());
        }

        if (getStringsAmount() == 1) {
            return strings[0].getComponentByIndex(0);
        }

        if (getStringsAmount() == 2) {
            return strings[0].getComponentByIndex(0) * strings[1].getComponentByIndex(1) -
                    strings[1].getComponentByIndex(0) * strings[0].getComponentByIndex(1);
        }

        double determinant = 0;

        for (int i = 0; i < getColumnsAmount(); i++) {
            determinant += Math.pow(-1, 2 + i) * strings[0].getComponentByIndex(i) *
                    getMatrixWithCrossedOutStringAndColumn(i).getDeterminant();
        }

        return determinant;
    }

    private Matrix getMatrixWithCrossedOutStringAndColumn(int columnIndex) {
        if (0 >= getStringsAmount() || columnIndex < 0 || columnIndex >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("stringIndex must be 0 <= stringIndex < strings amount " +
                    "and columnIndex must be 0 <= columnIndex <= columns amount. " +
                    "stringIndex = " + 0 + ", columnIndex = " + columnIndex +
                    ", matrix's strings amount = " + getStringsAmount() + ", matrix's columns amount = " + getColumnsAmount());
        }

        Matrix matrix = new Matrix(getStringsAmount() - 1, getColumnsAmount() - 1);

        for (int i = 1; i < getStringsAmount(); i++) {
            for (int j = 0; j < columnIndex; j++) {
                matrix.strings[i - 1].setComponentByIndex(j, strings[i].getComponentByIndex(j));
            }

            for (int j = columnIndex + 1; j < getColumnsAmount(); j++) {
                matrix.strings[i - 1].setComponentByIndex(j - 1, strings[i].getComponentByIndex(j));
            }
        }

        return matrix;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkForDimensionsEquality(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix = resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkForDimensionsEquality(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix = resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsAmount() != matrix2.getStringsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions are not proper. " +
                    "matrix1's columns amount must me equal to matrix2's strings amount. " +
                    "matrix1's columns amount = " + matrix1.getColumnsAmount() + ", matrix2's strings amount = " + matrix2.getStringsAmount());
        }

        Matrix resultMatrix = new Matrix(matrix1.getStringsAmount(), matrix2.getColumnsAmount());
        Matrix transposedMatrix = new Matrix(matrix2);
        transposedMatrix.transpose();

        for (int i = 0; i < resultMatrix.getStringsAmount(); i++) {
            for (int j = 0; j < resultMatrix.getColumnsAmount(); j++) {
                resultMatrix.strings[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.strings[i], transposedMatrix.strings[j]));
            }
        }

        return resultMatrix;
    }

    private static void checkForDimensionsEquality(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getStringsAmount() != matrix2.getStringsAmount() || matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions must be equal. " +
                    "matrix1's strings amount = " + matrix1.getStringsAmount() + ", matrix1's columns amount = " + matrix1.getColumnsAmount() +
                    ", matrix2's strings amount = " + matrix2.getStringsAmount() + ", matrix2's columns amount = " + matrix2.getStringsAmount());
        }
    }
}