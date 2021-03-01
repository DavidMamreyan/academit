package ru.academits.mamreyan.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be > 0. length = " + length);
        }

        components = new double[length];
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.getSize());
    }

    public Vector(double[] components) {
        if (components.length == 0) {
            throw new IllegalArgumentException("components.length cannot be 0");
        }

        this.components = Arrays.copyOf(components, components.length);
    }

    public Vector(int length, double[] components) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be > 0. length = " + length);
        }

        if (components.length > length) {
            throw new IllegalArgumentException("components.length must be <= length. components.length = " + components.length +
                    ", length = " + length);
        }

        this.components = Arrays.copyOf(components, length);
    }

    @Override
    public String toString() {
        StringBuilder line = new StringBuilder("{");

        for (double c : components) {
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
            throw new IndexOutOfBoundsException("index must be 0 <= index < components.length. index = " + index);
        }

        return components[index];
    }

    public void setComponentByIndex(int index, double component) {
        if (index < 0 || index >= components.length) {
            throw new IndexOutOfBoundsException("index must be 0 <= index < components.length. index = " + index);
        }

        components[index] = component;
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector resultVector;
        Vector tempVector = new Vector(vector1);

        resultVector = tempVector.add(vector2);

        return resultVector;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector resultVector;
        Vector tempVector = new Vector(vector1);

        resultVector = tempVector.subtract(vector2);

        return resultVector;
    }

    public static Vector getReversedVector(Vector vector) {
        Vector resultVector = new Vector(vector);

        resultVector.reverse();

        return resultVector;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0.0;

        for (int i = 0, minSize = Math.min(vector1.getSize(), vector2.getSize()); i < minSize; i++) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }

    public Vector add(Vector vector) {
        int appendixLength = 0;

        if (getSize() > vector.getSize()) {
            appendixLength = getSize() - vector.getSize();
            vector.appendZeroComponents(appendixLength);
        } else if (getSize() < vector.getSize()) {
            this.appendZeroComponents(vector.getSize() - getSize());
        }

        for (int i = 0, size = getSize(); i < size; i++) {
            components[i] += vector.components[i];
        }

        vector.trimZeroComponents(appendixLength);

        return this;
    }

    public Vector subtract(Vector vector) {
        int appendixLength = 0;

        if (getSize() > vector.getSize()) {
            appendixLength = getSize() - vector.getSize();
            vector.appendZeroComponents(appendixLength);
        } else if (getSize() < vector.getSize()) {
            this.appendZeroComponents(vector.getSize() - getSize());
        }

        for (int i = 0, size = getSize(); i < size; i++) {
            components[i] -= vector.components[i];
        }

        vector.trimZeroComponents(appendixLength);

        return this;
    }

    public Vector reverse() {
        this.multiplyByScalar(-1);

        return this;
    }

    public Vector multiplyByScalar(double scalar) {
        for (int i = 0, size = getSize(); i < size; i++) {
            components[i] *= scalar;
        }

        return this;
    }

    public double getLength() {
        double temp = 0;

        for (double e :
                components) {
            temp += e * e;
        }

        return Math.sqrt(temp);
    }

    public Vector trimZeroComponents(int n) { // Удалить нулевые компоненты справа
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0. n = " + n);
        }

        int i = 0;
        final double epsilon = 1e-5;

        for (double c : components) {
            if (c < epsilon && c > -epsilon) {
                i++;
            } else {
                i = 0;
            }
        }

        if (i == 0) {
            return this;
        }

        if (n >= i) {
            if (i == getSize()) {
                components = new double[]{0.0};
            } else {
                components = Arrays.copyOf(components, getSize() - i);
            }
        } else {
            components = Arrays.copyOf(components, getSize() - n);
        }

        return this;
    }

    public Vector trimZeroComponents() {
        trimZeroComponents(getSize());

        return this;
    }

    public Vector appendZeroComponents(int n) { // Добавить нулевые компоненты справа
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0. n = " + n);
        }

        components = Arrays.copyOf(components, components.length + n);

        return this;
    }
}