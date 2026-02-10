package io.github.piscescup.interval.primitive;

import io.github.piscescup.exception.IllegalMathIntervalEndpointException;
import io.github.piscescup.interval.EmptyInterval;
import io.github.piscescup.interval.IntervalFormatter;
import io.github.piscescup.interval.IntervalType;
import io.github.piscescup.interval.ObjectInterval;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;

/**
 * A primitive {@code float}-based interval with explicit endpoint inclusiveness.
 *
 * <p>This interval is defined by:
 * <ul>
 *   <li>a minimum (start) bound {@link #getMinimum()}</li>
 *   <li>a maximum (end) bound {@link #getMaximum()}</li>
 *   <li>an {@link IntervalType} describing whether endpoints are inclusive</li>
 * </ul>
 *
 * <p>Ordering uses {@link Float#compare(float, float)} (i.e. the comparator is
 * {@link Comparator#naturalOrder()}). This includes Java's defined ordering for
 * special values such as {@code NaN} and signed zeros.
 *
 * <h2>Validity</h2>
 * <p>An instance is constructed only when {@code minimum <= maximum} under
 * {@link Float#compare(float, float)}. If {@code minimum > maximum},
 * {@link IllegalMathIntervalEndpointException} is thrown.
 *
 * <h2>Empty interval</h2>
 * <p>When {@code minimum == maximum} (as determined by {@link Float#compare(float, float) == 0}),
 * the interval is empty unless it is closed on both ends (i.e. {@code [x, x]}).
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Create intervals with different endpoint types
 * FloatInterval closed    = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_INTERVAL);       // [1.0, 3.0]
 * FloatInterval open      = FloatInterval.of(1.0f, 3.0f, IntervalType.OPEN_INTERVAL);         // (1.0, 3.0)
 * FloatInterval openEnd   = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_OPEN_INTERVAL);  // [1.0, 3.0)
 * FloatInterval openStart = FloatInterval.of(1.0f, 3.0f, IntervalType.OPEN_CLOSED_INTERVAL);  // (1.0, 3.0]
 *
 * // Membership
 * closed.contains(1.0f); // true
 * open.contains(1.0f);   // false
 *
 * // Degenerate / empty
 * FloatInterval pointClosed = FloatInterval.of(5.0f, 5.0f, IntervalType.CLOSED_INTERVAL); // [5.0, 5.0]
 * pointClosed.isEmpty();      // false
 * pointClosed.isDegenerate(); // true
 * pointClosed.contains(5.0f); // true
 *
 * FloatInterval pointOpen = FloatInterval.of(5.0f, 5.0f, IntervalType.OPEN_INTERVAL); // (5.0, 5.0)
 * pointOpen.isEmpty(); // true
 *
 * // Overlap and intersection
 * FloatInterval a = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_INTERVAL); // [1.0, 3.0]
 * FloatInterval b = FloatInterval.of(3.0f, 8.0f, IntervalType.CLOSED_INTERVAL); // [3.0, 8.0]
 * a.overlaps(b); // true (touch at 3.0 and both inclusive at the touching endpoints)
 *
 * Optional<FloatInterval> inter = a.intersection(b); // Optional[[3.0, 3.0]]
 * inter.isPresent(); // true
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public class FloatInterval implements PrimitiveInterval, Serializable {

    @Serial
    private static final long serialVersionUID = 14889511478L;

    /**
     * The minimum (start) endpoint of this interval.
     */
    private final float minimum;
    /**
     * The maximum (end) endpoint of this interval.
     */
    private final float maximum;
    /**
     * The interval type describing endpoint inclusiveness.
     */
    private final IntervalType intervalType;

    /**
     * Creates a {@code FloatInterval} with the given bounds and endpoint type.
     *
     * <p>The interval is valid only when {@code minimum <= maximum} under
     * {@link Float#compare(float, float)}.
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 10.0f, IntervalType.CLOSED_OPEN_INTERVAL); // [1.0, 10.0)
     * }</pre>
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     * @return a new {@code FloatInterval}
     * @throws NullPointerException                 if {@code intervalType} is {@code null}
     * @throws IllegalMathIntervalEndpointException if {@code minimum > maximum}
     */
    @Contract("_, _, _ -> new")
    public static @NotNull FloatInterval of(float minimum, float maximum, IntervalType intervalType) {
        NullCheck.requireNonNull(intervalType);
        if (Float.compare(minimum, maximum) > 0) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }
        return new FloatInterval(minimum, maximum, intervalType);
    }

    /**
     * Constructs a new {@code FloatInterval}.
     *
     * <p>Callers should prefer {@link #of(float, float, IntervalType)} which performs
     * validation.
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     */
    private FloatInterval(float minimum, float maximum, IntervalType intervalType) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.intervalType = intervalType;
    }

    /**
     * Returns the minimum (start) endpoint.
     *
     * @return the minimum bound
     */
    public float getMinimum() {
        return minimum;
    }

    /**
     * Returns the maximum (end) endpoint.
     *
     * @return the maximum bound
     */
    public float getMaximum() {
        return maximum;
    }

    /**
     * Returns the interval type describing endpoint inclusiveness.
     *
     * @return the interval type
     */
    @Override
    public IntervalType getIntervalType() {
        return intervalType;
    }

    /**
     * Returns the comparator used by this interval.
     *
     * <p>The comparator is always the natural order.
     *
     * @return {@link Comparator#naturalOrder()}
     */
    public @NotNull Comparator<Float> getComparator() {
        return Comparator.naturalOrder();
    }

    /**
     * Returns {@code true} if {@code minimum == maximum} under {@link Float#compare(float, float)}.
     *
     * <p>Note that a degenerate interval may still be empty depending on endpoint
     * inclusiveness (see {@link #isEmpty()}).
     *
     * @return {@code true} if the endpoints are equal under {@code Float.compare}
     */
    public boolean isDegenerate() {
        return Float.compare(maximum, minimum) == 0;
    }

    /**
     * Returns whether the given value is exactly on the start endpoint and the start is inclusive.
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_OPEN_INTERVAL); // [1.0, 3.0)
     * i.onStartEndpoint(1.0f); // true
     * i.onStartEndpoint(2.0f); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} equals {@code minimum} and start is inclusive
     */
    public boolean onStartEndpoint(float value) {
        return isStartInclusive() && Float.compare(minimum, value) == 0;
    }

    /**
     * Returns whether the given value is exactly on the end endpoint and the end is inclusive.
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 3.0f, IntervalType.OPEN_CLOSED_INTERVAL); // (1.0, 3.0]
     * i.onEndEndpoint(3.0f); // true
     * i.onEndEndpoint(2.0f); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} equals {@code maximum} and end is inclusive
     */
    public boolean onEndEndpoint(float value) {
        return isEndInclusive() && Float.compare(maximum, value) == 0;
    }

    /**
     * Returns {@code true} if the given value is contained in this interval.
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 3.0f, IntervalType.OPEN_CLOSED_INTERVAL); // (1.0, 3.0]
     * i.contains(1.0f); // false
     * i.contains(2.0f); // true
     * i.contains(3.0f); // true
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} is inside the interval with respect to endpoint rules
     */
    public boolean contains(float value) {
        int cs = Float.compare(value, minimum);
        int ce = Float.compare(value, maximum);

        boolean afterStart = isStartInclusive() ? cs >= 0 : cs > 0;
        boolean beforeEnd = isEndInclusive() ? ce <= 0 : ce < 0;

        return afterStart && beforeEnd;
    }

    /**
     * Returns {@code true} if this interval completely contains {@code other}.
     *
     * <p>If {@code other} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * FloatInterval outer = FloatInterval.of(1.0f, 10.0f, IntervalType.CLOSED_INTERVAL); // [1.0,10.0]
     * FloatInterval inner = FloatInterval.of(3.0f, 7.0f, IntervalType.OPEN_INTERVAL);    // (3.0,7.0)
     * outer.containsInterval(inner); // true
     * inner.containsInterval(outer); // false
     * }</pre>
     *
     * @param other the interval to test
     * @return {@code true} if {@code other} lies within this interval
     */
    public boolean containsInterval(FloatInterval other) {
        if (other == null) return false;

        int startCmp = Float.compare(other.minimum, this.minimum);
        boolean startOk =
            startCmp > 0 ||
                (startCmp == 0 && (!other.isStartInclusive() || isStartInclusive()));

        int endCmp = Float.compare(other.maximum, this.maximum);
        boolean endOk =
            endCmp < 0 ||
                (endCmp == 0 && (!other.isEndInclusive() || isEndInclusive()));

        return startOk && endOk;
    }

    /**
     * Returns {@code true} if this interval is fully contained by {@code other}.
     *
     * <p>If {@code other} is {@code null}, this method returns {@code false}.
     *
     * @param other the interval to test against
     * @return {@code true} if this interval is contained by {@code other}
     */
    public boolean isContainedBy(FloatInterval other) {
        if (other == null) return false;
        return other.containsInterval(this);
    }

    /**
     * Returns {@code true} if this interval is empty.
     *
     * <p>When {@code minimum < maximum} under {@link Float#compare(float, float)},
     * the interval is never empty. When {@code minimum == maximum}, the interval is empty
     * unless both endpoints are inclusive.
     *
     * <pre>{@code
     * FloatInterval a = FloatInterval.of(5.0f, 5.0f, IntervalType.CLOSED_INTERVAL); // [5.0,5.0]
     * a.isEmpty(); // false
     *
     * FloatInterval b = FloatInterval.of(5.0f, 5.0f, IntervalType.OPEN_CLOSED_INTERVAL); // (5.0,5.0]
     * b.isEmpty(); // true
     * }</pre>
     *
     * @return {@code true} if this interval contains no values
     */
    public boolean isEmpty() {
        int c = Float.compare(minimum, maximum);
        if (c > 0) return true;
        if (c < 0) return false;
        return !(isStartInclusive() && isEndInclusive());
    }

    /**
     * Returns {@code true} if this interval overlaps with {@code other}.
     *
     * <p>If either interval is empty, this method returns {@code false}.
     * Endpoint touching counts as overlap only when the touching endpoints are inclusive.
     *
     * <pre>{@code
     * FloatInterval a = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_INTERVAL); // [1.0,3.0]
     * FloatInterval b = FloatInterval.of(3.0f, 8.0f, IntervalType.CLOSED_INTERVAL); // [3.0,8.0]
     * a.overlaps(b); // true
     *
     * FloatInterval c = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_OPEN_INTERVAL); // [1.0,3.0)
     * FloatInterval d = FloatInterval.of(3.0f, 8.0f, IntervalType.CLOSED_INTERVAL);      // [3.0,8.0]
     * c.overlaps(d); // false (touch at 3.0, but c excludes 3.0)
     * }</pre>
     *
     * @param other the other interval
     * @return {@code true} if the intervals overlap
     */
    public boolean overlaps(FloatInterval other) {
        if (other == null) return false;
        if (this.isEmpty() || other.isEmpty()) return false;

        int sCmp = Float.compare(this.minimum, other.maximum);
        if (sCmp > 0) return false;
        if (sCmp == 0 && !(this.isStartInclusive() && other.isEndInclusive())) return false;

        int eCmp = Float.compare(this.maximum, other.minimum);
        if (eCmp < 0) return false;
        return eCmp != 0 || (this.isEndInclusive() && other.isStartInclusive());
    }

    /**
     * Returns the intersection of this interval and {@code other}.
     *
     * <p>If the intervals do not overlap, returns {@link Optional#empty()}.
     *
     * <p><b>Current behavior note:</b> If {@code other} is {@code null} or empty,
     * this implementation returns {@code Optional.of(this)}.
     *
     * <pre>{@code
     * FloatInterval a = FloatInterval.of(1.0f, 5.0f, IntervalType.CLOSED_INTERVAL); // [1.0,5.0]
     * FloatInterval b = FloatInterval.of(3.0f, 8.0f, IntervalType.OPEN_CLOSED_INTERVAL); // (3.0,8.0]
     * Optional<FloatInterval> inter = a.intersection(b); // (3.0,5.0]
     * }</pre>
     *
     * @param other the other interval
     * @return an {@code Optional} describing the intersection, if present
     */
    public Optional<FloatInterval> intersection(FloatInterval other) {
        if (other == null || other.isEmpty()) {
            return Optional.of(this);
        }

        if (!overlaps(other)) {
            return Optional.empty();
        }

        float newStart;
        boolean newStartInc;
        int startCmp = Float.compare(this.minimum, other.minimum);
        if (startCmp > 0) {
            newStart = this.minimum;
            newStartInc = this.isStartInclusive();
        } else if (startCmp < 0) {
            newStart = other.minimum;
            newStartInc = other.isStartInclusive();
        } else {
            newStart = this.minimum;
            newStartInc = this.isStartInclusive() && other.isStartInclusive();
        }

        float newEnd;
        boolean newEndInc;
        int endCmp = Float.compare(this.maximum, other.maximum);
        if (endCmp < 0) {
            newEnd = this.maximum;
            newEndInc = this.isEndInclusive();
        } else if (endCmp > 0) {
            newEnd = other.maximum;
            newEndInc = other.isEndInclusive();
        } else {
            newEnd = this.maximum;
            newEndInc = this.isEndInclusive() && other.isEndInclusive();
        }

        IntervalType type = IntervalType.of(newStartInc, newEndInc);
        return Optional.of(FloatInterval.of(newStart, newEnd, type));
    }

    /**
     * Returns {@code true} if this interval starts after the given value, taking endpoint
     * inclusiveness into account.
     *
     * <p>That is:
     * <ul>
     *   <li>{@code minimum > value} => {@code true}</li>
     *   <li>{@code minimum < value} => {@code false}</li>
     *   <li>{@code minimum == value} => {@code !isStartInclusive()}</li>
     * </ul>
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 3.0f, IntervalType.OPEN_CLOSED_INTERVAL); // (1.0, 3.0]
     * i.startsAfter(1.0f); // true
     * }</pre>
     *
     * @param value the value to compare with the start bound
     * @return {@code true} if the interval starts after {@code value} considering inclusiveness
     */
    public boolean startsAfter(float value) {
        int comparison = Float.compare(this.minimum, value);
        if (comparison > 0) return true;
        if (comparison < 0) return false;
        return !isStartInclusive();
    }

    /**
     * Returns {@code true} if the start endpoint is inclusive and {@code minimum > value}.
     *
     * @param value the value to compare with the start bound
     * @return {@code true} if start is inclusive and strictly greater than {@code value}
     */
    public boolean startsAfterStrictly(float value) {
        int cmp = Float.compare(this.minimum, value);
        return isStartInclusive() && cmp > 0;
    }

    /**
     * Returns {@code true} if this interval ends before the given value, taking endpoint
     * inclusiveness into account.
     *
     * <p>That is:
     * <ul>
     *   <li>{@code maximum < value} => {@code true}</li>
     *   <li>{@code maximum > value} => {@code false}</li>
     *   <li>{@code maximum == value} => {@code !isEndInclusive()}</li>
     * </ul>
     *
     * @param value the value to compare with the end bound
     * @return {@code true} if the interval ends before {@code value} considering inclusiveness
     */
    public boolean endsBefore(float value) {
        int comparison = Float.compare(this.maximum, value);
        if (comparison < 0) return true;
        if (comparison > 0) return false;
        return !isEndInclusive();
    }

    /**
     * Returns {@code true} if the end endpoint is inclusive and {@code maximum < value}.
     *
     * @param value the value to compare with the end bound
     * @return {@code true} if end is inclusive and strictly less than {@code value}
     */
    public boolean endsBeforeStrictly(float value) {
        int cmp = Float.compare(this.maximum, value);
        return isEndInclusive() && cmp < 0;
    }

    /**
     * Returns a formatted string representation of this interval.
     *
     * <p>The formatting is delegated to {@link IntervalFormatter}.
     *
     * <pre>{@code
     * FloatInterval i = FloatInterval.of(1.0f, 3.0f, IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = i.formattedString(); // typically: "[1.0, 3.0)"
     * }</pre>
     *
     * @return the formatted string
     */
    public String formattedString() {
        return IntervalFormatter.format(this, getIntervalType());
    }

    /**
     * Returns an empty {@link Float} {@link ObjectInterval} instance.

     * @return an empty {@link Float} {@link ObjectInterval} instance.
     */
    public static @NotNull ObjectInterval<Float> empty() {
        return EmptyInterval.empty();
    }
}
