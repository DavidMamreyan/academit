package ru.academits.mamreyan.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0. size = " + size);
        }

        components = new double[size];
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }


    public Vector(int size, Vector vector) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0. size = " + size);
        }

        components = Arrays.copyOf(vector.components, size);
    }

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("arrays length cannot be 0");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int size, double[] components) {
        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0. size = " + size);
        }

        this.components = Arrays.copyOf(components, size);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (double c : components) {
            stringBuilder.append(c).append(", ");
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

        Vector v = (Vector) o;
        return Arrays.equals(components, v.components);
    }

    @Override
    public int hashCode() {
        final int prime = 3;
        int hash = 1;
        hash = prime * hash + Arrays.hashCode(components);
        return hash;
    }

    public int getSize() {
        return components.length;
    }

    public double getComponentByIndex(int index) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < vector's size. index = " + index +
                    ", vector's size = " + components.length);
        }

        return components[index];
    }

    public void setComponentByIndex(int index, double component) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < vector's size. index = " + index +
                    ", vector's size = " + components.length);
        }

        components[index] = component;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);

        resultVector = resultVector.add(vector2);

        return resultVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector resultVector = new Vector(vector1);

        resultVector = resultVector.subtract(vector2);

        return resultVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0.0;

        int minSize = Math.min(vector1.components.length, vector2.components.length);

        for (int i = 0; i < minSize; i++) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }

    public Vector add(Vector vector) {
        if (components.length < vector.components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; i++) {
            components[i] += vector.components[i];
        }

        return this;
    }

    public Vector subtract(Vector vector) {
        if (components.length < vector.components.length) {
            components = Arrays.copyOf(components, vector.components.length);
        }

        for (int i = 0; i < vector.components.length; i++) {
            components[i] -= vector.components[i];
        }

        return this;
    }

    public Vector reverse() {
        multiplyByScalar(-1);

        return this;
    }

    public Vector multiplyByScalar(double scalar) {
        for (int i = 0, size = components.length; i < size; i++) {
            components[i] *= scalar;
        }

        return this;
    }

    public double getLength() {
        double sum = 0;

        for (double e : components) {
            sum += e * e;
        }

        return Math.sqrt(sum);
    }
}