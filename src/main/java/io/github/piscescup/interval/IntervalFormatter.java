package io.github.piscescup.interval;


import io.github.piscescup.interval.primitive.*;
import org.jetbrains.annotations.NotNull;

import io.github.piscescup.interval.primitive.ByteInterval;
import io.github.piscescup.interval.primitive.CharInterval;
import io.github.piscescup.interval.primitive.DoubleInterval;
import io.github.piscescup.interval.primitive.FloatInterval;
import io.github.piscescup.interval.primitive.IntInterval;
import io.github.piscescup.interval.primitive.LongInterval;
import io.github.piscescup.interval.primitive.ShortInterval;

/**
 * Utility class for converting intervals (object or primitive-based) into a human-readable string.
 *
 * <p>This formatter uses the {@link IntervalType}'s start/end symbols (e.g. {@code [}, {@code )})
 * and concatenates the interval endpoints with a fixed prefix.
 *
 * <h2>Formatting rules</h2>
 * <ul>
 *   <li>For {@link ObjectInterval}, {@link ObjectInterval#getMinimum()} and {@link ObjectInterval#getMaximum()}
 *   are used directly.</li>
 *   <li>For primitive intervals, their concrete {@code getMinimum()} / {@code getMaximum()} methods are used.</li>
 *   <li>The method does not validate emptiness/degeneracy; it only prints the endpoints and symbols.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Object interval formatting
 * ObjectInterval<String> s = ObjectInterval.of("a", "z", IntervalType.CLOSED_OPEN_INTERVAL); // assume such factory exists
 * String s1 = IntervalFormatter.format(s, IntervalType.CLOSED_OPEN_INTERVAL);
 * // "Interval: [a, z)"
 *
 * // Primitive interval formatting
 * IntInterval i = IntInterval.of(1, 3, IntervalType.OPEN_CLOSED_INTERVAL); // (1, 3]
 * String s2 = IntervalFormatter.format(i, IntervalType.OPEN_CLOSED_INTERVAL);
 * // "Int Interval: (1, 3]"
 *
 * // Or formatting by raw endpoints
 * String s3 = IntervalFormatter.format(1, 3, IntervalType.CLOSED_INTERVAL);
 * // "Int Interval: [1, 3]"
 * }</pre>
 *
 * <p><b>Note:</b> This class is a simple string builder. If you need more advanced formatting
 * (e.g. different prefixes per primitive type, locale-aware number formatting, or custom
 * endpoint formatting), consider adding a strategy-based formatter instead of adding more overloads.
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public final class IntervalFormatter {

    private IntervalFormatter() {}

    /**
     * Formats an {@link ObjectInterval} using the provided {@link IntervalType}.
     *
     * <pre>{@code
     * ObjectInterval<Integer> x = ObjectInterval.of(1, 10, IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = IntervalFormatter.format(x, IntervalType.CLOSED_OPEN_INTERVAL);
     * // "Interval: [1, 10)"
     * }</pre>
     *
     * @param interval     the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @param <V>          the endpoint type
     * @return a formatted string
     * @throws NullPointerException if {@code interval} or {@code intervalType} is {@code null}
     */
    public static <V> @NotNull String format(@NotNull ObjectInterval<V> interval, @NotNull IntervalType intervalType) {
        return "Interval: " + intervalType.startSymbol() + interval.getMinimum()
            + ", " + interval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two endpoints as an object interval using the provided {@link IntervalType}.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format("a", "z", IntervalType.OPEN_INTERVAL);
     * // "Interval: (a, z)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @param <V>          the endpoint type
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static <V> @NotNull String format(V start, V end, @NotNull IntervalType intervalType) {
        return "Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats two naturally-ordered endpoints.
     *
     * <p>This overload exists to express that {@code V} must be {@link Comparable},
     * but it performs the same string construction as {@link #format(Object, Object, IntervalType)}.
     *
     * <pre>{@code
     * IntervalFormatter f = new IntervalFormatter(); // not possible (private ctor) - see note below
     * }</pre>
     *
     * <p><b>Important:</b> This method is currently an instance method, but the class has a private
     * constructor, so it is not invokable. Consider making it {@code static} or removing it.
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @param <V>          the endpoint type
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public <V extends Comparable<? super V>> @NotNull String naturalFormat(
        V start,
        V end,
        @NotNull IntervalType intervalType
    ) {
        return "Interval: " + intervalType.startSymbol() + start + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a naturally-ordered {@link ObjectInterval}.
     *
     * <p>This overload exists to express that {@code V} must be {@link Comparable},
     * but it performs the same string construction as {@link #format(ObjectInterval, IntervalType)}.
     *
     * <p><b>Important:</b> This method is currently an instance method, but the class has a private
     * constructor, so it is not invokable. Consider making it {@code static} or removing it.
     *
     * @param interval     the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @param <V>          the endpoint type
     * @return a formatted string
     * @throws NullPointerException if {@code interval} or {@code intervalType} is {@code null}
     */
    public <V extends Comparable<? super V>> @NotNull String naturalFormat(
        @NotNull ObjectInterval<V> interval,
        @NotNull IntervalType intervalType
    ) {
        return "Interval: " + intervalType.startSymbol() + interval.getMinimum()
            + ", " + interval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats an {@link IntInterval}.
     *
     * <pre>{@code
     * IntInterval i = IntInterval.of(1, 3, IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_OPEN_INTERVAL);
     * // "Int Interval: [1, 3)"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull IntInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code int} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format(1, 3, IntervalType.OPEN_CLOSED_INTERVAL);
     * // "Int Interval: (1, 3]"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(int start, int end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a {@link ByteInterval}.
     *
     * <pre>{@code
     * ByteInterval i = ByteInterval.of((byte) 1, (byte) 3, IntervalType.CLOSED_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_INTERVAL);
     * // "Int Interval: [1, 3]"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull ByteInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code byte} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format((byte) 1, (byte) 3, IntervalType.OPEN_INTERVAL);
     * // "Int Interval: (1, 3)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(byte start, byte end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a {@link ShortInterval}.
     *
     * <pre>{@code
     * ShortInterval i = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_INTERVAL);
     * // "Int Interval: [1, 3]"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull ShortInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code short} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format((short) 1, (short) 3, IntervalType.OPEN_INTERVAL);
     * // "Int Interval: (1, 3)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(short start, short end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a {@link LongInterval}.
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 3L, IntervalType.CLOSED_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_INTERVAL);
     * // "Int Interval: [1, 3]"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull LongInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code long} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format(1L, 3L, IntervalType.OPEN_INTERVAL);
     * // "Int Interval: (1, 3)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(long start, long end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a {@link FloatInterval}.
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_INTERVAL);
     * // "Int Interval: [1.0, 3.0]"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull FloatInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code float} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format(1.0f, 3.0f, IntervalType.OPEN_INTERVAL);
     * // "Int Interval: (1.0, 3.0)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(float start, float end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a {@link DoubleInterval}.
     *
     * <pre>{@code
     * DoubleInterval i = DoubleInterval.of(1.0d, 3.0d, IntervalType.CLOSED_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_INTERVAL);
     * // "Int Interval: [1.0, 3.0]"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull DoubleInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code double} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format(1.0d, 3.0d, IntervalType.OPEN_INTERVAL);
     * // "Int Interval: (1.0, 3.0)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(double start, double end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }

    /**
     * Formats a {@link CharInterval}.
     *
     * <pre>{@code
     * CharInterval i = CharInterval.of('a', 'z', IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = IntervalFormatter.format(i, IntervalType.CLOSED_OPEN_INTERVAL);
     * // "Int Interval: [a, z)"
     * }</pre>
     *
     * @param intInterval  the interval to format
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intInterval} or {@code intervalType} is {@code null}
     */
    public static @NotNull String format(@NotNull CharInterval intInterval, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + intInterval.getMinimum()
            + ", " + intInterval.getMaximum() + intervalType.endSymbol();
    }

    /**
     * Formats two {@code char} endpoints.
     *
     * <pre>{@code
     * String s = IntervalFormatter.format('a', 'z', IntervalType.OPEN_INTERVAL);
     * // "Int Interval: (a, z)"
     * }</pre>
     *
     * @param start        the start endpoint
     * @param end          the end endpoint
     * @param intervalType the interval type used to choose endpoint symbols
     * @return a formatted string
     * @throws NullPointerException if {@code intervalType} is {@code null}
     */
    public static @NotNull String format(char start, char end, @NotNull IntervalType intervalType) {
        return "Int Interval: " + intervalType.startSymbol() + start
            + ", " + end + intervalType.endSymbol();
    }
}
