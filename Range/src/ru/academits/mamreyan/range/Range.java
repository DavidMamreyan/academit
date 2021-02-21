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

    @Override
    public final String toString() {
        return "(" + from + "; " + to + ")";
    }

    public static String convertArrayToString(Range[] array) {
        StringBuilder string = new StringBuilder("[");

        for (int i = 0; i < array.length; i++) {
            string.append(array[i].toString());

            if (i != array.length - 1) {
                string.append(", ");
            }
        }

        string.append("]");

        return string.toString();
    }

    public Range getIntersection(Range range) {
        if (to <= range.from || from >= range.to) {
            return null;
        }

        return new Range(Math.max(from, range.from), Math.min(to, range.to));
    }

    public Range[] getUnion(Range range) {
        if ((to < range.from) || (from > range.to)) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        return new Range[]{new Range(Math.min(from, range.from), Math.max(to, range.to))};
    }

    public Range[] getDifference(Range range) {
        if (from >= range.to || to <= range.from) {
            return new Range[]{new Range(from, to)};
        }

        if (from >= range.from && to <= range.to) {
            return new Range[]{};
        }

        if (from < range.from) {
            if (to > range.to) {
                return new Range[]{new Range(from, range.from), new Range(range.to, to)};
            }

            return new Range[]{new Range(from, range.from)};
        }

        return new Range[]{new Range(range.to, to)};
    }
}