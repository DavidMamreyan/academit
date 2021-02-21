package ru.academits.mamreyan.shapes;

public class Rectangle extends Shapes implements Shape {
    private final double sideLength1;
    private final double sideLength2;

    public Rectangle(double sideLength1, double sideLength2) {
        this.sideLength1 = sideLength1;
        this.sideLength2 = sideLength2;
        width = Math.max(sideLength1, sideLength2);
        height = Math.min(sideLength1, sideLength2);
        area = sideLength1 * sideLength2;
        perimeter = (sideLength1 * 2) + (sideLength2 * 2);
    }

    @Override
    public final double getWidth() {
        return width;
    }

    @Override
    public final double getHeight() {
        return height;
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
        return "Rectangle " + area + " " + perimeter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Rectangle r = (Rectangle) o;
        return sideLength1 == r.sideLength1 && sideLength2 == r.sideLength2;
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int hash = 1;
        hash = (prime * hash) + Double.hashCode(width);
        hash = (prime * hash) + Double.hashCode(height);
        hash = (prime * hash) + Double.hashCode(area);
        hash = (prime * hash) + Double.hashCode(perimeter);
        hash = (prime * hash) + Double.hashCode(sideLength1);
        hash = (prime * hash) + Double.hashCode(sideLength2);
        return hash;
    }
}
