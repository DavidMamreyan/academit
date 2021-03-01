package ru.academits.mamreyan.matrix;

import ru.academits.mamreyan.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private int n;
    private int m;
    private Vector[] vectors;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        vectors = new Vector[n];

        for (int i = 0; i < n; i++) {
            vectors[i] = new Vector(m);
        }
    }

    public Matrix(Matrix matrix) {
        n = matrix.n;
        m = matrix.m;
        vectors = new Vector[n];

        vectors = Arrays.copyOf(matrix.vectors, n);
    }

    public Matrix(double[][] vectors) {
        n = vectors.length;
        this.vectors = new Vector[n];

        int maxM = 0;

        for (double[] doubles : vectors) {
            if (doubles.length > maxM) {
                maxM = doubles.length;
            }
        }

        m = maxM;

        for (int i = 0; i < n; i++) {
            this.vectors[i] = new Vector(vectors[i]);
            this.vectors[i].appendZeroComponents(m - vectors[i].length);
        }
    }

    public Matrix(Vector[] vectors) {
        n = vectors.length;
        this.vectors = new Vector[n];

        int maxM = 0;

        for (int i = 0; i < n; i++) {
            if (vectors[i].getSize() > maxM) {
                maxM = vectors[i].getSize();
            }
        }

        m = maxM;

        for (int i = 0; i < n; i++) {
            this.vectors[i] = new Vector(vectors[i]);
            this.vectors[i].appendZeroComponents(m - vectors[i].getSize());
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Vector getVectorByIndex(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("index must me 0 <= index < n. index = " + index + ", n = " + n);
        }

        return vectors[index];
    }

    public void setVectorByIndex(int index, Vector vector) {
        if (vector.getSize() != m) {
            throw new IllegalArgumentException("vector's length must be equal to m. vector's length = " + vector.getSize() +
                    ", m = " + m);
        }

        vectors[index] = new Vector(vector);
    }

    public Vector getColumnByIndex(int index) {
        if (index < 0 || index >= m) {
            throw new IndexOutOfBoundsException("index must me 0 <= index < m. index = " + index + ", m = " + m);
        }

        Vector vector = new Vector(n);

        for (int i = 0; i < n; i++) {
            vector.setComponentByIndex(i, vectors[i].getComponentByIndex(index));
        }

        return vector;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < n; i++) {
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
        return n == matrix.n && m == matrix.m && Arrays.equals(vectors, matrix.vectors);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = (prime * hash) + n;
        hash = (prime * hash) + m;
        hash = (prime * hash) + Arrays.hashCode(vectors);
        return hash;
    }

    public Matrix add(Matrix matrix) {
        if (n != matrix.n || m != matrix.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + n + ", m1 = " + m +
                    ", n2 = " + matrix.n + ", m2 = " + matrix.m);
        }

        for (int i = 0; i < n; i++) {
            vectors[i].add(matrix.vectors[i]);
        }


        return this;
    }

    public Matrix subtract(Matrix matrix) {
        if (n != matrix.n || m != matrix.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + n + ", m1 = " + m +
                    ", n2 = " + matrix.n + ", m2 = " + matrix.m);
        }

        for (int i = 0; i < n; i++) {
            vectors[i].subtract(matrix.vectors[i]);
        }


        return this;
    }

    public Matrix transpose() {
        Matrix tempMatrix = new Matrix(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tempMatrix.vectors[i].setComponentByIndex(j, vectors[j].getComponentByIndex(i));
            }
        }

        n = tempMatrix.n;
        m = tempMatrix.m;
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

        if (n == vector.getSize()) {
            matrix = new Matrix(Matrix.getMultiplication(this, new Matrix(new Vector[]{vector})));
        } else if (m == vector.getSize()) {
            matrix = new Matrix(Matrix.getMultiplication(this, new Matrix(new Vector[]{vector}).transpose()));
        } else {
            throw new IllegalArgumentException("vector's length must be equal to matrix's n or m. vector's length = " + vector.getSize() +
                    ", n = " + n + ", m =" + m);
        }

        n = matrix.n;
        m = matrix.m;
        vectors = matrix.vectors;

        return this;
    }

    public double getMatrixDeterminant() {
        if (n != m) {
            throw new IllegalArgumentException("matrix's dimensions must be equal. n = " + n + ", m = " + m);
        }

        if (n == 1) {
            return vectors[0].getComponentByIndex(0);
        }

        if (n == 2) {
            return vectors[0].getComponentByIndex(0) * vectors[1].getComponentByIndex(1) -
                    vectors[1].getComponentByIndex(0) * vectors[0].getComponentByIndex(1);
        }

        double determinant = 0;

        for (int i = 0; i < m; i++) {
            determinant += Math.pow(-1, 1 + (i + 1)) * vectors[0].getComponentByIndex(i) *
                    this.getMatrixWithCrossedOutStrAndCol(0, i).getMatrixDeterminant();
        }

        return determinant;
    }

    private Matrix getMatrixWithCrossedOutStrAndCol(@SuppressWarnings("SameParameterValue") int k, int l) {
        if (k < 0 || k >= n || l < 0 || l >= m) {
            throw new IndexOutOfBoundsException("k must be 0 <= k < n and l must be 0 <= l <= m. k = " + k + ", l = " + l +
                    ", n = " + n + ", m = " + m);
        }

        Matrix matrix = new Matrix(n - 1, m - 1);

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < l; j++) {
                matrix.vectors[i].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
            }

            for (int j = l + 1; j < m; j++) {
                matrix.vectors[i].setComponentByIndex(j - 1, vectors[i].getComponentByIndex(j));
            }
        }

        for (int i = k + 1; i < n; i++) {
            for (int j = 0; j < l; j++) {
                matrix.vectors[i - 1].setComponentByIndex(j, vectors[i].getComponentByIndex(j));
            }

            for (int j = l + 1; j < m; j++) {
                matrix.vectors[i - 1].setComponentByIndex(j - 1, vectors[i].getComponentByIndex(j));
            }
        }

        return matrix;
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.n != matrix2.n || matrix1.m != matrix2.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal. n1 = " + matrix1.n + ", m1 = " + matrix1.m +
                    ", n2 = " + matrix2.n + ", m2 = " + matrix2.m);
        }

        Matrix resultMatrix;
        Matrix tempMatrix = new Matrix(matrix1);

        resultMatrix = tempMatrix.add(matrix2);

        return resultMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.n != matrix2.n || matrix1.m != matrix2.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal");
        }

        Matrix resultMatrix;
        Matrix tempMatrix = new Matrix(matrix1);

        resultMatrix = tempMatrix.subtract(matrix2);

        return resultMatrix;
    }

    public static Matrix getMultiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.m != matrix2.n) {
            throw new IllegalArgumentException("matrices' dimensions are not proper. matrix1.m must me equal to matrix2.n. " +
                    "matrix1.m = " + matrix1.m + ", matrix2.n = " + matrix2.n);
        }

        Matrix resultMatrix = new Matrix(matrix1.n, matrix2.m);
        Matrix tempMatrix = new Matrix(matrix2);
        tempMatrix.transpose();

        for (int i = 0; i < resultMatrix.n; i++) {
            for (int j = 0; j < resultMatrix.m; j++) {
                resultMatrix.vectors[i].setComponentByIndex(j, Vector.getScalarProduct(matrix1.vectors[i], tempMatrix.vectors[j]));
            }
        }

        return resultMatrix;
    }
}