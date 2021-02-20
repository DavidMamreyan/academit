package ru.academits.mamreyan.shapes;

import java.util.Comparator;

public class ShapesAreaComparator implements Comparator<Shapes> {
    @Override
    public int compare(Shapes shape1, Shapes shape2) {
        double difference = shape1.area - shape2.area;

        if (difference > 0) {
            return (int) Math.ceil(difference);
        }

        if (difference < 0) {
            return (int) Math.floor(difference);
        }

        return 0;
    }
}