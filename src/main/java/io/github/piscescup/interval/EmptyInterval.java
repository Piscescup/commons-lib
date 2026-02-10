package io.github.piscescup.interval;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.util.Comparator;
import java.util.Optional;

/**
 * The empty interval, representing the empty set in mathematics.
 *
 * <p>This implementation models the mathematical empty set {@code ∅}. It contains no values
 * and overlaps no interval. The empty interval is also a singleton: {@link #empty()} always
 * returns the same instance.
 *
 * <h2>Mathematical semantics</h2>
 *
 * <div style="text-align: center;">
 *   <p><b>∅</b></p>
 *   <p>{@code ∀x, x ∉ ∅}</p>
 *   <p>{@code ∅ ⊆ A} for any interval/set {@code A}</p>
 *   <p>{@code ∅ ∩ A = ∅} for any interval/set {@code A}</p>
 * </div>
 *
 * <p>In this library, the empty interval has no endpoints, therefore calling
 * {@link #getMinimum()} or {@link #getMaximum()} throws {@link UnsupportedOperationException}.
 *
 * <h2>Comparator</h2>
 * <p>The comparator of this interval is a trivial comparator that always returns {@code 0}.
 * It exists only to satisfy the {@link ObjectInterval} contract; ordering-related operations on
 * {@code ∅} are defined by overriding default methods (e.g. {@link #contains(Object)},
 * {@link #overlaps(ObjectInterval)}, {@link #intersection(ObjectInterval)}).
 *
 * <h2>Ordering</h2>
 * <p>The empty interval is considered less than any non-empty interval, and equal to another
 * empty interval.
 *
 * <pre>{@code
 * Interval<Integer> e = EmptyInterval.empty();
 * Interval<Integer> a = Interval.naturalOrderedInterval(2, 6, IntervalType.CLOSED); // [2,6]
 *
 * e.isEmpty();               // true
 * e.contains(3);             // false
 * e.overlaps(a);             // false
 * e.intersection(a).get();   // ∅
 * e.compareTo(a) < 0;        // true
 * }</pre>
 *
 * @param <T> the element type
 *
 * @author REN YuanTong
 * @since 1.0.0
 */
public final class EmptyInterval<T> implements ObjectInterval<T> {
    @Serial
    private static final long serialVersionUID = 162344756789L;

    /**
     * A trivial comparator that treats all values as equal.
     *
     * <p>This comparator is used only to satisfy the {@link Interval} contract.
     * The empty interval's behavior is defined by overriding interval operations directly.
     */
    private static final Comparator<?> COMPARATOR = (a, b) -> 0;

    /**
     * The singleton empty interval instance.
     */
    private static final EmptyInterval<?> INSTANCE = new EmptyInterval<>();

    /**
     * Returns the singleton empty interval instance.
     *
     * <p>This method always returns the same object.
     *
     * <pre>{@code
     * EmptyInterval<Integer> e1 = EmptyInterval.empty();
     * EmptyInterval<Integer> e2 = EmptyInterval.empty();
     * (e1 == e2); // true
     * }</pre>
     *
     * @param <T> the element type
     * @return the singleton empty interval
     */
    @SuppressWarnings("unchecked")
    public static <T> EmptyInterval<T> empty() {
        return (EmptyInterval<T>) INSTANCE;
    }

    /**
     * Private constructor to enforce singleton usage.
     */
    private EmptyInterval() {}

    /**
     * The empty interval has no minimum bound.
     *
     * @return never returns normally
     * @throws UnsupportedOperationException always
     */
    @Override
    public T getMinimum() {
        throw new UnsupportedOperationException("An empty interval does not have a minimum value.");
    }

    /**
     * The empty interval has no maximum bound.
     *
     * @return never returns normally
     * @throws UnsupportedOperationException always
     */
    @Override
    public T getMaximum() {
        throw new UnsupportedOperationException("An empty interval does not have a maximum value.");
    }

    /**
     * Returns an interval type for formatting purposes.
     *
     * <p>Since the empty interval has no endpoints, the returned value does not carry
     * mathematical meaning and is used only to satisfy the {@link ObjectInterval} contract.
     *
     * @return an arbitrary interval type (used only for contract compatibility)
     */
    @Override
    public IntervalType getIntervalType() {
        return IntervalType.OPEN_INTERVAL;
    }

