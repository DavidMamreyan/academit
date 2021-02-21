package ru.academits.mamreyan.shapes;

public class Triangle extends Shapes implements Shape {
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;

    private final double a;
    private final double b;
    private final double c;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        a = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        b = Math.sqrt(Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2));
        c = Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));
        width = Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
        height = Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
        perimeter = a + b + c;
        double halfPerimeter = perimeter / 2;
        area = Math.sqrt(halfPerimeter * (halfPerimeter - a) * (halfPerimeter - b) * (halfPerimeter - c));
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
        return "Triangle " + area + " " + perimeter;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Triangle t = (Triangle) o;
        return x1 == t.x1 && y1 == t.y1 && x2 == t.x2 && y2 == t.y2 && x3 == t.x3 && y3 == t.y3;
    }

    @Override
    public int hashCode() {
        final int prime = 13;
        int hash = 1;
        hash = (prime * hash) + Double.hashCode(width);
        hash = (prime * hash) + Double.hashCode(height);
        hash = (prime * hash) + Double.hashCode(area);
        hash = (prime * hash) + Double.hashCode(perimeter);
        hash = (prime * hash) + Double.hashCode(x1);
        hash = (prime * hash) + Double.hashCode(y1);
        hash = (prime * hash) + Double.hashCode(x2);
        hash = (prime * hash) + Double.hashCode(y2);
        hash = (prime * hash) + Double.hashCode(x3);
        hash = (prime * hash) + Double.hashCode(y3);
        hash = (prime * hash) + Double.hashCode(a);
        hash = (prime * hash) + Double.hashCode(b);
        hash = (prime * hash) + Double.hashCode(c);
        return hash;
    }
}