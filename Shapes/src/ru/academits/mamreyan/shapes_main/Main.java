package ru.academits.mamreyan.shapes_main;

import ru.academits.mamreyan.shapes.*;

import java.util.Arrays;

public class Main {
    public static Shapes getMaxAreaShape(Shapes[] shapesArray) {
        Arrays.sort(shapesArray, new ShapesAreaComparator());

        return shapesArray[shapesArray.length - 1];
    }

    public static Shapes getSecondPerimeterShape(Shapes[] shapesArray) {
        Arrays.sort(shapesArray, new ShapesPerimeterComparator());

        return shapesArray[shapesArray.length - 2];
    }

    public static void main(String[] args) {
        Square square1 = new Square(1);
        Square square2 = new Square(1.5);
        Square square3 = new Square(3);

        Triangle triangle1 = new Triangle(1, 1, 2, 2, 2, 1);
        Triangle triangle2 = new Triangle(1, 2, 2, 4, 3, 3);
        Triangle triangle3 = new Triangle(0, 0, 1, 4, 4, 1);

        Rectangle rectangle1 = new Rectangle(0.5, 0.9);
        Rectangle rectangle2 = new Rectangle(1.5, 2);
        Rectangle rectangle3 = new Rectangle(2.2, 2.7);

        Circle circle1 = new Circle(1.1);
        Circle circle2 = new Circle(2.5);
        Circle circle3 = new Circle(3.9);

        Shapes[] shapesArray = new Shapes[]{circle1, square3, triangle1, rectangle2,
                square2, circle2, triangle3, rectangle1,
                triangle2, square1, circle3, rectangle3};

        System.out.println(Arrays.toString(shapesArray));

        System.out.println(getMaxAreaShape(shapesArray));

        System.out.println(getSecondPerimeterShape(shapesArray));
    }
}