package io.github.piscescup.interval;


import io.github.piscescup.interval.primitive.*;

/**
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public final class IntervalFormatter {
    private IntervalFormatter() {}


    public static <V> String format(ObjectInterval<V> interval, IntervalType intervalType) {
        return "Interval: " + intervalType.startSymbol() + interval.getMinimum()
            + ", " + interval.getMaximum() + intervalType.endSymbol();
    }

    public static <V> String format(V start, V end, IntervalType intervalType) {
        return "Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public <V extends Comparable<? super V>> String naturalFormat(V start, V end, IntervalType intervalType) {
        return "Interval: " + intervalType.startSymbol() + start + ", " + end + intervalType.endSymbol();
    }

    public <V extends Comparable<? super V>> String naturalFormat(ObjectInterval<V> interval, IntervalType intervalType) {
        return "Interval: " + intervalType.startSymbol() + interval.getMinimum()
            + ", " + interval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(IntInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(int start, int end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public static String format(ByteInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(byte start, byte end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public static String format(ShortInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(short start, short end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public static String format(LongInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(long start, long end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public static String format(FloatInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(float start, float end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public static String format(DoubleInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(double start, double end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    public static String format(CharInterval intInterval, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    public static String format(char start, char end, IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }


}
