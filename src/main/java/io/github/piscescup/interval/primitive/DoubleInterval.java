package io.github.piscescup.interval.primitive;

import io.github.piscescup.exception.IllegalMathIntervalEndpointException;
import io.github.piscescup.interval.EmptyInterval;
import io.github.piscescup.interval.IntervalFormatter;
import io.github.piscescup.interval.IntervalType;
import io.github.piscescup.interval.ObjectInterval;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Optional;

/**
 * A primitive {@code double}-based interval with explicit endpoint inclusiveness.
 *
 * <p>This interval is defined by:
 * <ul>
 *   <li>a minimum (start) bound {@link #getMinimum()}</li>
 *   <li>a maximum (end) bound {@link #getMaximum()}</li>
 *   <li>an {@link IntervalType} describing whether endpoints are inclusive</li>
 * </ul>
 *
 * <p>Ordering uses {@link Double#compare(double, double)} (i.e. the comparator is
 * {@link Comparator#naturalOrder()}). This includes Java's defined ordering for
 * special values such as {@code NaN} and signed zeros.
 *
 * <h2>Validity</h2>
 * <p>An instance is constructed only when {@code minimum <= maximum} under
 * {@link Double#compare(double, double)}. If {@code minimum > maximum},
 * {@link IllegalMathIntervalEndpointException} is thrown.
 *
 * <h2>Empty interval</h2>
 * <p>When {@code minimum == maximum} (as determined by {@link Double#compare(double, double) == 0}),
 * the interval is empty unless it is closed on both ends (i.e. {@code [x, x]}).
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Create intervals with different endpoint types
 * DoubleInterval closed    = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_INTERVAL);       // [1.0, 3.0]
 * DoubleInterval open      = DoubleInterval.of(1.0, 3.0, IntervalType.OPEN_INTERVAL);         // (1.0, 3.0)
 * DoubleInterval openEnd   = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_OPEN_INTERVAL);  // [1.0, 3.0)
 * DoubleInterval openStart = DoubleInterval.of(1.0, 3.0, IntervalType.OPEN_CLOSED_INTERVAL);  // (1.0, 3.0]
 *
 * // Membership
 * closed.contains(1.0); // true
 * open.contains(1.0);   // false
 *
 * // Degenerate / empty
 * DoubleInterval pointClosed = DoubleInterval.of(5.0, 5.0, IntervalType.CLOSED_INTERVAL); // [5.0, 5.0]
 * pointClosed.isEmpty();      // false
 * pointClosed.isDegenerate(); // true
 * pointClosed.contains(5.0);  // true
 *
 * DoubleInterval pointOpen = DoubleInterval.of(5.0, 5.0, IntervalType.OPEN_INTERVAL); // (5.0, 5.0)
 * pointOpen.isEmpty(); // true
 *
 * // Overlap and intersection
 * DoubleInterval a = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_INTERVAL); // [1.0, 3.0]
 * DoubleInterval b = DoubleInterval.of(3.0, 8.0, IntervalType.CLOSED_INTERVAL); // [3.0, 8.0]
 * a.overlaps(b); // true (touch at 3.0 and both inclusive at the touching endpoints)
 *
 * Optional<DoubleInterval> inter = a.intersection(b); // Optional[[3.0, 3.0]]
 * inter.isPresent(); // true
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public class DoubleInterval implements PrimitiveInterval, Serializable {

    private final double minimum;
    private final double maximum;
    private final IntervalType intervalType;

    /**
     * Creates a {@code DoubleInterval} with the given bounds and endpoint type.
     *
     * <p>The interval is valid only when {@code minimum <= maximum} under
     * {@link Double#compare(double, double)}.
     *
     * <pre>{@code
     * DoubleInterval i = DoubleInterval.of(1.0, 10.0, IntervalType.CLOSED_OPEN_INTERVAL); // [1.0, 10.0)
     * }</pre>
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     * @return a new {@code DoubleInterval}
     * @throws NullPointerException                 if {@code intervalType} is {@code null}
     * @throws IllegalMathIntervalEndpointException if {@code minimum > maximum}
     */
    @Contract("_, _, _ -> new")
    public static @NotNull DoubleInterval of(double minimum, double maximum, IntervalType intervalType) {
        NullCheck.requireNonNull(intervalType);
        if (Double.compare(minimum, maximum) > 0) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }
        return new DoubleInterval(minimum, maximum, intervalType);
    }

    /**
     * Constructs a new {@code DoubleInterval}.
     *
     * <p>Callers should prefer {@link #of(double, double, IntervalType)} which performs
     * validation.
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     */
    private DoubleInterval(double minimum, double maximum, IntervalType intervalType) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.intervalType = intervalType;
    }

    /**
     * Returns the minimum (start) endpoint.
     *
     * @return the minimum bound
     */
    public double getMinimum() {
        return minimum;
    }

    /**
     * Returns the maximum (end) endpoint.
     *
     * @return the maximum bound
     */
    public double getMaximum() {
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
    public @NotNull Comparator<Double> getComparator() {
        return Comparator.naturalOrder();
    }

    /**
     * Returns {@code true} if {@code minimum == maximum} under {@link Double#compare(double, double)}.
     *
     * <p>Note that a degenerate interval may still be empty depending on endpoint
     * inclusiveness (see {@link #isEmpty()}).
     *
     * @return {@code true} if the endpoints are equal under {@code Double.compare}
     */
    public boolean isDegenerate() {
        return Double.compare(maximum, minimum) == 0;
    }

    /**
     * Returns whether the given value is exactly on the start endpoint and the start is inclusive.
     *
     * <pre>{@code
     * DoubleInterval i = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_OPEN_INTERVAL); // [1.0, 3.0)
     * i.onStartEndpoint(1.0); // true
     * i.onStartEndpoint(2.0); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} equals {@code minimum} and start is inclusive
     */
    public boolean onStartEndpoint(double value) {
        return isStartInclusive() && Double.compare(minimum, value) == 0;
    }

    /**
     * Returns whether the given value is exactly on the end endpoint and the end is inclusive.
     *
     * <pre>{@code
     * DoubleInterval i = DoubleInterval.of(1.0, 3.0, IntervalType.OPEN_CLOSED_INTERVAL); // (1.0, 3.0]
     * i.onEndEndpoint(3.0); // true
     * i.onEndEndpoint(2.0); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} equals {@code maximum} and end is inclusive
     */
    public boolean onEndEndpoint(double value) {
        return isEndInclusive() && Double.compare(maximum, value) == 0;
    }

    /**
     * Returns {@code true} if the given value is contained in this interval.
     *
     * <pre>{@code
     * DoubleInterval i = DoubleInterval.of(1.0, 3.0, IntervalType.OPEN_CLOSED_INTERVAL); // (1.0, 3.0]
     * i.contains(1.0); // false
     * i.contains(2.0); // true
     * i.contains(3.0); // true
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} is inside the interval with respect to endpoint rules
     */
    public boolean contains(double value) {
        int cs = Double.compare(value, minimum);
        int ce = Double.compare(value, maximum);

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
     * DoubleInterval outer = DoubleInterval.of(1.0, 10.0, IntervalType.CLOSED_INTERVAL); // [1.0,10.0]
     * DoubleInterval inner = DoubleInterval.of(3.0, 7.0, IntervalType.OPEN_INTERVAL);    // (3.0,7.0)
     * outer.containsInterval(inner); // true
     * inner.containsInterval(outer); // false
     * }</pre>
     *
     * @param other the interval to test
     * @return {@code true} if {@code other} lies within this interval
     */
    public boolean containsInterval(DoubleInterval other) {
        if (other == null) return false;

        int startCmp = Double.compare(other.minimum, this.minimum);
        boolean startOk =
            startCmp > 0 ||
                (startCmp == 0 && (!other.isStartInclusive() || isStartInclusive()));

        int endCmp = Double.compare(other.maximum, this.maximum);
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
    public boolean isContainedBy(DoubleInterval other) {
        if (other == null) return false;
        return other.containsInterval(this);
    }

    /**
     * Returns {@code true} if this interval is empty.
     *
     * <p>When {@code minimum < maximum} under {@link Double#compare(double, double)},
     * the interval is never empty. When {@code minimum == maximum}, the interval is empty
     * unless both endpoints are inclusive.
     *
     * <pre>{@code
     * DoubleInterval a = DoubleInterval.of(5.0, 5.0, IntervalType.CLOSED_INTERVAL); // [5.0,5.0]
     * a.isEmpty(); // false
     *
     * DoubleInterval b = DoubleInterval.of(5.0, 5.0, IntervalType.OPEN_CLOSED_INTERVAL); // (5.0,5.0]
     * b.isEmpty(); // true
     * }</pre>
     *
     * @return {@code true} if this interval contains no values
     */
    public boolean isEmpty() {
        int c = Double.compare(minimum, maximum);
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
     * DoubleInterval a = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_INTERVAL); // [1.0,3.0]
     * DoubleInterval b = DoubleInterval.of(3.0, 8.0, IntervalType.CLOSED_INTERVAL); // [3.0,8.0]
     * a.overlaps(b); // true
     *
     * DoubleInterval c = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_OPEN_INTERVAL); // [1.0,3.0)
     * DoubleInterval d = DoubleInterval.of(3.0, 8.0, IntervalType.CLOSED_INTERVAL);      // [3.0,8.0]
     * c.overlaps(d); // false (touch at 3.0, but c excludes 3.0)
     * }</pre>
     *
     * @param other the other interval
     * @return {@code true} if the intervals overlap
     */
    public boolean overlaps(DoubleInterval other) {
        if (other == null) return false;
        if (this.isEmpty() || other.isEmpty()) return false;

        int sCmp = Double.compare(this.minimum, other.maximum);
        if (sCmp > 0) return false;
        if (sCmp == 0 && !(this.isStartInclusive() && other.isEndInclusive())) return false;

        int eCmp = Double.compare(this.maximum, other.minimum);
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
     * DoubleInterval a = DoubleInterval.of(1.0, 5.0, IntervalType.CLOSED_INTERVAL); // [1.0,5.0]
     * DoubleInterval b = DoubleInterval.of(3.0, 8.0, IntervalType.OPEN_CLOSED_INTERVAL); // (3.0,8.0]
     * Optional<DoubleInterval> inter = a.intersection(b); // (3.0,5.0]
     * }</pre>
     *
     * @param other the other interval
     * @return an {@code Optional} describing the intersection, if present
     */
    public Optional<DoubleInterval> intersection(DoubleInterval other) {
        if (other == null || other.isEmpty()) {
            return Optional.of(this);
        }

        if (!overlaps(other)) {
            return Optional.empty();
        }

        double newStart;
        boolean newStartInc;
        int startCmp = Double.compare(this.minimum, other.minimum);
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

        double newEnd;
        boolean newEndInc;
        int endCmp = Double.compare(this.maximum, other.maximum);
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
        return Optional.of(DoubleInterval.of(newStart, newEnd, type));
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
     * DoubleInterval i = DoubleInterval.of(1.0, 3.0, IntervalType.OPEN_CLOSED_INTERVAL); // (1.0, 3.0]
     * i.startsAfter(1.0); // true
     * }</pre>
     *
     * @param value the value to compare with the start bound
     * @return {@code true} if the interval starts after {@code value} considering inclusiveness
     */
    public boolean startsAfter(double value) {
        int comparison = Double.compare(this.minimum, value);
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
    public boolean startsAfterStrictly(double value) {
        int cmp = Double.compare(this.minimum, value);
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
    public boolean endsBefore(double value) {
        int comparison = Double.compare(this.maximum, value);
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
    public boolean endsBeforeStrictly(double value) {
        int cmp = Double.compare(this.maximum, value);
        return isEndInclusive() && cmp < 0;
    }

    /**
     * Returns a formatted string representation of this interval.
     *
     * <p>The formatting is delegated to {@link IntervalFormatter}.
     *
     * <pre>{@code
     * DoubleInterval i = DoubleInterval.of(1.0, 3.0, IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = i.formattedString(); // typically: "[1.0, 3.0)"
     * }</pre>
     *
     * @return the formatted string
     */
    public String formattedString() {
        return IntervalFormatter.format(this, getIntervalType());
    }

    /**
     * Returns an empty {@link Double} {@link ObjectInterval} instance.

     * @return an empty {@link Double} {@link ObjectInterval} instance.
     */
    static @NotNull ObjectInterval<Double> empty() {
        return EmptyInterval.empty();
    }
}
