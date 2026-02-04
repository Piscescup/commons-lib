package io.github.piscescup.math.interval;

import io.github.piscescup.util.validation.NullCheck;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a (possibly open/closed) interval over an ordered domain.
 *
 * <p>An interval is defined by:
 * <ul>
 *   <li>a minimum bound (start)</li>
 *   <li>a maximum bound (end)</li>
 *   <li>an {@link IntervalType} describing endpoint inclusiveness</li>
 *   <li>a {@link Comparator} that defines the ordering of the domain</li>
 * </ul>
 *
 * <h2>Ordering and Comparator compatibility</h2>
 * <p>All comparisons performed by this interface are based on {@link #getComparator()}.
 * For operations that combine two intervals (e.g. {@link #containsInterval(Interval)},
 * {@link #overlaps(Interval)}, {@link #intersection(Interval)}), the two intervals are
 * expected to use compatible ordering semantics.
 *
 * <p><b>Important:</b> If two intervals use different comparators that are not mutually
 * consistent, an {@link IllegalArgumentException} will be thrown.
 *
 * <h2>Empty and degenerate intervals</h2>
 * <ul>
 *   <li>A <em>degenerate</em> interval has equal bounds: {@code min == max} under the comparator
 *       (see {@link #isDegenerate()}).</li>
 *   <li>An <em>empty</em> interval contains no values (see {@link #isEmpty()}).
 *       For example, {@code (a, a)} and {@code (a, a]} are empty, while {@code [a, a]} is not.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Natural ordering interval
 * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2, 6]
 * a.contains(2);  // true
 * a.contains(7);  // false
 *
 * // Open/closed endpoints
 * Interval<Integer> b = Interval.naturalOrderedInterval(2, 6, IntervalType.OPEN_CLOSED_INTERVAL); // (2, 6]
 * b.contains(2);  // false
 * b.contains(6);  // true
 *
 * // Comparator-based interval (e.g., reverse order)
 * Interval<Integer> c = Interval.comparatorOrderedInterval(6, 2, IntervalType.CLOSED_INTERVAL, Comparator.reverseOrder()); // [6, 2] under reverse order
 * c.contains(5);  // true (because reverse order defines 6 <= 5 <= 2)
 * }</pre>
 *
 * @param <T> the element type
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public interface Interval<T> extends Comparable<Interval<T>>, Serializable {

    /**
     * Returns the minimum (start) bound of this interval.
     *
     * <p>The meaning of "minimum" is defined by {@link #getComparator()}.
     *
     * @return the minimum (start) bound
     */
    T getMinimum();

    /**
     * Returns the maximum (end) bound of this interval.
     *
     * <p>The meaning of "maximum" is defined by {@link #getComparator()}.
     *
     * @return the maximum (end) bound
     */
    T getMaximum();

    /**
     * Returns the {@link IntervalType} describing endpoint inclusiveness.
     *
     * <p>The interval type determines whether the start/end bounds are inclusive
     * or exclusive. Convenience methods {@link #isStartInclusive()} and
     * {@link #isEndInclusive()} are derived from this value.
     *
     * @return the interval type
     */
    IntervalType getIntervalType();

    /**
     * Returns the comparator that defines the ordering of values inside this interval.
     *
     * <p>All comparisons and ordering-related operations in this interface must
     * use this comparator.
     *
     * @return the comparator used by this interval
     */
    @NotNull
    Comparator<T> getComparator();

    /**
     * Returns {@code true} if the start bound equals the end bound under the comparator.
     *
     * <p>Note that a degenerate interval may still be empty depending on inclusiveness:
     * {@code [a, a]} is not empty, while {@code (a, a)} is empty.
     *
     * <pre>{@code
     * Interval<Integer> x = Interval.naturalOrderedInterval(4, 4, IntervalType.CLOSED_INTERVAL); // [4,4]
     * x.isDegenerate(); // true
     * x.isEmpty();      // false
     * }</pre>
     *
     * @return {@code true} if {@code minimum == maximum} under {@link #getComparator()}
     */
    default boolean isDegenerate() {
        return getComparator().compare(getMinimum(), getMaximum()) == 0;
    }

    /**
     * Returns {@code true} if the given value is exactly on the start endpoint and the start
     * endpoint is inclusive.
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * a.onStartEndpoint(2); // true
     *
     * Interval<Integer> b = Interval.naturalOrderedInterval(2, 6, IntervalType.OPEN_CLOSED_INTERVAL); // (2,6]
     * b.onStartEndpoint(2); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} equals {@link #getMinimum()} and {@link #isStartInclusive()} is {@code true}
     */
    default boolean onStartEndpoint(T value) {
        if (value == null) return false;
        return isStartInclusive() &&
            getComparator().compare(getMinimum(), value) == 0;
    }

    /**
     * Returns {@code true} if the given value is exactly on the end endpoint and the end
     * endpoint is inclusive.
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * a.onEndEndpoint(6); // true
     *
     * Interval<Integer> b = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_OPEN_INTERVAL); // [2,6)
     * b.onEndEndpoint(6); // false
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} equals {@link #getMaximum()} and {@link #isEndInclusive()} is {@code true}
     */
    default boolean onEndEndpoint(T value) {
        if (value == null) return false;
        return isEndInclusive() &&
            getComparator().compare(getMaximum(), value) == 0;
    }

    /**
     * Returns {@code true} if this interval contains the given value.
     *
     * <p>Containment respects endpoint inclusiveness:
     * <ul>
     *   <li>If the start is inclusive, {@code value >= min} is allowed; otherwise {@code value > min}.</li>
     *   <li>If the end is inclusive, {@code value <= max} is allowed; otherwise {@code value < max}.</li>
     * </ul>
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * a.contains(2); // true
     * a.contains(6); // true
     *
     * Interval<Integer> b = Interval.naturalOrderedInterval(2, 6, IntervalType.OPEN_INTERVAL); // (2,6)
     * b.contains(2); // false
     * b.contains(5); // true
     * }</pre>
     *
     * @param value the value to test
     * @return {@code true} if {@code value} lies within this interval according to bounds and inclusiveness
     */
    default boolean contains(T value) {
        if (value == null) return false;

        int cs = getComparator().compare(value, getMinimum());
        int ce = getComparator().compare(value, getMaximum());

        boolean afterStart = isStartInclusive() ? cs >= 0 : cs > 0;
        boolean beforeEnd  = isEndInclusive()   ? ce <= 0 : ce < 0;

        return afterStart && beforeEnd;
    }

    /**
     * Returns {@code true} if this interval fully contains the given interval {@code other}.
     *
     * <p>Containment rules:
     * <ul>
     *   <li>{@code other.min} must be after this {@code min}, or equal with compatible inclusiveness.</li>
     *   <li>{@code other.max} must be before this {@code max}, or equal with compatible inclusiveness.</li>
     * </ul>
     *
     * <p>If {@code other} is {@code null}, this method returns {@code false}.
     *
     * <p><b>Comparator requirement:</b> the two intervals must use the same comparator.
     * Otherwise this method throws {@link IllegalArgumentException}.
     *
     * <p><b>Comparator compatibility:</b> this operation assumes both intervals are ordered under
     * compatible comparators. If comparators differ, results are undefined unless the implementation
     * enforces a check.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * Interval<Integer> b = Interval.naturalOrderedInterval(3, 5, IntervalType.OPEN_INTERVAL); // (3,5)
     * a.containsInterval(b); // true
     *
     * Interval<Integer> c = Interval.naturalOrderedInterval(2, 6, IntervalType.OPEN_INTERVAL); // (2,6)
     * a.containsInterval(c); // false (a includes endpoints, but c excludes them; start/end equality rules apply)
     * }</pre>
     *
     * @param other the interval to test
     * @return {@code true} if this interval contains {@code other}
     */
    default boolean containsInterval(Interval<T> other) {
        if (other == null) return false;
        if (!Objects.equals(this.getComparator(), other.getComparator()))
            throw new IllegalArgumentException("Comparator must be the same");

        // start bound
        int startCmp = getComparator().compare(other.getMinimum(), getMinimum());
        boolean startOk =
            startCmp > 0 ||
                (startCmp == 0 && (!other.isStartInclusive() || isStartInclusive()));

        // end bound
        int endCmp = getComparator().compare(other.getMaximum(), getMaximum());
        boolean endOk =
            endCmp < 0 ||
                (endCmp == 0 && (!other.isEndInclusive() || isEndInclusive()));

        return startOk && endOk;
    }

    /**
     * Returns {@code true} if this interval is fully contained by {@code other}.
     *
     * <p>This is equivalent to {@code other.containsInterval(this)}.
     *
     * <p>If {@code other} is {@code null}, this method returns {@code false}.
     *
     * <p><b>Comparator requirement:</b> the two intervals must use the same comparator.
     * Otherwise this method throws {@link IllegalArgumentException}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(3, 5, IntervalType.CLOSED_INTERVAL); // [3,5]
     * Interval<Integer> b = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * a.isContainedBy(b); // true
     * }</pre>
     *
     * @param other the interval to test against
     * @return {@code true} if this interval is contained by {@code other}
     */
    default boolean isContainedBy(Interval<T> other) {
        if (other == null) return false;
        if (!Objects.equals(this.getComparator(), other.getComparator()))
            throw new IllegalArgumentException("Comparator must be the same");
        return other.containsInterval(this);
    }

    /**
     * Returns {@code true} if this interval is empty (contains no values).
     *
     * <p>An interval is considered empty if:
     * <ul>
     *   <li>{@code minimum > maximum} under the comparator; or</li>
     *   <li>{@code minimum == maximum} under the comparator and not both endpoints are inclusive.</li>
     * </ul>
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(4, 4, IntervalType.OPEN_INTERVAL); // (4,4)
     * a.isEmpty(); // true
     *
     * Interval<Integer> b = Interval.naturalOrderedInterval(4, 4, IntervalType.CLOSED_INTERVAL); // [4,4]
     * b.isEmpty(); // false
     * }</pre>
     *
     * @return {@code true} if this interval is empty
     */
    default boolean isEmpty() {
        int c = getComparator().compare(getMinimum(), getMaximum());
        if (c > 0) return true;
        if (c < 0) return false;
        return !(isStartInclusive() && isEndInclusive());
    }

    /**
     * Returns {@code true} if this interval overlaps with {@code other}.
     *
     * <p>Two intervals overlap if their intersection is non-empty.
     * Empty intervals never overlap any interval.
     *
     * <p><b>Comparator requirement:</b> the two intervals must use the same comparator.
     * Otherwise this method throws {@link IllegalArgumentException}.
     *
     * <p>If {@code other} is {@code null}, this method returns {@code false}.
     *
     * <p><b>Comparator compatibility:</b> this operation assumes both intervals are ordered under
     * compatible comparators. If comparators differ, results are undefined unless the implementation
     * enforces a check.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * Interval<Integer> b = Interval.naturalOrderedInterval(6, 9, IntervalType.CLOSED_INTERVAL); // [6,9]
     * a.overlaps(b); // true (touching at 6, both inclusive)
     *
     * Interval<Integer> c = Interval.naturalOrderedInterval(6, 9, IntervalType.OPEN_INTERVAL); // (6,9)
     * a.overlaps(c); // false (touching at 6 but c excludes 6)
     * }</pre>
     *
     * @param other the other interval
     * @return {@code true} if the intervals overlap
     */
    default boolean overlaps(Interval<T> other) {
        if (other == null) return false;
        if (!Objects.equals(this.getComparator(), other.getComparator()))
            throw new IllegalArgumentException("Comparator must be the same");

        if (this.isEmpty() || other.isEmpty()) {
            return false;
        }

        int sCmp = getComparator().compare(getMinimum(), other.getMaximum()); // this.start ? other.end
        if (sCmp > 0) return false;
        if (sCmp == 0 && !(this.isStartInclusive() && other.isEndInclusive())) return false;

        int eCmp = getComparator().compare(this.getMaximum(), other.getMinimum()); // this.end ? other.start
        if (eCmp < 0) return false;
        return eCmp != 0 || this.isEndInclusive() && other.isStartInclusive();
    }

    /**
     * Computes the intersection of this interval and {@code other}.
     *
     * <p>If the intervals do not overlap, returns {@link Optional#empty()}.
     * If {@code other} is {@code null}, returns {@code Optional.of(this)}.
     *
     * <p><b>Comparator requirement:</b> the two intervals must use the same comparator.
     * Otherwise this method throws {@link IllegalArgumentException}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * Interval<Integer> b = Interval.naturalOrderedInterval(4, 9, IntervalType.OPEN_CLOSED_INTERVAL); // (4,9]
     *
     * Optional<Interval<Integer>> r = a.intersection(b);
     * r.get().formattedString(); // (4,6]
     * }</pre>
     *
     * @param other the other interval
     * @return an {@link Optional} describing the intersection, or empty if there is no overlap
     * @throws IllegalArgumentException if {@code other} uses a different comparator
     */
    default Optional<Interval<T>> intersection(Interval<T> other) {
        if (other == null) return Optional.of(this);

        if (!Objects.equals(this.getComparator(), other.getComparator()))
            throw new IllegalArgumentException("Comparator must be the same");

        if (!overlaps(other)) {
            return Optional.empty();
        }

        // newStart = max(start)
        T newStart;
        boolean newStartInc;
        int startCmp = getComparator().compare(getMinimum(), other.getMinimum());
        if (startCmp > 0) {
            newStart = this.getMinimum();
            newStartInc = this.isStartInclusive();
        } else if (startCmp < 0) {
            newStart = other.getMinimum();
            newStartInc = other.isStartInclusive();
        } else {
            newStart = this.getMinimum();
            newStartInc = this.isStartInclusive() && other.isStartInclusive();
        }

        // newEnd = min(end)
        T newEnd;
        boolean newEndInc;
        int endCmp = getComparator().compare(this.getMaximum(), other.getMaximum());
        if (endCmp < 0) {
            newEnd = this.getMaximum();
            newEndInc = this.isEndInclusive();
        } else if (endCmp > 0) {
            newEnd = other.getMaximum();
            newEndInc = other.isEndInclusive();
        } else {
            newEnd = this.getMaximum();
            newEndInc = this.isEndInclusive() && other.isEndInclusive();
        }

        IntervalType type = IntervalType.of(newStartInc, newEndInc);

        return Optional.of(
            ComparatorOrderedInterval.of(newStart, newEnd, type, other.getComparator())
        );
    }

    /**
     * Returns {@code true} if the start of this interval is after the given value,
     * taking the start inclusiveness into account.
     *
     * <p>Semantics:
     * <ul>
     *   <li>If {@code minimum > value}, returns {@code true}.</li>
     *   <li>If {@code minimum < value}, returns {@code false}.</li>
     *   <li>If {@code minimum == value}, returns {@code true} iff start is exclusive.</li>
     * </ul>
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(4, 6, IntervalType.CLOSED_INTERVAL); // [4,6]
     * a.startsAfter(4); // false
     *
     * Interval<Integer> b = Interval.naturalOrderedInterval(4, 6, IntervalType.OPEN_CLOSED_INTERVAL); // (4,6]
     * b.startsAfter(4); // true
     * }</pre>
     *
     * @param value the reference value
     * @return {@code true} if this interval starts after {@code value} under the comparator
     */
    default boolean startsAfter(T value) {
        if (value == null) return false;
        int comparison = getComparator().compare(getMinimum(), value);

        if (comparison > 0) return true;
        if (comparison < 0) return false;
        return !isStartInclusive();
    }

    /**
     * Returns {@code true} if this interval starts strictly after the given value.
     *
     * <p>This method uses a stricter interpretation than {@link #startsAfter(Object)}:
     * it requires the start endpoint to be inclusive and {@code minimum > value}.
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(4, 6, IntervalType.CLOSED_INTERVAL); // [4,6]
     * a.startsAfterStrictly(3); // true
     * a.startsAfterStrictly(4); // false
     * }</pre>
     *
     * @param value the reference value
     * @return {@code true} if this interval starts strictly after {@code value}
     */
    default boolean startsAfterStrictly(T value) {
        if (value == null) return false;
        int cmp = getComparator().compare(getMinimum(), value);
        return isStartInclusive() && cmp > 0;
    }

    /**
     * Returns {@code true} if the end of this interval is before the given value,
     * taking the end inclusiveness into account.
     *
     * <p>Semantics:
     * <ul>
     *   <li>If {@code maximum < value}, returns {@code true}.</li>
     *   <li>If {@code maximum > value}, returns {@code false}.</li>
     *   <li>If {@code maximum == value}, returns {@code true} iff end is exclusive.</li>
     * </ul>
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(4, 6, IntervalType.CLOSED_INTERVAL); // [4,6]
     * a.endsBefore(6); // false
     *
     * Interval<Integer> b = Interval.naturalOrderedInterval(4, 6, IntervalType.CLOSED_OPEN_INTERVAL); // [4,6)
     * b.endsBefore(6); // true
     * }</pre>
     *
     * @param value the reference value
     * @return {@code true} if this interval ends before {@code value}
     */
    default boolean endsBefore(T value) {
        if (value == null) return false;
        int comparison = getComparator().compare(getMaximum(), value);

        if (comparison < 0) return true;
        if (comparison > 0) return false;

        return !isEndInclusive();
    }

    /**
     * Returns {@code true} if this interval ends strictly before the given value.
     *
     * <p>This method uses a stricter interpretation than {@link #endsBefore(Object)}:
     * it requires the end endpoint to be inclusive and {@code maximum < value}.
     *
     * <p>If {@code value} is {@code null}, this method returns {@code false}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(4, 6, IntervalType.CLOSED_INTERVAL); // [4,6]
     * a.endsBeforeStrictly(7); // true
     * a.endsBeforeStrictly(6); // false
     * }</pre>
     *
     * @param value the reference value
     * @return {@code true} if this interval ends strictly before {@code value}
     */
    default boolean endsBeforeStrictly(T value) {
        if (value == null) return false;

        int cmp = getComparator().compare(getMaximum(), value);
        return isEndInclusive() && cmp < 0;
    }

    /**
     * Returns {@code true} if the start endpoint of this interval is inclusive.
     *
     * @return {@code true} if the start is inclusive
     */
    default boolean isStartInclusive() {
        return getIntervalType().isStartInclusive();
    }

    /**
     * Returns {@code true} if the end endpoint of this interval is inclusive.
     *
     * @return {@code true} if the end is inclusive
     */
    default boolean isEndInclusive() {
        return getIntervalType().isEndInclusive();
    }

    /**
     * Returns a formatted string representation of this interval using its {@link IntervalType}.
     *
     * <p>The concrete formatting logic is delegated to {@link IntervalType#format(Interval)}.
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL);
     * a.formattedString(); // "[2, 6]" (depending on IntervalType implementation)
     * }</pre>
     *
     * @return a formatted string representation of this interval
     */
    default String formattedString() {
        return getIntervalType().format(this);
    }

    /**
     * Creates a natural-ordered interval using the elements' natural ordering.
     *
     * <p>This factory requires {@code V} to be {@link Comparable} and uses the natural order
     * comparator ({@link Comparator#naturalOrder()}).
     *
     * <pre>{@code
     * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED_INTERVAL); // [2,6]
     * }</pre>
     *
     * @param min  the start bound
     * @param max  the end bound
     * @param type the interval type (endpoint inclusiveness)
     * @param <V>  the element type
     * @return a new {@link NaturalOrderedInterval}
     */
    @Contract("_, _, _ -> new")
    static <V extends Comparable<? super V>> @NotNull Interval<V> naturalOrdered(
        V min, V max, IntervalType type
    ) {
        return NaturalOrderedInterval.of(min, max, type);
    }

    /**
     * Creates a comparator-ordered interval using the specified comparator.
     *
     * <p>The provided comparator defines the ordering semantics of the interval.
     *
     * <pre>{@code
     * Interval<String> a = Interval.comparatorOrderedInterval(
     *     "a", "z", IntervalType.CLOSED_INTERVAL, String.CASE_INSENSITIVE_ORDER
     * );
     * }</pre>
     *
     * @param min        the start bound
     * @param max        the end bound
     * @param type       the interval type (endpoint inclusiveness)
     * @param comparator the comparator defining the order (must not be null)
     * @param <V>        the element type
     * @return a new {@link ComparatorOrderedInterval}
     * @throws NullPointerException if {@code comparator} is null
     */
    static <V> @NotNull Interval<V> comparatorOrdered(
        V min, V max, IntervalType type, Comparator<V> comparator
    ) {
        NullCheck.requireNonNull(comparator);
        return ComparatorOrderedInterval.of(min, max, type, comparator);
    }

    /**
     * Returns the empty interval (the mathematical empty set {@code ∅}).
     *
     * <p>The returned interval contains no elements and overlaps no interval.
     * This method always returns the same singleton instance.
     *
     * <strong>Mathematical semantics</strong>
     *
     * <pre>
     * ∀x, x ∉ ∅
     * ∅ ⊆ A
     * ∅ ∩ A = ∅
     * </pre>
     *
     * <strong>Examples</strong>
     *
     * <pre>{@code
     * Interval<Integer> e = Interval.empty();
     *
     * e.isEmpty();        // true
     * e.contains(10);     // false
     * e.formattedString(); // "∅"
     * }</pre>
     *
     * @param <V> the element type
     * @return the singleton empty interval
     *
     * @since 1.0.0
     */
    static <V> @NotNull Interval<V> empty() {
        return EmptyInterval.empty();
    }

}
