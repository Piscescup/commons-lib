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
 * A primitive {@code long}-based interval with explicit endpoint inclusiveness.
 *
 * <p>This interval is defined by:
 * <ul>
 *   <li>a minimum (start) bound {@link #getMinimum()}</li>
 *   <li>a maximum (end) bound {@link #getMaximum()}</li>
 *   <li>an {@link IntervalType} describing whether endpoints are inclusive</li>
 * </ul>
 *
 * <p>Ordering is the natural numeric ordering of {@code long}. Therefore, the
 * comparator is always {@link Comparator#naturalOrder()}.
 *
 * <h2>Validity</h2>
 * <p>An instance is constructed only when {@code minimum <= maximum}. If
 * {@code minimum > maximum}, {@link IllegalMathIntervalEndpointException} is thrown.
 *
 * <h2>Empty interval</h2>
 * <p>When {@code minimum == maximum}, the interval is empty unless it is closed on
 * both ends (i.e. {@code [a, a]}). Concretely:
 * <ul>
 *   <li>{@code [a, a]} is <b>not</b> empty and contains {@code a}</li>
 *   <li>{@code (a, a)}, {@code (a, a]}, {@code [a, a)} are empty</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Create intervals with different endpoint types
 * LongInterval closed    = LongInterval.of(1L, 3L, IntervalType.CLOSED_INTERVAL);       // [1, 3]
 * LongInterval open      = LongInterval.of(1L, 3L, IntervalType.OPEN_INTERVAL);         // (1, 3)
 * LongInterval openEnd   = LongInterval.of(1L, 3L, IntervalType.CLOSED_OPEN_INTERVAL);  // [1, 3)
 * LongInterval openStart = LongInterval.of(1L, 3L, IntervalType.OPEN_CLOSED_INTERVAL);  // (1, 3]
 *
 * // Membership
 * closed.contains(1L); // true
 * open.contains(1L);   // false
 *
 * // Degenerate / empty
 * LongInterval pointClosed = LongInterval.of(5L, 5L, IntervalType.CLOSED_INTERVAL); // [5, 5]
 * pointClosed.isEmpty();      // false
 * pointClosed.isDegenerate(); // true
 * pointClosed.contains(5L);   // true
 *
 * LongInterval pointOpen = LongInterval.of(5L, 5L, IntervalType.OPEN_INTERVAL); // (5, 5)
 * pointOpen.isEmpty(); // true
 *
 * // Overlap and intersection
 * LongInterval a = LongInterval.of(1L, 3L, IntervalType.CLOSED_INTERVAL); // [1, 3]
 * LongInterval b = LongInterval.of(3L, 8L, IntervalType.CLOSED_INTERVAL); // [3, 8]
 * a.overlaps(b); // true (touch at 3 and both inclusive at the touching endpoints)
 *
 * Optional<LongInterval> inter = a.intersection(b); // Optional[[3, 3]]
 * inter.isPresent(); // true
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public class LongInterval implements PrimitiveInterval, Serializable {

    @Serial
    private static final long serialVersionUID = 7011889511478L;

    /**
     * The minimum (start) endpoint of this interval.
     */
    private final long minimum;
    /**
     * The maximum (end) endpoint of this interval.
     */
    private final long maximum;
    /**
     * The interval type describing endpoint inclusiveness.
     */
    private final IntervalType intervalType;


    /**
     * Creates a {@code LongInterval} with the given bounds and endpoint type.
     *
     * <p>The interval is valid only when {@code minimum <= maximum}.
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 10L, IntervalType.CLOSED_OPEN_INTERVAL); // [1, 10)
     * }</pre>
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     * @return a new {@code LongInterval}
     * @throws NullPointerException                 if {@code intervalType} is {@code null}
     * @throws IllegalMathIntervalEndpointException if {@code minimum > maximum}
     */
    @Contract("_, _, _ -> new")
    public static @NotNull LongInterval of(long minimum, long maximum, IntervalType intervalType) {
        NullCheck.requireNonNull(intervalType);
        if (minimum > maximum) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }
        return new LongInterval(minimum, maximum, intervalType);
    }

    /**
     * Constructs a new {@code LongInterval}.
     *
     * <p>Callers should prefer {@link #of(long, long, IntervalType)} which performs
     * validation.
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     */
    private LongInterval(long minimum, long maximum, IntervalType intervalType) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.intervalType = intervalType;
    }

    /**
     * Returns the minimum (start) endpoint.
     *
     * @return the minimum bound
     */
    public long getMinimum() {
        return minimum;
    }

    /**
     * Returns the maximum (end) endpoint.
     *
     * @return the maximum bound
     */
    public long getMaximum() {
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
     * <p>For primitive numeric intervals, the comparator is always the natural order.
     *
     * @return {@link Comparator#naturalOrder()}
     */
    public @NotNull Comparator<Long> getComparator() {
        return Comparator.naturalOrder();
    }

    /**
     * Returns {@code true} if {@code minimum == maximum}.
     *
     * <p>Note that a degenerate interval may still be empty depending on
     * endpoint inclusiveness (see {@link #isEmpty()}).
     *
     * @return {@code true} if the endpoints are equal
     */
    public boolean isDegenerate() {
        return maximum == minimum;
    }

    /**
     * Returns whether the given value is exactly on the start endpoint and the start is inclusive.
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 3L, IntervalType.CLOSED_OPEN_INTERVAL); // [1, 3)
     * i.onStartEndpoint(1L); // true
     * i.onStartEndpoint(2L); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value == minimum} and start is inclusive
     */
    public boolean onStartEndpoint(long value) {
        return isStartInclusive() && minimum == value;
    }

    /**
     * Returns whether the given value is exactly on the end endpoint and the end is inclusive.
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 3L, IntervalType.OPEN_CLOSED_INTERVAL); // (1, 3]
     * i.onEndEndpoint(3L); // true
     * i.onEndEndpoint(2L); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value == maximum} and end is inclusive
     */
    public boolean onEndEndpoint(long value) {
        return isEndInclusive() && maximum == value;
    }

    /**
     * Returns {@code true} if the given value is contained in this interval.
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 3L, IntervalType.OPEN_CLOSED_INTERVAL); // (1, 3]
     * i.contains(1L); // false
     * i.contains(2L); // true
     * i.contains(3L); // true
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} is inside the interval with respect to endpoint rules
     */
    public boolean contains(long value) {
        int cs = Long.compare(value, minimum);
        int ce = Long.compare(value, maximum);

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
     * LongInterval outer = LongInterval.of(1L, 10L, IntervalType.CLOSED_INTERVAL); // [1,10]
     * LongInterval inner = LongInterval.of(3L, 7L, IntervalType.OPEN_INTERVAL);   // (3,7)
     * outer.containsInterval(inner); // true
     * inner.containsInterval(outer); // false
     * }</pre>
     *
     * @param other the interval to test
     * @return {@code true} if {@code other} lies within this interval
     */
    public boolean containsInterval(LongInterval other) {
        if (other == null) return false;

        int startCmp = Long.compare(other.minimum, this.minimum);
        boolean startOk =
            startCmp > 0 ||
                (startCmp == 0 && (!other.isStartInclusive() || isStartInclusive()));

        int endCmp = Long.compare(other.maximum, this.maximum);
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
    public boolean isContainedBy(LongInterval other) {
        if (other == null) return false;
        return other.containsInterval(this);
    }

    /**
     * Returns {@code true} if this interval is empty.
     *
     * <p>When {@code minimum < maximum}, the interval is never empty.
     * When {@code minimum == maximum}, the interval is empty unless both endpoints
     * are inclusive.
     *
     * <pre>{@code
     * LongInterval a = LongInterval.of(5L, 5L, IntervalType.CLOSED_INTERVAL); // [5,5]
     * a.isEmpty(); // false
     *
     * LongInterval b = LongInterval.of(5L, 5L, IntervalType.OPEN_CLOSED_INTERVAL); // (5,5]
     * b.isEmpty(); // true
     * }</pre>
     *
     * @return {@code true} if this interval contains no values
     */
    public boolean isEmpty() {
        int c = Long.compare(minimum, maximum);
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
     * LongInterval a = LongInterval.of(1L, 3L, IntervalType.CLOSED_INTERVAL); // [1,3]
     * LongInterval b = LongInterval.of(3L, 8L, IntervalType.CLOSED_INTERVAL); // [3,8]
     * a.overlaps(b); // true
     *
     * LongInterval c = LongInterval.of(1L, 3L, IntervalType.CLOSED_OPEN_INTERVAL); // [1,3)
     * LongInterval d = LongInterval.of(3L, 8L, IntervalType.CLOSED_INTERVAL);      // [3,8]
     * c.overlaps(d); // false (touch at 3, but c excludes 3)
     * }</pre>
     *
     * @param other the other interval
     * @return {@code true} if the intervals overlap
     */
    public boolean overlaps(LongInterval other) {
        if (other == null) return false;
        if (this.isEmpty() || other.isEmpty()) return false;

        int sCmp = Long.compare(this.minimum, other.maximum);
        if (sCmp > 0) return false;
        if (sCmp == 0 && !(this.isStartInclusive() && other.isEndInclusive())) return false;

        int eCmp = Long.compare(this.maximum, other.minimum);
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
     * LongInterval a = LongInterval.of(1L, 5L, IntervalType.CLOSED_INTERVAL); // [1,5]
     * LongInterval b = LongInterval.of(3L, 8L, IntervalType.OPEN_CLOSED_INTERVAL); // (3,8]
     * Optional<LongInterval> inter = a.intersection(b); // (3,5]
     * }</pre>
     *
     * @param other the other interval
     * @return an {@code Optional} describing the intersection, if present
     */
    public Optional<LongInterval> intersection(LongInterval other) {
        if (other == null || other.isEmpty()) {
            return Optional.of(this);
        }

        if (!overlaps(other)) {
            return Optional.empty();
        }

        long newStart;
        boolean newStartInc;
        int startCmp = Long.compare(this.minimum, other.minimum);
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

        long newEnd;
        boolean newEndInc;
        int endCmp = Long.compare(this.maximum, other.maximum);
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
        return Optional.of(LongInterval.of(newStart, newEnd, type));
    }

    /**
     * Returns {@code true} if this interval starts after the given value,
     * taking endpoint inclusiveness into account.
     *
     * <p>That is:
     * <ul>
     *   <li>{@code minimum > value} => {@code true}</li>
     *   <li>{@code minimum < value} => {@code false}</li>
     *   <li>{@code minimum == value} => {@code !isStartInclusive()}</li>
     * </ul>
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 3L, IntervalType.OPEN_CLOSED_INTERVAL); // (1, 3]
     * i.startsAfter(1L); // true
     * }</pre>
     *
     * @param value the value to compare with the start bound
     * @return {@code true} if the interval starts after {@code value} with inclusiveness considered
     */
    public boolean startsAfter(long value) {
        int comparison = Long.compare(this.minimum, value);
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
    public boolean startsAfterStrictly(long value) {
        int cmp = Long.compare(this.minimum, value);
        return isStartInclusive() && cmp > 0;
    }

    /**
     * Returns {@code true} if this interval ends before the given value,
     * taking endpoint inclusiveness into account.
     *
     * <p>That is:
     * <ul>
     *   <li>{@code maximum < value} => {@code true}</li>
     *   <li>{@code maximum > value} => {@code false}</li>
     *   <li>{@code maximum == value} => {@code !isEndInclusive()}</li>
     * </ul>
     *
     * @param value the value to compare with the end bound
     * @return {@code true} if the interval ends before {@code value} with inclusiveness considered
     */
    public boolean endsBefore(long value) {
        int comparison = Long.compare(this.maximum, value);
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
    public boolean endsBeforeStrictly(long value) {
        int cmp = Long.compare(this.maximum, value);
        return isEndInclusive() && cmp < 0;
    }

    /**
     * Returns a formatted string representation of this interval.
     *
     * <p>The formatting is delegated to {@link IntervalFormatter}.
     *
     * <pre>{@code
     * LongInterval i = LongInterval.of(1L, 3L, IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = i.formattedString(); // typically: "[1, 3)"
     * }</pre>
     *
     * @return the formatted string
     */
    public String formattedString() {
        return IntervalFormatter.format(this, getIntervalType());
    }

    /**
     * Returns an empty {@link Long} {@link ObjectInterval} instance.

     * @return an empty {@link Long} {@link ObjectInterval} instance.
     */
    public static @NotNull ObjectInterval<Long> empty() {
        return EmptyInterval.empty();
    }
}
