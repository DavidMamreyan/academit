package ru.academits.mamreyan.shapes;

import java.util.Comparator;

public class ShapesPerimeterComparator implements Comparator<Shapes> {
    @Override
    public int compare(Shapes shape1, Shapes shape2) {
        double difference = shape1.perimeter - shape2.perimeter;

        if (difference > 0) {
            return (int) Math.ceil(difference);
        }

        if (difference < 0) {
            return (int) Math.floor(difference);
        }

        return 0;
    }
}