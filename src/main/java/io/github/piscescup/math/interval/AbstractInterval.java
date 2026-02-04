package io.github.piscescup.math.interval;

import io.github.piscescup.util.CompareUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.plaf.FileChooserUI;
import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Abstract base class for {@link Interval} implementations with finite bounds.
 *
 * <p>This class provides a common implementation for intervals defined by:
 * <ul>
 *   <li>a finite minimum bound ({@link #getMinimum()})</li>
 *   <li>a finite maximum bound ({@link #getMaximum()})</li>
 *   <li>an {@link IntervalType} describing endpoint inclusiveness</li>
 *   <li>a {@link Comparator} defining the ordering of values</li>
 * </ul>
 *
 * <h2>Ordering and comparator compatibility</h2>
 * <p>All comparison operations are based on the {@code comparator} supplied at construction time.
 * Two intervals are considered <em>comparable</em> only if their comparators are equal according
 * to {@link Objects#equals(Object, Object)}.
 *
 * <p>Invoking {@link #compareTo(Interval)} on intervals with incompatible comparators
 * results in an {@link IllegalArgumentException}.
 *
 * <h2>Comparison semantics</h2>
 * <p>The natural ordering of {@code AbstractInterval} instances is defined lexicographically
 * by the following components (in order):
 *
 * <ol>
 *   <li>minimum bound</li>
 *   <li>maximum bound</li>
 *   <li>start endpoint inclusiveness</li>
 *   <li>end endpoint inclusiveness</li>
 * </ol>
 *
 * <p>When comparing inclusiveness, inclusive endpoints are considered greater than exclusive
 * endpoints. That is:
 *
 * <pre>{@code
 * [a, b]  > (a, b]
 * (a, b]  > (a, b)
 * }</pre>
 *
 * <h2>Equality and hash code</h2>
 * <p>Two {@code AbstractInterval} instances are considered equal if and only if they have
 * equal minimum bounds, equal maximum bounds, and the same {@link IntervalType}.
 * The comparator is <em>not</em> considered in {@link #equals(Object)}.
 *
 * <p>The {@link #hashCode()} implementation, however, incorporates the comparator.
 * Subclasses should be aware of this when overriding equality semantics.
 *
 * <h2>Thread safety</h2>
 * <p>This class is immutable provided that the bound objects and comparator are themselves
 * immutable and thread-safe.
 *
 * <h2>Examples</h2>
 *
 * <pre>{@code
 * Interval<Integer> a =
 *     Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
 *
 * Interval<Integer> b =
 *     Interval.naturalOrderedInterval(2, 6, IntervalType.OPEN_INTERVAL); // (2,6)
 *
 * a.compareTo(b) > 0; // true ([2,6] > (2,6))
 * }</pre>
 *
 * @param <T> the element type
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public abstract class AbstractInterval<T> implements Interval<T>, Serializable {

    @Serial
    private static final long serialVersionUID = 1554871104L;

    /**
     * The maximum (end) bound of this interval.
     *
     * <p>This value represents the upper limit of the interval under the
     * ordering defined by {@link #getComparator()}.
     */
    private final T maximum;

    /**
     * The minimum (start) bound of this interval.
     *
     * <p>This value represents the lower limit of the interval under the
     * ordering defined by {@link #getComparator()}.
     */
    private final T minimum;

    /**
     * The type of this interval, indicating whether the start and end bounds
     * are inclusive or exclusive.
     */
    private final IntervalType intervalType;

    /**
     * The comparator that defines the ordering semantics of this interval.
     */
    private final Comparator<T> comparator;

    /**
     * Constructs a new {@code AbstractInterval} with the specified bounds,
     * interval type, and comparator.
     *
     * <p>Subclasses are responsible for validating the consistency of the
     * provided parameters (for example, ensuring that {@code minimum <= maximum}
     * under the given comparator, if required).
     *
     * @param minimum      the minimum (start) bound
     * @param maximum      the maximum (end) bound
     * @param intervalType the interval type (endpoint inclusiveness)
     * @param comparator   the comparator defining the ordering of values
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    protected AbstractInterval(
        T minimum,
        T maximum,
        IntervalType intervalType,
        Comparator<T> comparator
    ) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.intervalType = intervalType;
        this.comparator = comparator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getMinimum() {
        return minimum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getMaximum() {
        return maximum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IntervalType getIntervalType() {
        return this.intervalType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Comparator<T> getComparator() {
        return this.comparator;
    }

    /**
     * Compares this interval with the specified interval for order.
     *
     * <p>The comparison is performed lexicographically using the following fields:
     * minimum, maximum, start inclusiveness, and end inclusiveness.
     *
     * <p>Both intervals must use equal comparators. Otherwise, this method
     * throws an {@link IllegalArgumentException}.
     *
     * @param other the interval to be compared
     * @return a negative integer, zero, or a positive integer as this interval
     *         is less than, equal to, or greater than the specified interval
     *
     * @throws IllegalArgumentException if the comparators are not equal
     * @throws NullPointerException     if {@code other} is {@code null}
     */
    @Override
    public int compareTo(@NotNull Interval<T> other) {
        if (!Objects.equals(this.comparator, other.getComparator()))
            throw new IllegalArgumentException("Comparator must be the same");

        return CompareUtils.createCompareBuilder()
            .tryCompare(this.minimum, other.getMinimum(), this.comparator)
            .tryCompare(this.maximum, other.getMaximum(), this.comparator)
            .compareBoolean(this.isStartInclusive(), other.isStartInclusive())
            .compareBoolean(this.isEndInclusive(), other.isEndInclusive())
            .executeCompare();
    }

    /**
     * Indicates whether some other object is "equal to" this interval.
     *
     * <p>Two intervals are equal if they have the same runtime class,
     * equal bounds, and the same {@link IntervalType}.
     *
     * <p>The comparator is not considered in this comparison.
     *
     * @param o the reference object with which to compare
     * @return {@code true} if this object is equal to {@code o}; {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractInterval<?> that = (AbstractInterval<?>) o;
        return Objects.equals(maximum, that.maximum) &&
            Objects.equals(minimum, that.minimum) &&
            intervalType == that.intervalType &&
            Objects.equals(comparator, that.comparator);
    }

    /**
     * Returns a hash code value for this interval.
     *
     * <p>The hash code is computed from the bounds, interval type, and comparator.
     *
     * @return a hash code value for this interval
     */
    @Override
    public int hashCode() {
        return Objects.hash(maximum, minimum, intervalType, comparator);
    }
}
