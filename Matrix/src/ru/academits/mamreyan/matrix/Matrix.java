package ru.academits.mamreyan.matrix;

import ru.academits.mamreyan.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsAmount, int columnsAmount) {
        if (rowsAmount <= 0 || columnsAmount <= 0) {
            throw new IllegalArgumentException("Rows and columns amount must be > 0. " +
                    "Rows amount = " + rowsAmount + ", columns amount = " + columnsAmount);
        }

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; i++) {
            rows[i] = new Vector(columnsAmount);
        }
    }

    public Matrix(Matrix matrix) {
        int rowsAmount = matrix.getRowsAmount();

        rows = new Vector[rowsAmount];

        for (int i = 0; i < rowsAmount; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Rows amount must be > 0. Rows amount = " + array.length);
        }

        rows = new Vector[array.length];

        int maxSize = 0;

        for (double[] row : array) {
            if (row.length > maxSize) {
                maxSize = row.length;
            }
        }

        if (maxSize == 0) {
            throw new IllegalArgumentException("Columns amount must be > 0. Columns amount = " + maxSize);
        }

        for (int i = 0; i < array.length; i++) {
            rows[i] = new Vector(maxSize, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        if (vectors.length == 0) {
            throw new IllegalArgumentException("Rows amount must be > 0. Rows amount = " + vectors.length);
        }

        rows = new Vector[vectors.length];

        int maxSize = 0;

        for (Vector vector : vectors) {
            if (vector.getSize() > maxSize) {
                maxSize = vector.getSize();
            }
        }

        for (int i = 0; i < vectors.length; i++) {
            rows[i] = new Vector(maxSize, vectors[i]);
        }
    }

    public int getRowsAmount() {
        return rows.length;
    }

    public int getColumnsAmount() {
        return rows[0].getSize();
    }

    public Vector getRowByIndex(int index) {
        if (index < 0 || index >= getRowsAmount()) {
            throw new IndexOutOfBoundsException("Index must be 0 <= index < matrix's rows amount. Index = " + index +
                    ", matrix's rows amount = " + getRowsAmount());
        }

        return new Vector(rows[index]);
    }

    public void setRowByIndex(int index, Vector vector) {
        if (index < 0 || index >= getRowsAmount()) {
            throw new IndexOutOfBoundsException("Index must be 0 <= index < matrix's rows amount. Index = " + index +
                    ", matrix's rows amount = " + getRowsAmount());
        }

        if (vector.getSize() != getColumnsAmount()) {
            throw new IllegalArgumentException("Vector's size must be equal to matrix's columns amount. " +
                    "Vector's size = " + vector.getSize() + ", matrix's columns amount = " + getColumnsAmount());
        }

        rows[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("Index must me 0 <= index < matrix's columns amount. Index = " + index +
                    ", matrix's columns amount = " + getColumnsAmount());
        }

        Vector vector = new Vector(getRowsAmount());

        for (int i = 0; i < getRowsAmount(); i++) {
            vector.setComponentByIndex(i, rows[i].getComponentByIndex(index));
        }

        return vector;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector row : rows) {
            stringBuilder.append(row).append(", ");
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
        return getRowsAmount() == matrix.getRowsAmount() && getColumnsAmount() == matrix.getColumnsAmount()
                && Arrays.equals(rows, matrix.rows);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(rows);
        return hash;
    }

    public Matrix add(Matrix matrix) {
        checkForDimensionsEquality(this, matrix);

        for (int i = 0; i < getRowsAmount(); i++) {
            rows[i].add(matrix.rows[i]);
        }

        return this;
    }

    public Matrix subtract(Matrix matrix) {
        checkForDimensionsEquality(this, matrix);

        for (int i = 0; i < getRowsAmount(); i++) {
            rows[i].subtract(matrix.rows[i]);
        }

        return this;
    }

    public Matrix transpose() {
        Vector[] newRows = new Vector[getColumnsAmount()];

        for (int i = 0; i < getColumnsAmount(); i++) {
            newRows[i] = getColumnByIndex(i);
        }

        rows = newRows;

        return this;
    }

    public Matrix multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }

        return this;
    }

    public Vector multiplyByVector(Vector vector) {
        if (vector.getSize() != getColumnsAmount()) {
            throw new IllegalArgumentException("Vector's size must be equal to matrix's columns amount. " +
                    "Vector's size = " + vector.getSize() + ", matrix's columns amount =" + getColumnsAmount());
        }

        Vector resultVector;

        resultVector = new Vector(getRowsAmount());

        for (int i = 0; i < resultVector.getSize(); i++) {
            resultVector.setComponentByIndex(i, Vector.getScalarProduct(rows[i], vector));
        }

        return resultVector;
    }

    public double getDeterminant() {
        if (getRowsAmount() != getColumnsAmount()) {
            throw new UnsupportedOperationException("Matrix must be square. " +
                    "Matrix's rows amount = " + getRowsAmount() + ", matrix's columns amount = " + getColumnsAmount());
        }

        if (getRowsAmount() == 1) {
            return rows[0].getComponentByIndex(0);
        }

        if (getRowsAmount() == 2) {
            return rows[0].getComponentByIndex(0) * rows[1].getComponentByIndex(1) -
                    rows[1].getComponentByIndex(0) * rows[0].getComponentByIndex(1);
        }

        double determinant = 0;

        for (int i = 0; i < getColumnsAmount(); i++) {
            determinant += Math.pow(-1, 2 + i) * rows[0].getComponentByIndex(i) *
                    getMatrixWithCrossedOutRowAndColumn(i).getDeterminant();
        }

        return determinant;
    }

    private Matrix getMatrixWithCrossedOutRowAndColumn(int columnIndex) {
        if (columnIndex < 0 || columnIndex >= getColumnsAmount()) {
            throw new IndexOutOfBoundsException("ColumnIndex must be 0 <= columnIndex <= columns amount. " +
                    "ColumnIndex = " + columnIndex +
                    ", matrix's columns amount = " + getColumnsAmount());
        }

        Matrix matrix = new Matrix(getRowsAmount() - 1, getColumnsAmount() - 1);

        for (int i = 1; i < getRowsAmount(); i++) {
            for (int j = 0; j < columnIndex; j++) {
                matrix.rows[i - 1].setComponentByIndex(j, rows[i].getComponentByIndex(j));
            }

            for (int j = columnIndex + 1; j < getColumnsAmount(); j++) {
                matrix.rows[i - 1].setComponentByIndex(j - 1, rows[i].getComponentByIndex(j));
            }
        }

        return matrix;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        checkForDimensionsEquality(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        checkForDimensionsEquality(matrix1, matrix2);

        Matrix resultMatrix = new Matrix(matrix1);

        resultMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getColumnsAmount() != matrix2.getRowsAmount()) {
            throw new IllegalArgumentException("Matrices' dimensions are not proper. " +
                    "Matrix1's columns amount must me equal to matrix2's rows amount. " +
                    "Matrix1's columns amount = " + matrix1.getColumnsAmount() + ", matrix2's rows amount = " + matrix2.getRowsAmount());
        }

        Matrix resultMatrix = new Matrix(matrix1.getRowsAmount(), matrix2.getColumnsAmount());
        Matrix transposedMatrix = new Matrix(matrix2);
        transposedMatrix.transpose();

        for (int i = 0; i < resultMatrix.getRowsAmount(); i++) {
            for (int j = 0; j < resultMatrix.getColumnsAmount(); j++) {
                resultMatrix.rows[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.rows[i], transposedMatrix.rows[j]));
            }
        }

        return resultMatrix;
    }

    private static void checkForDimensionsEquality(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getRowsAmount() != matrix2.getRowsAmount() || matrix1.getColumnsAmount() != matrix2.getColumnsAmount()) {
            throw new IllegalArgumentException("Matrices' dimensions must be equal. " +
                    "Matrix1's rows amount = " + matrix1.getRowsAmount() + ", matrix1's columns amount = " + matrix1.getColumnsAmount() +
                    ", matrix2's rows amount = " + matrix2.getRowsAmount() + ", matrix2's columns amount = " + matrix2.getRowsAmount());
        }
    }
}