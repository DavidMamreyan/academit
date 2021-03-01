package ru.academits.mamreyan.shapes_main;

import ru.academits.mamreyan.shapes.*;

import java.util.Arrays;

public class Main {
    public static Shape getMaxAreaShape(Shape[] shapesArray) {
        if (shapesArray.length == 0) {
            throw new IllegalArgumentException("shapesArray.length must be > 0");
        }

        Arrays.sort(shapesArray, new ShapesAreaComparator());

        return shapesArray[shapesArray.length - 1];
    }

    public static Shape getSecondPerimeterShape(Shape[] shapesArray) {
        if (shapesArray.length == 0) {
            throw new IllegalArgumentException("shapesArray.length must be > 0");
        }

        Arrays.sort(shapesArray, new ShapesPerimeterComparator());

        return shapesArray[shapesArray.length - 2];
    }

    public static void main(String[] args) {
        Shape[] shapesArray = new Shape[]{
                new Circle(1.1),
                new Square(3),
                new Triangle(1, 1, 2, 2, 2, 1),
                new Rectangle(1.5, 2),
                new Square(1.5),
                new Circle(2.5),
                new Triangle(0, 0, 1, 4, 4, 1),
                new Rectangle(0.5, 0.9),
                new Triangle(1, 2, 2, 4, 3, 3),
                new Square(1),
                new Circle(3.9),
                new Rectangle(2.2, 2.7)
        };

        System.out.println(Arrays.toString(shapesArray));

        System.out.println("Shape with max area is " + getMaxAreaShape(shapesArray));

        System.out.println("Shape with second max perimeter is " + getSecondPerimeterShape(shapesArray));
    }
}