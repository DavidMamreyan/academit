package ru.academits.mamreyan.shapes;

public class Circle extends Shapes implements Shape {
    private final double radius;

    public Circle(double radius) {
        this.radius = radius;
        width = radius * 2;
        height = radius * 2;
        area = Math.PI * radius * radius;
        perimeter = 2 * Math.PI * radius;
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
        return "Circle " + area + " " + perimeter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Circle c = (Circle) o;
        return radius == c.radius;
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int hash = 1;
        hash = (prime * hash) + Double.hashCode(width);
        hash = (prime * hash) + Double.hashCode(height);
        hash = (prime * hash) + Double.hashCode(area);
        hash = (prime * hash) + Double.hashCode(perimeter);
        hash = (prime * hash) + Double.hashCode(radius);
        return hash;
    }
}
