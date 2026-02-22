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
 * A primitive {@code char}-based interval with explicit endpoint inclusiveness.
 *
 * <p>This interval is defined by:
 * <ul>
 *   <li>a minimum (start) bound {@link #getMinimum()}</li>
 *   <li>a maximum (end) bound {@link #getMaximum()}</li>
 *   <li>an {@link IntervalType} describing whether endpoints are inclusive</li>
 * </ul>
 *
 * <p>Ordering is the natural ordering of {@code char} values (by numeric code unit).
 * The comparator is always {@link Comparator#naturalOrder()}.
 *
 * <h2>Validity</h2>
 * <p>An instance is constructed only when {@code minimum <= maximum}. If
 * {@code minimum > maximum}, {@link IllegalMathIntervalEndpointException} is thrown.
 *
 * <h2>Empty interval</h2>
 * <p>When {@code minimum == maximum}, the interval is empty unless it is closed on
 * both ends (i.e. {@code [c, c]}). Concretely:
 * <ul>
 *   <li>{@code [c, c]} is <b>not</b> empty and contains {@code c}</li>
 *   <li>{@code (c, c)}, {@code (c, c]}, {@code [c, c)} are empty</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Create intervals with different endpoint types
 * CharInterval closed    = CharInterval.of('a', 'z', IntervalType.CLOSED_INTERVAL);       // [a, z]
 * CharInterval open      = CharInterval.of('a', 'z', IntervalType.OPEN_INTERVAL);         // (a, z)
 * CharInterval openEnd   = CharInterval.of('a', 'z', IntervalType.CLOSED_OPEN_INTERVAL);  // [a, z)
 * CharInterval openStart = CharInterval.of('a', 'z', IntervalType.OPEN_CLOSED_INTERVAL);  // (a, z]
 *
 * // Membership
 * closed.contains('a'); // true
 * open.contains('a');   // false
 * openEnd.contains('z'); // false
 *
 * // Degenerate / empty
 * CharInterval pointClosed = CharInterval.of('x', 'x', IntervalType.CLOSED_INTERVAL); // [x, x]
 * pointClosed.isEmpty();      // false
 * pointClosed.isDegenerate(); // true
 * pointClosed.contains('x');  // true
 *
 * CharInterval pointOpen = CharInterval.of('x', 'x', IntervalType.OPEN_INTERVAL); // (x, x)
 * pointOpen.isEmpty(); // true
 *
 * // Overlap and intersection
 * CharInterval a = CharInterval.of('a', 'f', IntervalType.CLOSED_INTERVAL); // [a, f]
 * CharInterval b = CharInterval.of('f', 'm', IntervalType.CLOSED_INTERVAL); // [f, m]
 * a.overlaps(b); // true (touch at 'f' and both inclusive at the touching endpoints)
 *
 * Optional<CharInterval> inter = a.intersection(b); // Optional[[f, f]]
 * inter.isPresent(); // true
 * }</pre>
 *
 * @author REN YuanTong
 * @since 1.0.1
 */
public class CharInterval implements PrimitiveInterval, Serializable {

    @Serial
    private static final long serialVersionUID = 8895989911478L;

    /**
     * The minimum (start) endpoint of this interval.
     */
    private final char minimum;
    /**
     * The maximum (end) endpoint of this interval.
     */
    private final char maximum;
    /**
     * The interval type describing endpoint inclusiveness.
     */
    private final IntervalType intervalType;


    /**
     * Creates a {@code CharInterval} with the given bounds and endpoint type.
     *
     * <p>The interval is valid only when {@code minimum <= maximum}.
     *
     * <pre>{@code
     * CharInterval letters = CharInterval.of('a', 'z', IntervalType.CLOSED_OPEN_INTERVAL); // [a, z)
     * }</pre>
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     * @return a new {@code CharInterval}
     * @throws NullPointerException                 if {@code intervalType} is {@code null}
     * @throws IllegalMathIntervalEndpointException if {@code minimum > maximum}
     */
    @Contract("_, _, _ -> new")
    public static @NotNull CharInterval of(char minimum, char maximum, IntervalType intervalType) {
        NullCheck.requireNonNull(intervalType);
        if (minimum > maximum) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }
        return new CharInterval(minimum, maximum, intervalType);
    }

    /**
     * Constructs a new {@code CharInterval}.
     *
     * <p>Callers should prefer {@link #of(char, char, IntervalType)} which performs
     * validation.
     *
     * @param minimum      the start bound
     * @param maximum      the end bound
     * @param intervalType the endpoint inclusiveness descriptor
     */
    private CharInterval(char minimum, char maximum, IntervalType intervalType) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.intervalType = intervalType;
    }

    /**
     * Returns the minimum (start) endpoint.
     *
     * @return the minimum bound
     */
    public char getMinimum() {
        return minimum;
    }

    /**
     * Returns the maximum (end) endpoint.
     *
     * @return the maximum bound
     */
    public char getMaximum() {
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
     * <p>For primitive {@code char} intervals, the comparator is always the natural order.
     *
     * @return {@link Comparator#naturalOrder()}
     */
    public @NotNull Comparator<Character> getComparator() {
        return Comparator.naturalOrder();
    }

    /**
     * Returns {@code true} if {@code minimum == maximum}.
     *
     * <p>Note that a degenerate interval may still be empty depending on endpoint
     * inclusiveness (see {@link #isEmpty()}).
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
     * CharInterval i = CharInterval.of('a', 'c', IntervalType.CLOSED_OPEN_INTERVAL); // [a, c)
     * i.onStartEndpoint('a'); // true
     * i.onStartEndpoint('b'); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value == minimum} and start is inclusive
     */
    public boolean onStartEndpoint(char value) {
        return isStartInclusive() && minimum == value;
    }

    /**
     * Returns whether the given value is exactly on the end endpoint and the end is inclusive.
     *
     * <pre>{@code
     * CharInterval i = CharInterval.of('a', 'c', IntervalType.OPEN_CLOSED_INTERVAL); // (a, c]
     * i.onEndEndpoint('c'); // true
     * i.onEndEndpoint('b'); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value == maximum} and end is inclusive
     */
    public boolean onEndEndpoint(char value) {
        return isEndInclusive() && maximum == value;
    }

    /**
     * Returns {@code true} if the given value is contained in this interval.
     *
     * <pre>{@code
     * CharInterval i = CharInterval.of('a', 'c', IntervalType.OPEN_CLOSED_INTERVAL); // (a, c]
     * i.contains('a'); // false
     * i.contains('b'); // true
     * i.contains('c'); // true
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} is inside the interval with respect to endpoint rules
     */
    public boolean contains(char value) {
        int cs = Character.compare(value, minimum);
        int ce = Character.compare(value, maximum);

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
     * CharInterval outer = CharInterval.of('a', 'z', IntervalType.CLOSED_INTERVAL); // [a,z]
     * CharInterval inner = CharInterval.of('c', 'x', IntervalType.OPEN_INTERVAL);   // (c,x)
     * outer.containsInterval(inner); // true
     * inner.containsInterval(outer); // false
     * }</pre>
     *
     * @param other the interval to test
     * @return {@code true} if {@code other} lies within this interval
     */
    public boolean containsInterval(CharInterval other) {
        if (other == null) return false;

        int startCmp = Character.compare(other.minimum, this.minimum);
        boolean startOk =
            startCmp > 0 ||
                (startCmp == 0 && (!other.isStartInclusive() || isStartInclusive()));

        int endCmp = Character.compare(other.maximum, this.maximum);
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
    public boolean isContainedBy(CharInterval other) {
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
     * CharInterval a = CharInterval.of('x', 'x', IntervalType.CLOSED_INTERVAL); // [x,x]
     * a.isEmpty(); // false
     *
     * CharInterval b = CharInterval.of('x', 'x', IntervalType.OPEN_CLOSED_INTERVAL); // (x,x]
     * b.isEmpty(); // true
     * }</pre>
     *
     * @return {@code true} if this interval contains no values
     */
    public boolean isEmpty() {
        int c = Character.compare(minimum, maximum);
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
     * CharInterval a = CharInterval.of('a', 'f', IntervalType.CLOSED_INTERVAL); // [a,f]
     * CharInterval b = CharInterval.of('f', 'm', IntervalType.CLOSED_INTERVAL); // [f,m]
     * a.overlaps(b); // true
     *
     * CharInterval c = CharInterval.of('a', 'f', IntervalType.CLOSED_OPEN_INTERVAL); // [a,f)
     * CharInterval d = CharInterval.of('f', 'm', IntervalType.CLOSED_INTERVAL);      // [f,m]
     * c.overlaps(d); // false (touch at 'f', but c excludes 'f')
     * }</pre>
     *
     * @param other the other interval
     * @return {@code true} if the intervals overlap
     */
    public boolean overlaps(CharInterval other) {
        if (other == null) return false;
        if (this.isEmpty() || other.isEmpty()) return false;

        int sCmp = Character.compare(this.minimum, other.maximum);
        if (sCmp > 0) return false;
        if (sCmp == 0 && !(this.isStartInclusive() && other.isEndInclusive())) return false;

        int eCmp = Character.compare(this.maximum, other.minimum);
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
     * CharInterval a = CharInterval.of('a', 'g', IntervalType.CLOSED_INTERVAL); // [a,g]
     * CharInterval b = CharInterval.of('d', 'z', IntervalType.OPEN_CLOSED_INTERVAL); // (d,z]
     * Optional<CharInterval> inter = a.intersection(b); // (d,g]
     * }</pre>
     *
     * @param other the other interval
     * @return an {@code Optional} describing the intersection, if present
     */
    public Optional<CharInterval> intersection(CharInterval other) {
        if (other == null || other.isEmpty()) {
            return Optional.of(this);
        }

        if (!overlaps(other)) {
            return Optional.empty();
        }

        char newStart;
        boolean newStartInc;
        int startCmp = Character.compare(this.minimum, other.minimum);
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

        char newEnd;
        boolean newEndInc;
        int endCmp = Character.compare(this.maximum, other.maximum);
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
        return Optional.of(CharInterval.of(newStart, newEnd, type));
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
     * CharInterval i = CharInterval.of('a', 'c', IntervalType.OPEN_CLOSED_INTERVAL); // (a, c]
     * i.startsAfter('a'); // true
     * }</pre>
     *
     * @param value the value to compare with the start bound
     * @return {@code true} if the interval starts after {@code value} considering inclusiveness
     */
    public boolean startsAfter(char value) {
        int comparison = Character.compare(this.minimum, value);
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
    public boolean startsAfterStrictly(char value) {
        int cmp = Character.compare(this.minimum, value);
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
    public boolean endsBefore(char value) {
        int comparison = Character.compare(this.maximum, value);
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
    public boolean endsBeforeStrictly(char value) {
        int cmp = Character.compare(this.maximum, value);
        return isEndInclusive() && cmp < 0;
    }

    /**
     * Returns a formatted string representation of this interval.
     *
     * <p>The formatting is delegated to {@link IntervalFormatter}.
     *
     * <pre>{@code
     * CharInterval i = CharInterval.of('a', 'c', IntervalType.CLOSED_OPEN_INTERVAL);
     * String s = i.formattedString(); // typically: "[a, c)"
     * }</pre>
     *
     * @return the formatted string
     */
    public String formattedString() {
        return IntervalFormatter.format(this, getIntervalType());
    }

    /**
     * Returns an empty {@link Character} {@link ObjectInterval} instance.

     * @return an empty {@link Character} {@link ObjectInterval} instance.
     */
    public static @NotNull ObjectInterval<Character> empty() {
        return EmptyInterval.empty();
    }
}
