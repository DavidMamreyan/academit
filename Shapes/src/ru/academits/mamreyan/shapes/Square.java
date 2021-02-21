package ru.academits.mamreyan.shapes;

public class Square extends Shapes implements Shape {
    private double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
        area = sideLength * sideLength;
        perimeter = sideLength * 4;
    }


    @Override
    public final double getWidth() {
        return sideLength;
    }

    @Override
    public final double getHeight() {
        return sideLength;
    }

    @Override
    public final double getArea() {
        return area;
    }

    @Override
    public final double getPerimeter() {
        return perimeter;
    }

    @Override
    public final String toString() {
        return "Square " + area + " " + perimeter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Square s = (Square) o;
        return sideLength == s.sideLength;
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int hash = 1;
        hash = (prime * hash) + Double.hashCode(width);
        hash = (prime * hash) + Double.hashCode(height);
        hash = (prime * hash) + Double.hashCode(area);
        hash = (prime * hash) + Double.hashCode(perimeter);
        hash = (prime * hash) + Double.hashCode(sideLength);
        return hash;
    }
}