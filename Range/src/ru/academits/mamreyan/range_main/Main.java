package ru.academits.mamreyan.range_main;

import ru.academits.mamreyan.range.Range;

public class Main {
    public static void executeMethods(Range range1, Range range2) {
        System.out.println(range2.getFrom());
        System.out.println(range2.getTo());
        System.out.println();

        Range intersection = range1.getIntersection(range2);

        if (intersection != null) {
            System.out.printf("Начало пересечения - %.1f%n", intersection.getFrom());
            System.out.printf("Конец пересечения - %.1f%n", intersection.getTo());
        } else {
            System.out.println("Нет пересечения");
        }

        System.out.println();

        Range[] integration = range1.getIntegration(range2);

        for (int i = 0; i < integration.length; i++) {
            System.out.printf("Начало %d-го фрагмента объединения - %.1f%n", i + 1, integration[i].getFrom());
            System.out.printf("Конец %d-го фрагмента объединения - %.1f%n", i + 1, integration[i].getTo());
        }

        System.out.println();

        Range[] difference = range1.getDifference(range2);

        for (int i = 0; i < difference.length; i++) {
            if (difference[i] != null) {
                System.out.printf("Начало %d-го фрагмента разности - %.1f%n", i + 1, difference[i].getFrom());
                System.out.printf("Конец %d-го фрагмента разности - %.1f%n", i + 1, difference[i].getTo());
            } else {
                System.out.println("Разность равна нулю");
            }
        }

        System.out.println();

    }

    public static void main(String[] args) {
        Range range = new Range(1.25, 10.33);

        System.out.println(range.getFrom());
        System.out.println(range.getTo());
        System.out.println(range.getLength());
        System.out.println();

        System.out.println(range.isInside(range.getFrom() - 0.0125));
        System.out.println(range.isInside(range.getFrom()));
        System.out.println(range.isInside(range.getFrom() + 0.0125));
        System.out.println(range.isInside(range.getTo() - 0.0125));
        System.out.println(range.isInside(range.getTo()));
        System.out.println(range.isInside(range.getTo() + 0.0125));
        System.out.println(range.isInside(range.getFrom() / 2));
        System.out.println(range.isInside((range.getFrom() + range.getTo()) / 2));
        System.out.println(range.isInside(range.getTo() * 2));

        System.out.println();

        range.setFrom(10.0);
        range.setTo(100.0);

        System.out.println(range.getFrom());
        System.out.println(range.getTo());
        System.out.println(range.getLength());
        System.out.println();

        System.out.println(range.isInside(range.getFrom() - 0.0999));
        System.out.println(range.isInside(range.getFrom()));
        System.out.println(range.isInside(range.getFrom() + 0.0999));
        System.out.println(range.isInside(range.getTo() - 0.0999));
        System.out.println(range.isInside(range.getTo()));
        System.out.println(range.isInside(range.getTo() + 0.0999));
        System.out.println(range.isInside(range.getFrom() / 10));
        System.out.println(range.isInside((range.getFrom() + range.getTo()) / 2));
        System.out.println(range.isInside(range.getTo() * 10));

        System.out.printf("____________________%n%n");

        // Часть 2

        System.out.printf("Часть 2%n%n");

        Range range1 = new Range(10.5, 15.5);

        System.out.println(range1.getFrom());
        System.out.println(range1.getTo());
        System.out.println();

        Range range2;

        range2 = new Range(range1.getFrom(), range1.getTo());
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() - 1, range1.getTo());
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() - 1, range1.getTo() + 1);
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom(), range1.getTo() + 1);
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() + 1, range1.getTo() + 1);
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() + 1, range1.getTo());
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() + 1, range1.getTo() - 1);
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom(), range1.getTo() - 1);
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() - 1, range1.getTo() - 1);
        executeMethods(range1, range2);

        range2 = new Range(range1.getTo(), range1.getTo() + 2);
        executeMethods(range1, range2);

        range2 = new Range(range1.getTo() + 1, range1.getTo() + 2);
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() - 2, range1.getFrom());
        executeMethods(range1, range2);

        range2 = new Range(range1.getFrom() - 2, range1.getFrom() - 1);
        executeMethods(range1, range2);
    }
}