    /**
     * Returns a trivial comparator for contract compatibility.
     *
     * <p>This comparator always returns {@code 0} and should not be relied upon for
     * ordering semantics beyond the empty interval itself.
     *
     * @return a trivial comparator
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public @NotNull Comparator<T> getComparator() {
        return (Comparator<T>) COMPARATOR;
    }

    /**
     * The empty interval is never degenerate (it has no bounds).
     *
     * @return {@code false}
     */
    @Override
    public boolean isDegenerate() {
        return false;
    }

    /**
     * The empty interval has no endpoints.
     *
     * @param value the value to test
     * @return {@code false}
     */
    @Override
    public boolean onStartEndpoint(T value) {
        return false;
    }

    /**
     * The empty interval has no endpoints.
     *
     * @param value the value to test
     * @return {@code false}
     */
    @Override
    public boolean onEndEndpoint(T value) {
        return false;
    }

    /**
     * The empty interval contains no values.
     *
     * <div style="text-align: center;">
     *    ∀x, x ∉ ∅
     * </div>
     *
     * @param value the value to test
     * @return {@code false}
     */
    @Override
    public boolean contains(T value) {
        return false;
    }

    /**
     * Returns {@code true} if and only if {@code other} is also empty.
     *
     * <div style="text-align: center;">
     *   ∅ ⊇ ∅
     * </div>
     *
     * @param other the interval to test
     * @return {@code true} if {@code other} is non-null and empty; {@code false} otherwise
     */
    @Override
    public boolean containsInterval(ObjectInterval<T> other) {
        return other != null && other.isEmpty();
    }

    /**
     * The empty interval is contained by any non-null interval.
     *
     * <div style="text-align: center;">
     *   ∅ ⊆ A
     * </div>
     *
     * @param other the interval to test against
     * @return {@code true} if {@code other} is non-null; {@code false} otherwise
     */
    @Override
    public boolean isContainedBy(ObjectInterval<T> other) {
        return other != null;
    }

    /**
     * Always returns {@code true}.
     *
     * @return {@code true}
     */
    @Override
    public boolean isEmpty() {
        return true;
    }

    /**
     * The empty interval overlaps no interval.
     *
     * <div style="text-align: center;">
     *   ∀A, ∅ ∩ A = ∅
     * </div>
     *
     * @param other the other interval
     * @return {@code false}
     */
    @Override
    public boolean overlaps(ObjectInterval<T> other) {
        return false;
    }

    /**
     * Returns the intersection of this empty interval with {@code other}.
     *
     * <div style="text-align: center;">
     *    ∅ ∩ A = ∅
     * </div>
     *
     * @param other the other interval (ignored)
     * @return {@code Optional.of(this)} (the empty interval)
     */
    @Override
    public Optional<ObjectInterval<T>> intersection(ObjectInterval<T> other) {
        return Optional.of(this);
    }

    /**
     * The empty interval has no start bound; this predicate is always {@code false}.
     *
     * @param value the reference value
     * @return {@code false}
     */
    @Override
    public boolean startsAfter(T value) {
        return false;
    }

    /**
     * The empty interval has no start bound; this predicate is always {@code false}.
     *
     * @param value the reference value
     * @return {@code false}
     */
    @Override
    public boolean startsAfterStrictly(T value) {
        return false;
    }

    /**
     * The empty interval has no end bound; this predicate is always {@code false}.
     *
     * @param value the reference value
     * @return {@code false}
     */
    @Override
    public boolean endsBefore(T value) {
        return false;
    }

    /**
     * The empty interval has no end bound; this predicate is always {@code false}.
     *
     * @param value the reference value
     * @return {@code false}
     */
    @Override
    public boolean endsBeforeStrictly(T value) {
        return false;
    }


    /**
     * Returns the formatted string representation of the empty interval.
     *
     * @return {@code "∅"}
     */
    @Override
    public String formattedString() {
        return "∅";
    }

    /**
     * Compares this empty interval to another interval.
     *
     * <p>Ordering rules:
     * <ul>
     *   <li>∅ == ∅</li>
     *   <li>∅ is smaller than any non-empty interval</li>
     * </ul>
     *
     * <p><b>Note:</b> although {@code o} is annotated as {@link NotNull}, this implementation
     * defines {@code null} to be greater than {@code ∅} (returns {@code 1}).
     *
     * @param o the other interval
     * @return {@code 0} if {@code o} is empty; {@code -1} if {@code o} is non-empty;
     *         {@code 1} if {@code o} is {@code null}
     */
    @Override
    public int compareTo(@NotNull ObjectInterval<T> o) {
        if (o == null) return 1;
        if (o.isEmpty()) return 0;
        return -1;
    }
}
