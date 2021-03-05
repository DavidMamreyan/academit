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
        vectors = new Vector[matrix.getHeight()];

        vectors = Arrays.copyOf(matrix.vectors, matrix.getHeight());
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

    public int getHeight() {
        return vectors.length;
    }

    public int getLength() {
        return vectors[0].getSize();
    }

    public Vector getVectorByIndex(int index) {
        if (index < 0 || index >= getHeight()) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < height. index = " + index + ", height = " + getHeight());
        }

        return vectors[index];
    }

    public void setVectorByIndex(int index, Vector vector) {
        if (vector.getSize() != getLength()) {
            throw new IllegalArgumentException("vector's length must be equal to matrix's length. vector's length = " + vector.getSize() +
                    ", matrix's length = " + getLength());
        }

        vectors[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= getLength()) {
            throw new IndexOutOfBoundsException("index must me 0 <= index < matrix's length. index = " + index +
                    ", matrix's length = " + getLength());
        }

        Vector vector = new Vector(getHeight());

        for (int i = 0; i < getHeight(); i++) {
            vector.setComponentByIndex(i, vectors[i].getComponentByIndex(index));
        }

        return vector;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < getHeight(); i++) {
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
        return getHeight() == matrix.getHeight() && getLength() == matrix.getLength() && Arrays.equals(vectors, matrix.vectors);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(vectors);
        return hash;
    }

    public Matrix add(Matrix matrix) {
        if (getHeight() != matrix.getHeight() || getLength() != matrix.getLength()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + getHeight() + ", m1 = " + getLength() +
                    ", n2 = " + matrix.getHeight() + ", m2 = " + matrix.getLength());
        }

        for (int i = 0; i < getHeight(); i++) {
            vectors[i].add(matrix.vectors[i]);
        }


        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (getHeight() != matrix.getHeight() || getLength() != matrix.getLength()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + getHeight() + ", m1 = " + getLength() +
                    ", n2 = " + matrix.getHeight() + ", m2 = " + matrix.getLength());
        }

        for (int i = 0; i < getHeight(); i++) {
            vectors[i].subtract(matrix.vectors[i]);
        }


        return this;
    }

    public Matrix transpose() {
        Matrix tempMatrix = new Matrix(getLength(), getHeight());

        for (int i = 0; i < getLength(); i++) {
            for (int j = 0; j < getHeight(); j++) {
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

        if (getHeight() == vector.getSize()) {
            matrix = new Matrix(Matrix.getMultiplication(this, new Matrix(new Vector[]{vector})));
        } else if (getLength() == vector.getSize()) {
            matrix = new Matrix(Matrix.getMultiplication(this, new Matrix(new Vector[]{vector}).transpose()));
        } else {
            throw new IllegalArgumentException("vector's length must be equal to matrix's height or length. vector's length = " +
                    vector.getSize() + ", matrix's height = " + getHeight() + ", matrix's length =" + getLength());
        }

        vectors = matrix.vectors;

        return this;
    }

    public double getMatrixDeterminant() {
        if (getHeight() != getLength()) {
            throw new IllegalArgumentException("matrix's dimensions must be equal. height = " + getHeight() + ", length = " + getLength());
        }

        if (getHeight() == 1) {
            return vectors[0].getComponentByIndex(0);
        }

        if (getHeight() == 2) {
            return vectors[0].getComponentByIndex(0) * vectors[1].getComponentByIndex(1) -
                    vectors[1].getComponentByIndex(0) * vectors[0].getComponentByIndex(1);
        }

        double determinant = 0;

        for (int i = 0; i < getLength(); i++) {
            determinant += Math.pow(-1, 1 + (i + 1)) * vectors[0].getComponentByIndex(i) *
                    this.getMatrixWithCrossedOutStrAndCol(0, i).getMatrixDeterminant();
        }

        return determinant;
    }

    private Matrix getMatrixWithCrossedOutStrAndCol(@SuppressWarnings("SameParameterValue") int k, int l) {
        if (k < 0 || k >= getHeight() || l < 0 || l >= getLength()) {
            throw new IndexOutOfBoundsException("k must be 0 <= k < height and l must be 0 <= l <= length. k = " + k + ", l = " + l +
                    ", height = " + getHeight() + ", length = " + getLength());
        }

        Matrix matrix = new Matrix(getHeight() - 1, getLength() - 1);

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < l; j++) {
                matrix.vectors[i].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
            }

            for (int j = l + 1; j < getLength(); j++) {
                matrix.vectors[i].setComponentByIndex(j - 1, vectors[i].getComponentByIndex(j));
            }
        }

        for (int i = k + 1; i < getHeight(); i++) {
            for (int j = 0; j < l; j++) {
                matrix.vectors[i - 1].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
            }

            for (int j = l + 1; j < getLength(); j++) {
                matrix.vectors[i - 1].setComponentByIndex(j - 1, vectors[i].getComponentByIndex(j));
            }
        }

        return matrix;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getHeight() != matrix2.getHeight() || matrix1.getLength() != matrix2.getLength()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + matrix1.getHeight() + ", m1 = " +
                    matrix1.getLength() + ", n2 = " + matrix2.getHeight() + ", m2 = " + matrix2.getHeight());
        }

        Matrix resultMatrix;
        Matrix tempMatrix = new Matrix(matrix1);

        resultMatrix = tempMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getHeight() != matrix2.getHeight() || matrix1.getLength() != matrix2.getLength()) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + matrix1.getHeight() + ", m1 = " +
                    matrix1.getLength() + ", n2 = " + matrix2.getHeight() + ", m2 = " + matrix2.getHeight());
        }

        Matrix resultMatrix;
        Matrix tempMatrix = new Matrix(matrix1);

        resultMatrix = tempMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.getLength() != matrix2.getHeight()) {
            throw new IllegalArgumentException("matrices' dimensions are not proper. matrix1's length must me equal to matrix2's height. " +
                    "matrix1's length = " + matrix1.getLength() + ", matrix2's height = " + matrix2.getHeight());
        }

        Matrix resultMatrix = new Matrix(matrix1.getHeight(), matrix2.getLength());
        Matrix tempMatrix = new Matrix(matrix2);
        tempMatrix.transpose();

        for (int i = 0; i < resultMatrix.getHeight(); i++) {
            for (int j = 0; j < resultMatrix.getLength(); j++) {
                resultMatrix.vectors[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.vectors[i], tempMatrix.vectors[j]));
            }
        }

        return resultMatrix;
    }
}