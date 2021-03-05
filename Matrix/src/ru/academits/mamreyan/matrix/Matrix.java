package ru.academits.mamreyan.matrix;

import ru.academits.mamreyan.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] vectors;

    public Matrix(int n, int m) {
        vectors = new Vector[n];

        for (int i = 0; i < n; i++) {
            vectors[i] = new Vector(m);
        }
    }

    public Matrix(Matrix matrix) {
        vectors = new Vector[matrix.getStringsAmount()];

        vectors = Arrays.copyOf(matrix.vectors, matrix.getStringsAmount());
    }

    public Matrix(double[][] vectors) {
        this.vectors = new Vector[vectors.length];

        int maxM = 0;

        for (double[] doubles : vectors) {
            if (doubles.length > maxM) {
                maxM = doubles.length;
            }
        }

        for (int i = 0; i < vectors.length; i++) {
            this.vectors[i] = new Vector(vectors[i]);
            this.vectors[i].appendZeroComponents(maxM - vectors[i].length);
        }
    }

    public Matrix(Vector[] vectors) {
        this.vectors = new Vector[vectors.length];

        int maxM = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxM) {
                maxM = vector.getSize();
            }
        }

        for (int i = 0; i < vectors.length; i++) {
            this.vectors[i] = new Vector(vectors[i]);
            this.vectors[i].appendZeroComponents(maxM - vectors[i].getSize());
        }
    }

    public int getStringsAmount() {
        return vectors.length;
    }

    public int getColumnsAmount() {
        return vectors[0].getSize();
    }

    public Vector getVectorByIndex(int index) {
        if (index < 0 || index >= getStringsAmount()) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < matrix's strings amount. index = " + index +
                    ", matrix's strings amount = " + getStringsAmount());
        }

        return vectors[index];
    }

    public void setVectorByIndex(int index, Vector vector) {
        if (vector.getSize() != getColumnsAmount()) {
            throw new IllegalArgumentException("vector's length must be equal to matrix's columns amount. " +
                    "vector's length = " + vector.getSize() + ", matrix's columns amount = " + getColumnsAmount());
        }

        vectors[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("index must me 0 <= index < matrix's columns amount. index = " + index +
                    ", matrix's columns amount = " + getColumnsAmount());
        }

        Vector vector = new Vector(getStringsAmount());

        for (int i = 0; i < getStringsAmount(); i++) {
            vector.setComponentByIndex(i, vectors[i].getComponentByIndex(index));
        }

        return vector;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < getStringsAmount(); i++) {
            stringBuilder.append(vectors[i]).append(", ");
        }

        stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length() - 1, "}");

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
                && Arrays.equals(vectors, matrix.vectors);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(vectors);
        return hash;
    }

    public Matrix add(Matrix matrix) {
        if (getStringsAmount() != matrix.getStringsAmount() || getColumnsAmount() != matrix.getColumnsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. " +
                    "matrix1's strings amount = " + getStringsAmount() + ", matrix1's columns amount = " + getColumnsAmount() +
                    ", matrix2's strings amount = " + matrix.getStringsAmount() + ", matrix2's columns amount = " + matrix.getColumnsAmount());
        }

        for (int i = 0; i < getStringsAmount(); i++) {
            vectors[i].add(matrix.vectors[i]);
        }


        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (getStringsAmount() != matrix.getStringsAmount() || getColumnsAmount() != matrix.getColumnsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. " +
                    "matrix1's strings amount = " + getStringsAmount() + ", matrix1's columns amount = " + getColumnsAmount() +
                    ", matrix2's strings amount = " + matrix.getStringsAmount() + ", matrix2's columns amount = " + matrix.getColumnsAmount());
        }

        for (int i = 0; i < getStringsAmount(); i++) {
            vectors[i].subtract(matrix.vectors[i]);
        }


        return this;
    }

    public Matrix transpose() {
        Matrix tempMatrix = new Matrix(getColumnsAmount(), getStringsAmount());

        for (int i = 0; i < getColumnsAmount(); i++) {
            for (int j = 0; j < getStringsAmount(); j++) {
                tempMatrix.vectors[i].setComponentByIndex(j, vectors[j].getComponentByIndex(i));
            }
        }

        vectors = tempMatrix.vectors;

        return this;
    }

    public Matrix multiplyByScalar(double scalar) {
        for (int i = 0; i < scalar; i++) {
            vectors[i].multiplyByScalar(scalar);
        }

        return this;
    }

    public Matrix multiplyByVector(Vector vector) {
        Matrix matrix;

        if (getStringsAmount() == vector.getSize()) {
            matrix = new Matrix(Matrix.getMultiplication(this, new Matrix(new Vector[]{vector})));
        } else if (getColumnsAmount() == vector.getSize()) {
            matrix = new Matrix(Matrix.getMultiplication(this, new Matrix(new Vector[]{vector}).transpose()));
        } else {
            throw new IllegalArgumentException("vector's length must be equal to matrix's strings or columns amount. " +
                    "vector's length = " + vector.getSize() +
                    ", matrix's strings amount = " + getStringsAmount() + ", matrix's columns amount =" + getColumnsAmount());
        }

        vectors = matrix.vectors;

        return this;
    }

    public double getMatrixDeterminant() {
        if (getStringsAmount() != getColumnsAmount()) {
            throw new IllegalArgumentException("matrix's dimensions must be equal. " +
                    "matrix's strings amount = " + getStringsAmount() + ", matrix's columns amount = " + getColumnsAmount());
        }

        if (getStringsAmount() == 1) {
            return vectors[0].getComponentByIndex(0);
        }

        if (getStringsAmount() == 2) {
            return vectors[0].getComponentByIndex(0) * vectors[1].getComponentByIndex(1) -
                    vectors[1].getComponentByIndex(0) * vectors[0].getComponentByIndex(1);
        }

        double determinant = 0;

        for (int i = 0; i < getColumnsAmount(); i++) {
            determinant += Math.pow(-1, 1 + (i + 1)) * vectors[0].getComponentByIndex(i) *
                    this.getMatrixWithCrossedOutStrAndCol(0, i).getMatrixDeterminant();
        }

        return determinant;
    }

    private Matrix getMatrixWithCrossedOutStrAndCol(@SuppressWarnings("SameParameterValue") int k, int l) {
        if (k < 0 || k >= getStringsAmount() || l < 0 || l >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("k must be 0 <= k < strings amount and l must be 0 <= l <= columns amount. " +
                    "k = " + k + ", l = " + l +
                    ", matrix's strings amount = " + getStringsAmount() + ", matrix's columns amount = " + getColumnsAmount());
        }

        Matrix matrix = new Matrix(getStringsAmount() - 1, getColumnsAmount() - 1);

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < l; j++) {
                matrix.vectors[i].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
            }

            for (int j = l + 1; j < getColumnsAmount(); j++) {
                matrix.vectors[i].setComponentByIndex(j - 1, vectors[i].getComponentByIndex(j));
            }
        }

        for (int i = k + 1; i < getStringsAmount(); i++) {
            for (int j = 0; j < l; j++) {
                matrix.vectors[i - 1].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
            }

            for (int j = l + 1; j < getColumnsAmount(); j++) {
                matrix.vectors[i - 1].setComponentByIndex(j - 1, vectors[i].getComponentByIndex(j));
            }
        }

        return matrix;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getStringsAmount() != matrix2.getStringsAmount() || matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. " +
                    "matrix1's strings amount = " + matrix1.getStringsAmount() + ", matrix1's columns amount = " + matrix1.getColumnsAmount() +
                    ", matrix2's strings amount = " + matrix2.getStringsAmount() + ", matrix2's columns amount = " + matrix2.getStringsAmount());
        }

        Matrix resultMatrix;
        Matrix tempMatrix = new Matrix(matrix1);

        resultMatrix = tempMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getStringsAmount() != matrix2.getStringsAmount() || matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. " +
                    "matrix1's strings amount = " + matrix1.getStringsAmount() + ", matrix1's columns amount = " + matrix1.getColumnsAmount() +
                    ", matrix2's strings amount = " + matrix2.getStringsAmount() + ", matrix2's columns amount = " + matrix2.getStringsAmount());
        }

        Matrix resultMatrix;
        Matrix tempMatrix = new Matrix(matrix1);

        resultMatrix = tempMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsAmount() != matrix2.getStringsAmount()) {
            throw new IllegalArgumentException("matrices' dimensions are not proper. " +
                    "matrix1's columns amount must me equal to matrix2's strings amount. " +
                    "matrix1's columns amount = " + matrix1.getColumnsAmount() + ", matrix2's strings amount = " + matrix2.getStringsAmount());
        }

        Matrix resultMatrix = new Matrix(matrix1.getStringsAmount(), matrix2.getColumnsAmount());
        Matrix tempMatrix = new Matrix(matrix2);
        tempMatrix.transpose();

        for (int i = 0; i < resultMatrix.getStringsAmount(); i++) {
            for (int j = 0; j < resultMatrix.getColumnsAmount(); j++) {
                resultMatrix.vectors[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.vectors[i], tempMatrix.vectors[j]));
            }
        }

        return resultMatrix;
    }
}