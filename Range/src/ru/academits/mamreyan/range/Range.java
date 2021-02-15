package ru.academits.mamreyan.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return (number >= from) && (number <= to);
    }

    // Часть 2

    public String toString() {
        return "(" + from + "; " + to + ")";
    }

    public static void arrayToString(Range[] array) {
        System.out.print("[");

        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);

            if (i != array.length - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }

    public Range getIntersection(Range range) {
        if (to > range.from && to <= range.to) {
            return new Range(Math.max(from, range.from), to);
        }

        if (from >= range.from && from < range.to) {
            return new Range(from, Math.min(to, range.to));
        }

        if (from <= range.from && to >= range.to) {
            return new Range(range.from, range.to);
        }

        return null;
    }

    public Range[] getUnion(Range range) {
        if ((to < range.from) || (from > range.to)) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        return new Range[]{new Range(Math.min(from, range.from), Math.max(to, range.to))};
    }

    public Range[] getDifference(Range range) {
        if (from >= range.from && from < range.to) {
            if (to > range.to) {
                return new Range[]{new Range(range.to, to)};
            }

            return new Range[]{};
        }

        if (to > range.from && to <= range.to) {
            if (from < range.from) {
                return new Range[]{new Range(from, range.from)};
            }

            return new Range[]{};
        }

        if (from < range.from && to > range.to) {
            return new Range[]{new Range(from, range.from), new Range(range.to, to)};
        }

        return new Range[]{new Range(from, to)};
    }
}