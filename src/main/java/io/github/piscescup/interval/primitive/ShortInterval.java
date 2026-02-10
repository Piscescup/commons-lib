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
 * A primitive {@code short}-based interval with explicit endpoint inclusiveness.
 *
 * <p>This interval is defined by:
 * <ul>
 *   <li>a minimum (start) bound {@link #getMinimum()}</li>
 *   <li>a maximum (end) bound {@link #getMaximum()}</li>
 *   <li>an {@link IntervalType} describing whether endpoints are inclusive</li>
 * </ul>
 *
 * <p>Ordering is the natural numeric ordering of {@code short}. Therefore, the
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
 * ShortInterval closed    = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_INTERVAL);       // [1, 3]
 * ShortInterval open      = ShortInterval.of((short) 1, (short) 3, IntervalType.OPEN_INTERVAL);         // (1, 3)
 * ShortInterval openEnd   = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_OPEN_INTERVAL);  // [1, 3)
 * ShortInterval openStart = ShortInterval.of((short) 1, (short) 3, IntervalType.OPEN_CLOSED_INTERVAL);  // (1, 3]
 *
 * // Membership
 * closed.contains((short) 1); // true
 * open.contains((short) 1);   // false
 *
 * // Degenerate / empty
 * ShortInterval pointClosed = ShortInterval.of((short) 5, (short) 5, IntervalType.CLOSED_INTERVAL); // [5, 5]
 * pointClosed.isEmpty();      // false
 * pointClosed.isDegenerate(); // true
 * pointClosed.contains((short) 5); // true
 *
 * ShortInterval pointOpen = ShortInterval.of((short) 5, (short) 5, IntervalType.OPEN_INTERVAL); // (5, 5)
 * pointOpen.isEmpty(); // true
 *
 * // Overlap and intersection
 * ShortInterval a = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_INTERVAL); // [1, 3]
 * ShortInterval b = ShortInterval.of((short) 3, (short) 8, IntervalType.CLOSED_INTERVAL); // [3, 8]
 * a.overlaps(b); // true (touch at 3 and both inclusive at the touching endpoints)
 *
 * Optional<ShortInterval> inter = a.intersection(b); // Optional[[3, 3]]
 * inter.isPresent(); // true
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public class ShortInterval implements PrimitiveInterval, Serializable {

    private final short minimum;
    private final short maximum;
    private final IntervalType intervalType;

    /**
     * Creates a {@code ShortInterval} with the given bounds and endpoint type.
     *
     * <p>The interval is valid only when {@code minimum <= maximum}.
     *
     * <pre>{@code
     * ShortInterval i = ShortInterval.of((short) 1, (short) 10, IntervalType.CLOSED_OPEN_INTERVAL); // [1, 10)
     * }</pre>
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     * @return a new {@code ShortInterval}
     * @throws NullPointerException                 if {@code intervalType} is {@code null}
     * @throws IllegalMathIntervalEndpointException if {@code minimum > maximum}
     */
    @Contract("_, _, _ -> new")
    public static @NotNull ShortInterval of(short minimum, short maximum, IntervalType intervalType) {
        NullCheck.requireNonNull(intervalType);
        if (minimum > maximum) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }
        return new ShortInterval(minimum, maximum, intervalType);
    }

    /**
     * Constructs a new {@code ShortInterval}.
     *
     * <p>Callers should prefer {@link #of(short, short, IntervalType)} which performs
     * validation.
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     */
    private ShortInterval(short minimum, short maximum, IntervalType intervalType) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.intervalType = intervalType;
    }

    /**
     * Returns the minimum (start) endpoint.
     *
     * @return the minimum bound
     */
    public short getMinimum() {
        return minimum;
    }

    /**
     * Returns the maximum (end) endpoint.
     *
     * @return the maximum bound
     */
    public short getMaximum() {
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
    public @NotNull Comparator<Short> getComparator() {
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
     * ShortInterval i = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_OPEN_INTERVAL); // [1, 3)
     * i.onStartEndpoint((short) 1); // true
     * i.onStartEndpoint((short) 2); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value == minimum} and start is inclusive
     */
    public boolean onStartEndpoint(short value) {
        return isStartInclusive() && minimum == value;
    }

    /**
     * Returns whether the given value is exactly on the end endpoint and the end is inclusive.
     *
     * <pre>{@code
     * ShortInterval i = ShortInterval.of((short) 1, (short) 3, IntervalType.OPEN_CLOSED_INTERVAL); // (1, 3]
     * i.onEndEndpoint((short) 3); // true
     * i.onEndEndpoint((short) 2); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value == maximum} and end is inclusive
     */
    public boolean onEndEndpoint(short value) {
        return isEndInclusive() && maximum == value;
    }

    /**
     * Returns {@code true} if the given value is contained in this interval.
     *
     * <pre>{@code
     * ShortInterval i = ShortInterval.of((short) 1, (short) 3, IntervalType.OPEN_CLOSED_INTERVAL); // (1, 3]
     * i.contains((short) 1); // false
     * i.contains((short) 2); // true
     * i.contains((short) 3); // true
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} is inside the interval with respect to endpoint rules
     */
    public boolean contains(short value) {
        int cs = Short.compare(value, minimum);
        int ce = Short.compare(value, maximum);

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
     * ShortInterval outer = ShortInterval.of((short) 1, (short) 10, IntervalType.CLOSED_INTERVAL); // [1,10]
     * ShortInterval inner = ShortInterval.of((short) 3, (short) 7, IntervalType.OPEN_INTERVAL);   // (3,7)
     * outer.containsInterval(inner); // true
     * inner.containsInterval(outer); // false
     * }</pre>
     *
     * @param other the interval to test
     * @return {@code true} if {@code other} lies within this interval
     */
    public boolean containsInterval(ShortInterval other) {
        if (other == null) return false;

        int startCmp = Short.compare(other.minimum, this.minimum);
        boolean startOk =
            startCmp > 0 ||
                (startCmp == 0 && (!other.isStartInclusive() || isStartInclusive()));

        int endCmp = Short.compare(other.maximum, this.maximum);
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
    public boolean isContainedBy(ShortInterval other) {
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
     * ShortInterval a = ShortInterval.of((short) 5, (short) 5, IntervalType.CLOSED_INTERVAL); // [5,5]
     * a.isEmpty(); // false
     *
     * ShortInterval b = ShortInterval.of((short) 5, (short) 5, IntervalType.OPEN_CLOSED_INTERVAL); // (5,5]
     * b.isEmpty(); // true
     * }</pre>
     *
     * @return {@code true} if this interval contains no values
     */
    public boolean isEmpty() {
        int c = Short.compare(minimum, maximum);
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
     * ShortInterval a = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_INTERVAL); // [1,3]
     * ShortInterval b = ShortInterval.of((short) 3, (short) 8, IntervalType.CLOSED_INTERVAL); // [3,8]
     * a.overlaps(b); // true
     *
     * ShortInterval c = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_OPEN_INTERVAL); // [1,3)
     * ShortInterval d = ShortInterval.of((short) 3, (short) 8, IntervalType.CLOSED_INTERVAL);      // [3,8]
     * c.overlaps(d); // false (touch at 3, but c excludes 3)
     * }</pre>
     *
     * @param other the other interval
     * @return {@code true} if the intervals overlap
     */
    public boolean overlaps(ShortInterval other) {
        if (other == null) return false;
        if (this.isEmpty() || other.isEmpty()) return false;

        int sCmp = Short.compare(this.minimum, other.maximum);
        if (sCmp > 0) return false;
        if (sCmp == 0 && !(this.isStartInclusive() && other.isEndInclusive())) return false;

        int eCmp = Short.compare(this.maximum, other.minimum);
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
     * ShortInterval a = ShortInterval.of((short) 1, (short) 5, IntervalType.CLOSED_INTERVAL); // [1,5]
     * ShortInterval b = ShortInterval.of((short) 3, (short) 8, IntervalType.OPEN_CLOSED_INTERVAL); // (3,8]
     * Optional<ShortInterval> inter = a.intersection(b); // (3,5]
     * }</pre>
     *
     * @param other the other interval
     * @return an {@code Optional} describing the intersection, if present
     */
    public Optional<ShortInterval> intersection(ShortInterval other) {
        if (other == null || other.isEmpty()) {
            return Optional.of(this);
        }

        if (!overlaps(other)) {
            return Optional.empty();
        }

        short newStart;
        boolean newStartInc;
        int startCmp = Short.compare(this.minimum, other.minimum);
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

        short newEnd;
        boolean newEndInc;
        int endCmp = Short.compare(this.maximum, other.maximum);
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
        return Optional.of(ShortInterval.of(newStart, newEnd, type));
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
     * @param value the value to compare with the start bound
     * @return {@code true} if the interval starts after {@code value} with inclusiveness considered
     */
    public boolean startsAfter(short value) {
        int comparison = Short.compare(this.minimum, value);
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
    public boolean startsAfterStrictly(short value) {
        int cmp = Short.compare(this.minimum, value);
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
    public boolean endsBefore(short value) {
        int comparison = Short.compare(this.maximum, value);
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
    public boolean endsBeforeStrictly(short value) {
        int cmp = Short.compare(this.maximum, value);
        return isEndInclusive() && cmp < 0;
    }

    /**
     * Returns a formatted string representation of this interval.
     *
     * <p>The formatting is delegated to {@link IntervalFormatter}.
     *
     * <pre>{@code
     * ShortInterval i = ShortInterval.of((short) 1, (short) 3, IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = i.formattedString(); // typically: "[1, 3)"
     * }</pre>
     *
     * @return the formatted string
     */
    public String formattedString() {
        return IntervalFormatter.format(this, getIntervalType());
    }

    /**
     * Returns an empty {@link Short} {@link ObjectInterval} instance.

     * @return an empty {@link Short} {@link ObjectInterval} instance.
     */
    public static @NotNull ObjectInterval<Short> empty() {
        return EmptyInterval.empty();
    }
}
