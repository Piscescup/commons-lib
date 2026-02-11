package io.github.piscescup.interval;


import io.github.piscescup.exception.IllegalMathIntervalEndpointException;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Comparator;

/**
 * An {@link ObjectInterval} whose ordering is defined by the natural ordering of its elements.
 *
 * <p>This implementation uses {@link Comparator#naturalOrder()} as its comparator and therefore
 * requires the element type {@code T} to implement {@link Comparable}.
 *
 * <h2>Comparator semantics</h2>
 * <p>The comparator of this interval is always {@link Comparator#naturalOrder()}.
 * As a consequence, two {@code NaturalOrderedInterval} instances are comparable with each other
 * (via {@link #compareTo(ObjectInterval)}) as long as the other interval uses an equal comparator
 * according to {@link java.util.Objects#equals(Object, Object)}.
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * Interval<Integer> a = NaturalOrderedInterval.of(2, 6, IntervalType.CLOSED_INTERVAL);      // [2, 6]
 * Interval<Integer> b = NaturalOrderedInterval.of(2, 6, IntervalType.OPEN_INTERVAL);        // (2, 6)
 *
 * a.contains(2);        // true
 * b.contains(2);        // false
 * a.compareTo(b) > 0;   // true  ([2,6] > (2,6))
 * }</pre>
 *
 * @param <T> the element type, which must be {@link Comparable}
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public class NaturalOrderedInterval<T extends Comparable<? super T>>
    extends AbstractInterval<T>
    implements ObjectInterval<T>, Serializable
{
    @Serial
    private static final long serialVersionUID = 10586997701532L;

    /**
     * Creates a new natural-ordered interval with the specified bounds and interval type.
     *
     * <p>This constructor uses {@link Comparator#naturalOrder()} as the comparator.
     *
     * @param minimum      the minimum (start) bound
     * @param maximum      the maximum (end) bound
     * @param intervalType the interval type describing endpoint inclusiveness
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    private NaturalOrderedInterval(T minimum, T maximum, IntervalType intervalType) {
        super(minimum, maximum, intervalType, Comparator.naturalOrder());
    }

    /**
     * Creates a new {@code NaturalOrderedInterval} instance with the specified bounds
     * and interval type.
     *
     * <p>The returned interval uses {@link Comparator#naturalOrder()} for ordering.
     *
     * <pre>{@code
     * NaturalOrderedInterval<Integer> x =
     *     NaturalOrderedInterval.of(1, 10, IntervalType.CLOSED_INTERVAL); // [1, 10]
     * }</pre>
     *
     * @param minimum      the minimum (start) bound
     * @param maximum      the maximum (end) bound
     * @param intervalType the interval type describing endpoint inclusiveness
     * @param <T>          the element type
     *
     * @return a new {@code NaturalOrderedInterval}
     *
     * @throws NullPointerException if any argument is {@code null}
     */
    @Contract("_, _, _ -> new")
    public static <T extends Comparable<? super T>> @NotNull NaturalOrderedInterval<T> of(
        T minimum, T maximum, IntervalType intervalType
    ) {
        NullCheck.requireNonNull(maximum);
        NullCheck.requireNonNull(minimum);
        NullCheck.requireNonNull(intervalType);
        if (minimum.compareTo(maximum) > 0) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }
        return new NaturalOrderedInterval<>(minimum, maximum, intervalType);
    }
}
