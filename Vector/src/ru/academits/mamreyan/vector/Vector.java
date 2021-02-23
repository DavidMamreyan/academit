package ru.academits.mamreyan.vector;

import java.util.Arrays;

public class Vector {
    private int n;
    private double[] componentsArray;

    public Vector(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0. n must be > 0.");
        }

        this.n = n;
        componentsArray = new double[n];

        Arrays.fill(componentsArray, 0.0);
    }

    public Vector(Vector vector) {
        if (vector == null) {
            throw new IllegalArgumentException("vector is null. vector must not be null.");
        }

        n = vector.n;
        componentsArray = new double[n];

        System.arraycopy(vector.componentsArray, 0, componentsArray, 0, n);
    }

    public Vector(double[] componentsArray) {
        if (componentsArray == null) {
            throw new IllegalArgumentException("componentsArray is null. componentsArray must not be null.");
        }

        n = componentsArray.length;
        this.componentsArray = new double[n];

        System.arraycopy(componentsArray, 0, this.componentsArray, 0, n);
    }

    public Vector(int n, double[] componentsArray) {
        if (n <= 0) {
            throw new IllegalArgumentException("n <= 0. n must be > 0.");
        }

        if (componentsArray.length > n) {
            throw new IllegalArgumentException("componentsArray.length > n. componentsArray.length must be <= n.");
        }

        this.n = n;
        this.componentsArray = new double[n];

        System.arraycopy(componentsArray, 0, this.componentsArray, 0, componentsArray.length);

        for (int i = componentsArray.length; i < n; i++) {
            this.componentsArray[i] = 0.0;
        }
    }

    public int getSize() {
        return n;
    }

    public double[] getComponentsArray() {
        return componentsArray;
    }

    public void setComponentsArray(double[] componentsArray) {
        if (componentsArray.length > this.componentsArray.length) {
            throw new IllegalArgumentException("New componentsArray.length > this.componentsArray.length. " +
                    "New componentsArray.length must be <= this.componentsArray.length.");
        }

        System.arraycopy(componentsArray, 0, this.componentsArray, 0, componentsArray.length);

        for (int i = componentsArray.length; i < this.componentsArray.length; i++) {
            this.componentsArray[i] = 0.0;
        }
    }

    public double getComponentByIndex(int i) {
        if (i >= n || i < 0) {
            throw new IllegalArgumentException("Index must be 0 <= i < n.");
        }

        return componentsArray[i];
    }

    public void setComponentByIndex(int i, double component) {
        if (i >= n || i < 0) {
            throw new IllegalArgumentException("Index must be 0 <= i < n.");
        }

        componentsArray[i] = component;
    }

    @Override
    public final String toString() {
        StringBuilder line = new StringBuilder("{");

        for (double c :
                componentsArray) {
            line.append(c).append(", ");
        }

        line.replace(line.length() - 2, line.length(), "}");

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

        Vector v = (Vector) o;
        return n == v.n && Arrays.equals(componentsArray, v.componentsArray);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = (prime * hash) + n;
        hash = (prime * hash) + Arrays.hashCode(componentsArray);
        return hash;
    }

    public static Vector addition(Vector vector1, Vector vector2) {
        Vector resultVector, lesserVector, biggerVector;

        if (vector1.n > vector2.n) {
            biggerVector = vector1;
            lesserVector = vector2;
        } else {
            biggerVector = vector2;
            lesserVector = vector1;
        }

        resultVector = new Vector(biggerVector);

        for (int i = 0; i < lesserVector.n; i++) {
            resultVector.componentsArray[i] += lesserVector.componentsArray[i];
        }

        return resultVector;
    }

    public static Vector subtraction(Vector vector1, Vector vector2) {
        Vector resultVector;

        if (vector1.n > vector2.n) {
            resultVector = new Vector(vector1);

            for (int i = 0; i < vector2.n; i++) {
                resultVector.componentsArray[i] -= vector2.componentsArray[i];
            }

        } else {
            resultVector = new Vector(Vector.reversion(vector2));

            for (int i = 0; i < vector1.n; i++) {
                resultVector.componentsArray[i] += vector1.componentsArray[i];
            }

        }

        return resultVector;
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0.0;

        for (int i = 0; i < Math.min(vector1.n, vector2.n); i++) {
            scalarProduct += vector1.componentsArray[i] * vector2.componentsArray[i];
        }

        return scalarProduct;
    }

    public static Vector reversion(Vector vector) {
        Vector resultVector = new Vector(vector);

        for (int i = 0; i < resultVector.n; i++) {
            resultVector.componentsArray[i] *= -1;
        }

        return resultVector;
    }

    public Vector addVector(Vector vector) {
        if (n >= vector.n) {
            for (int i = 0; i < vector.n; i++) {
                componentsArray[i] += vector.componentsArray[i];
            }
        } else {
            double[] tempArray = new double[componentsArray.length];
            System.arraycopy(componentsArray, 0, tempArray, 0, tempArray.length);

            componentsArray = new double[vector.componentsArray.length];
            System.arraycopy(vector.componentsArray, 0, componentsArray, 0, componentsArray.length);

            for (int i = 0; i < tempArray.length; i++) {
                componentsArray[i] += tempArray[i];
            }
        }

        return this;
    }

    public Vector subtractVector(Vector vector) {
        if (n >= vector.n) {
            for (int i = 0; i < vector.n; i++) {
                componentsArray[i] -= vector.componentsArray[i];
            }
        } else {
            double[] tempArray = new double[componentsArray.length];
            System.arraycopy(componentsArray, 0, tempArray, 0, tempArray.length);

            componentsArray = new double[vector.componentsArray.length];
            System.arraycopy(Vector.reversion(vector).componentsArray, 0, componentsArray, 0, componentsArray.length);

            for (int i = 0; i < tempArray.length; i++) {
                componentsArray[i] += tempArray[i];
            }
        }

        return this;
    }

    public Vector reverseVector() {
        for (int i = 0; i < n; i++) {
            componentsArray[i] *= -1;
        }

        return this;
    }

    public Vector multiplyVector(double scalar) {
        for (int i = 0; i < n; i++) {
            componentsArray[i] *= scalar;
        }

        return this;
    }

    public double getLength() {
        double length = 0;

        for (int i = 0; i < n; i++) {
            length += componentsArray[i] * componentsArray[i];
        }


        return Math.sqrt(length);
    }

    public Vector trimZeroComponents() { // Удалить нулевые компоненты справа
        int i = 0;
        double epsilon = 1e-5;

        for (double c :
                componentsArray) {
            if (c < epsilon && c > -epsilon) {
                i++;
            } else {
                i = 0;
            }
        }

        if (i == 0) {
            return this;
        } else if (i == n) {
            n = 1;
            componentsArray = new double[]{0};
        } else {
            n -= i;
            componentsArray = Arrays.copyOf(componentsArray, n);

        }

        return this;
    }

    public Vector appendZeroComponents(int n) { // Добавить нулевые компоненты справа
        this.n += n;
        double[] tempArray = Arrays.copyOf(componentsArray, componentsArray.length);
        componentsArray = new double[this.n];

        Arrays.fill(componentsArray, 0.0);
        System.arraycopy(tempArray, 0, componentsArray, 0, tempArray.length);

        return this;
    }
}