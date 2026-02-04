package io.github.piscescup.math.interval;

import io.github.piscescup.exception.IllegalMathIntervalEndpointException;
import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * An {@link Interval} whose ordering is defined by a user-supplied {@link Comparator}.
 *
 * <p>This implementation allows defining intervals over arbitrary element types,
 * including types that do not implement {@link Comparable}, by providing an explicit
 * comparator.
 *
 * <h2>Comparator semantics</h2>
 * <p>All ordering-related operations (containment, overlap detection, comparison, etc.)
 * are based exclusively on the comparator supplied at construction time.
 *
 * <p>Two {@code ComparatorOrderedInterval} instances are considered comparable via
 * {@link #compareTo(Interval)} only if their comparators are equal according to
 * {@link java.util.Objects#equals(Object, Object)}. Otherwise, an
 * {@link IllegalArgumentException} is thrown.
 *
 * <h2>Usage examples</h2>
 *
 * <pre>{@code
 * // Case-insensitive string interval
 * Comparator<String> cmp = String.CASE_INSENSITIVE_ORDER;
 *
 * Interval<String> a =
 *     ComparatorOrderedInterval.of("a", "z", IntervalType.CLOSED_INTERVAL, cmp); // [a, z]
 *
 * a.contains("M"); // true
 *
 * // Custom comparator (by string length)
 * Comparator<String> byLength = Comparator.comparingInt(String::length);
 *
 * Interval<String> b =
 *     ComparatorOrderedInterval.of("a", "aaaa", IntervalType.CLOSED_INTERVAL, byLength);
 *
 * b.contains("bb");   // true (length 2 in [1,4])
 * b.contains("aaaaa"); // false (length 5 > 4)
 * }</pre>
 *
 * @param <T> the element type
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public class ComparatorOrderedInterval<T>
    extends AbstractInterval<T>
    implements Interval<T> {

    /**
     * Creates a new comparator-ordered interval with the specified bounds, interval type,
     * and comparator.
     *
     * <p>This constructor is private; instances must be created via
     * {@link #of(Object, Object, IntervalType, Comparator)}.
     *
     * @param minimum      the minimum (start) bound
     * @param maximum      the maximum (end) bound
     * @param intervalType the interval type describing endpoint inclusiveness
     * @param comparator   the comparator defining the ordering (must not be {@code null})
     *
     * @throws NullPointerException if {@code comparator} is {@code null}
     */
    private ComparatorOrderedInterval(
        T minimum,
        T maximum,
        IntervalType intervalType,
        Comparator<T> comparator
    ) {
        super(minimum, maximum, intervalType, comparator);
    }

    /**
     * Creates a new {@code ComparatorOrderedInterval} instance with the specified bounds,
     * interval type, and comparator.
     *
     * <p>The provided comparator defines the ordering semantics of the resulting interval.
     *
     * <pre>{@code
     * Interval<Double> x =
     *     ComparatorOrderedInterval.of(
     *         0.0, 1.0, IntervalType.CLOSED_INTERVAL, Comparator.reverseOrder()
     *     ); // [1.0, 0.0] under reverse order
     * }</pre>
     *
     * @param minimum      the minimum (start) bound
     * @param maximum      the maximum (end) bound
     * @param intervalType the interval type describing endpoint inclusiveness
     * @param comparator   the comparator defining the ordering (must not be {@code null})
     * @param <T>          the element type
     *
     * @return a new {@code ComparatorOrderedInterval}
     *
     * @throws NullPointerException if {@code comparator} is {@code null}
     */
    @Contract("_, _, _, _ -> new")
    public static <T> @NotNull ComparatorOrderedInterval<T> of(
        T minimum,
        T maximum,
        IntervalType intervalType,
        Comparator<T> comparator
    ) {
        NullCheck.requireNonNull(maximum);
        NullCheck.requireNonNull(minimum);
        NullCheck.requireNonNull(intervalType);
        NullCheck.requireNonNull(comparator);
        if (comparator.compare(minimum, maximum) > 0) {
            throw new IllegalMathIntervalEndpointException(minimum, maximum);
        }

        return new ComparatorOrderedInterval<>(minimum, maximum, intervalType, comparator);
    }
}
