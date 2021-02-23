package ru.academits.mamreyan.matrix;

import ru.academits.mamreyan.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private int n;
    private int m;
    private Vector[] vectorsArray;

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        vectorsArray = new Vector[n];

        for (int i = 0; i < n; i++) {
            vectorsArray[i] = new Vector(m);
        }
    }

    public Matrix(Matrix matrix) {
        n = matrix.n;
        m = matrix.m;
        vectorsArray = new Vector[n];

        for (int i = 0; i < n; i++) {
            vectorsArray[i] = new Vector(matrix.vectorsArray[i]);
        }
    }

    public Matrix(double[][] vectorsArray) {
        n = vectorsArray.length;
        this.vectorsArray = new Vector[n];

        int maxM = 0;

        for (double[] doubles : vectorsArray) {
            if (doubles.length > maxM) {
                maxM = doubles.length;
            }
        }

        m = maxM;


        for (int i = 0; i < n; i++) {
            this.vectorsArray[i] = new Vector(vectorsArray[i]);
            this.vectorsArray[i].appendZeroComponents(m - vectorsArray[i].length);
        }
    }

    public Matrix(Vector[] vectorsArray) {
        n = vectorsArray.length;
        this.vectorsArray = new Vector[n];

        int maxM = 0;

        for (int i = 0; i < n; i++) {
            if (vectorsArray[i].getSize() > maxM) {
                maxM = vectorsArray[i].getSize();
            }
        }

        m = maxM;

        for (int i = 0; i < n; i++) {
            this.vectorsArray[i] = new Vector(vectorsArray[i]);
            this.vectorsArray[i].appendZeroComponents(m - vectorsArray[i].getSize());
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public Vector getVectorByIndex(int i) {
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("i must me 0 <= i < n");
        }

        return vectorsArray[i];
    }

    public void setVectorByIndex(int i, Vector vector) {
        if (vector.getSize() != m) {
            throw new IllegalArgumentException("vector.n is not equal to matrix.m");
        }

        vectorsArray[i] = new Vector(vector);
    }

    public Vector getColumnByIndex(int j) {
        if (j < 0 || j >= m) {
            throw new IllegalArgumentException("i must me 0 <= i < m");
        }

        Vector vector = new Vector(n);

        for (int i = 0; i < n; i++) {
            vector.setComponentByIndex(i, vectorsArray[i].getComponentByIndex(j));
        }

        return vector;
    }

    @Override
    public final String toString() {
        StringBuilder line = new StringBuilder("{");

        for (int i = 0; i < n; i++) {
            line.append(vectorsArray[i]).append(", ");
        }

        line.replace(line.length() - 2, line.length() - 1, "}");

        return line.toString();
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
        return n == matrix.n && m == matrix.m && Arrays.equals(vectorsArray, matrix.vectorsArray);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = (prime * hash) + n;
        hash = (prime * hash) + m;
        hash = (prime * hash) + Arrays.hashCode(vectorsArray);
        return hash;
    }

    public Matrix transpose() {
        Matrix tempMatrix = new Matrix(m, n);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                tempMatrix.vectorsArray[i].setComponentByIndex(j, vectorsArray[j].getComponentByIndex(i));
            }
        }

        n = tempMatrix.n;
        m = tempMatrix.m;
        vectorsArray = new Vector[n];

        for (int i = 0; i < n; i++) {
            vectorsArray[i] = new Vector(tempMatrix.vectorsArray[i]);
        }

        return this;
    }

    public Matrix multiplyByScalar(double n) {
        for (int i = 0; i < n; i++) {
            vectorsArray[i] = vectorsArray[i].multiplyVector(n);
        }

        return this;
    }

    private Matrix getMatrixWithCrossedOutStrAndCol(@SuppressWarnings("SameParameterValue") int k, int l) {
        if (k >= n || l >= m) {
            throw new IllegalArgumentException("k must be 0 <= k < n and l must be 0 <= l <= m");
        }

        Matrix matrix = new Matrix(n - 1, m - 1);

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m - 1; j++) {
                if (i >= k) {
                    if (j >= l) {
                        matrix.vectorsArray[i].setComponentByIndex(j, vectorsArray[i + 1].getComponentByIndex(j + 1));
                    } else {
                        matrix.vectorsArray[i].setComponentByIndex(j, vectorsArray[i + 1].getComponentByIndex(j));
                    }
                } else if (j >= l) {
                    matrix.vectorsArray[i].setComponentByIndex(j, vectorsArray[i].getComponentByIndex(j + 1));
                } else {
                    matrix.vectorsArray[i].setComponentByIndex(j, vectorsArray[i].getComponentByIndex(j));

                }
            }
        }

        return matrix;
    }

    public double getMatrixDeterminant() {
        if (n != m) {
            throw new IllegalArgumentException("matrix dimensions must be equal");
        }

        if (n == 1) {
            return vectorsArray[0].getComponentByIndex(0);
        }

        if (n == 2) {
            return vectorsArray[0].getComponentByIndex(0) * vectorsArray[1].getComponentByIndex(1) -
                    vectorsArray[1].getComponentByIndex(0) * vectorsArray[0].getComponentByIndex(1);
        }

        double determinant = 0;

        for (int i = 0; i < m; i++) {
            determinant += Math.pow(-1, 1 + (i + 1)) * vectorsArray[0].getComponentByIndex(i) *
                    this.getMatrixWithCrossedOutStrAndCol(0, i).getMatrixDeterminant();
        }

        return determinant;
    }

    public Matrix multiplyByVector(Vector vector) {
        Matrix matrix;

        if (n == vector.getSize()) {
            matrix = new Matrix(Matrix.multiplication(this, new Matrix(new Vector[]{vector})));
        } else if (m == vector.getSize()) {
            matrix = new Matrix(Matrix.multiplication(this, new Matrix(new Vector[]{vector}).transpose()));
        } else {
            throw new IllegalArgumentException("matrix n or m must be equal to vector.n");
        }

        n = matrix.n;
        m = matrix.m;
        vectorsArray = new Vector[n];

        System.arraycopy(matrix.vectorsArray, 0, vectorsArray, 0, n);

        return this;
    }

    public Matrix addMatrix(Matrix matrix) {
        if (n != matrix.n || m != matrix.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal");
        }

        for (int i = 0; i < n; i++) {
            vectorsArray[i] = Vector.addition(vectorsArray[i], matrix.vectorsArray[i]);
        }


        return this;
    }

    public Matrix subtractMatrix(Matrix matrix) {
        if (n != matrix.n || m != matrix.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal");
        }

        for (int i = 0; i < n; i++) {
            vectorsArray[i] = Vector.subtraction(vectorsArray[i], matrix.vectorsArray[i]);
        }


        return this;
    }

    public static Matrix addition(Matrix matrix1, Matrix matrix2) {
        if (matrix1.n != matrix2.n || matrix1.m != matrix2.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal");
        }

        Matrix resultMatrix = new Matrix(matrix1.n, matrix1.m);

        for (int i = 0; i < resultMatrix.n; i++) {
            resultMatrix.vectorsArray[i] = Vector.addition(matrix1.vectorsArray[i], matrix2.vectorsArray[i]);
        }

        return resultMatrix;
    }

    public static Matrix subtraction(Matrix matrix1, Matrix matrix2) {
        if (matrix1.n != matrix2.n || matrix1.m != matrix2.m) {
            throw new IllegalArgumentException("matrices' dimensions are not equal");
        }

        Matrix resultMatrix = new Matrix(matrix1.n, matrix1.m);

        for (int i = 0; i < resultMatrix.n; i++) {
            resultMatrix.vectorsArray[i] = Vector.subtraction(matrix1.vectorsArray[i], matrix2.vectorsArray[i]);
        }

        return resultMatrix;
    }

    public static Matrix multiplication(Matrix matrix1, Matrix matrix2) {
        if (matrix1.m != matrix2.n) {
            throw new IllegalArgumentException("matrices' dimensions are not proper. matrix1.m must me equal to matrix2.n");
        }

        Matrix resultMatrix = new Matrix(matrix1.n, matrix2.m);
        matrix2.transpose();

        for (int i = 0; i < resultMatrix.n; i++) {
            for (int j = 0; j < resultMatrix.m; j++) {
                resultMatrix.vectorsArray[i].setComponentByIndex(j, Vector.scalarProduct(matrix1.vectorsArray[i], matrix2.vectorsArray[j]));
            }
        }

        matrix2.transpose();

        return resultMatrix;
    }
}