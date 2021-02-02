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
        return (number > from) && (number < to);
    }

    // Часть 2

    public Range getIntersection(Range range) {
        if (this.to > range.from && this.to <= range.to) {
            return new Range(Math.max(this.from, range.from), this.to);
        }

        if (this.from >= range.from && this.from < range.to) {
            return new Range(this.from, Math.min(this.to, range.to));
        }

        if (this.from <= range.from && this.to >= range.to) {
            return new Range(range.from, range.to);
        }

        return null;
    }

    public Range[] getIntegration(Range range) {
        if ((this.getIntersection(range) != null) || (this.to == range.from || this.from == range.to)) {
            return new Range[]{new Range(Math.min(this.from, range.from), Math.max(this.to, range.to))};
        }

        return new Range[]{this, range};
    }

    public Range[] getDifference(Range range) {
        if (this.from >= range.from && this.from < range.to) {
            if (this.to > range.to) {
                return new Range[]{new Range(range.to, this.to)};
            }

            return new Range[]{null};
        }

        if (this.to > range.from && this.to <= range.to) {
            if (this.from < range.from) {
                return new Range[]{new Range(this.from, range.from)};
            }

            return new Range[]{null};
        }

        if (this.from < range.from && this.to > range.to) {
            return new Range[]{new Range(this.from, range.from), new Range(range.to, this.to)};
        }

        if (this.from == range.from && this.to == range.to) {
            return new Range[]{null};
        }

        return new Range[]{this};
    }
}